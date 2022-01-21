package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.tree.*;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class VTable {
    private HashMap<String, List<Label>> VTables;
    private List<Label> currentLabelList;
    private IMACompiler compiler;

    public VTable(IMACompiler compiler) {
        this.compiler = compiler;
        this.VTables = new HashMap<>();
    }

    /**
     * add the label of the current methodName in the method table. It replaces an exiting method by the new one
     * @param methodName
     * @param className
     */
    private void addLabelInVTable(String methodName, String className, AbstractDeclMethod declMethod, RegisterOffset reg) {
        Label label = compiler.getLabelManager().getMethodLabel(className, methodName);
        declMethod.getMethodIdent().getMethodDefinition().setLabel(label);
        for(int i = 0; i < currentLabelList.size(); i++) {
            if(currentLabelList.get(i).toString().contains(methodName)) {
                currentLabelList.set(i, label);
                declMethod.getMethodIdent().getMethodDefinition().setOperand(new RegisterOffset(reg.getOffset() + i + 1, Register.GB));
                return;
            }
        }
        declMethod.getMethodIdent().getMethodDefinition().setOperand(
                new RegisterOffset(reg.getOffset() + currentLabelList.size() + 1, Register.GB));
        currentLabelList.add(label);
    }

    /**
     * create the method table for Object
     */
    public void VTableForObject() {
        compiler.addComment("For Oject:");
        this.currentLabelList = new ArrayList<>();
        DAddr dAddr = compiler.getStack().declareImmediateOnStackAndReturnDAddr(new NullOperand());
        ((ClassDefinition) compiler.getEnvironmentType().get(compiler.OBJECT_SYMBOL)).setdAddrVTable(dAddr);
        Label equals = compiler.getLabelManager().getMethodLabel("Object", "equals");
        compiler.getStack().declareLabelOnStackAndReturnDAddr(equals);
        currentLabelList.add(equals);
        this.VTables.put("Object", currentLabelList);
    }

    /**
     * create the methode table for classIdent
     * @param classIdent
     * @param superClassIdent the super class of classIdent
     * @param listDeclMethod the list of method in classIdent
     */
    public void VTableFromIdent(Identifier classIdent, Identifier superClassIdent, ListDeclMethod listDeclMethod) {
        compiler.addComment("For " + classIdent.getName().getName());
        String className = classIdent.getName().getName();
        if(Objects.equals(superClassIdent.getName().getName(), "Object")) {
            this.currentLabelList = new ArrayList<>(this.VTables.get("Object"));
            compiler.addInstruction(new LEA(new RegisterOffset(1, Register.GB), Register.R1));
        } else {
            this.currentLabelList = new ArrayList<>(this.VTables.get(superClassIdent.getName().getName()));
            compiler.addInstruction(new LEA(superClassIdent.getClassDefinition().getdAddrVTable(), Register.R1));
        }
        DAddr dAddr = compiler.getStack().declareImmediateOnStackAndReturnDAddr(Register.R1);
        classIdent.getClassDefinition().setdAddrVTable(dAddr);
        for(AbstractDeclMethod declMethod : listDeclMethod.getList()) {
            this.addLabelInVTable(declMethod.getMethodIdent().getName().getName(), className, declMethod, (RegisterOffset) dAddr);
        }
        //add the VTable in the stack
        for(Label label : currentLabelList) {
            compiler.getStack().declareLabelOnStackAndReturnDAddr(label);
        }
        this.VTables.put(className, currentLabelList);
    }

    /**
     * return the label of the method associated with the class in parameters
     * @param methodName
     * @param className
     * @return
     */
    public Label getMethodLabel(String methodName, String className) {
        this.currentLabelList = this.VTables.get(className);
        for(Label label : currentLabelList) {
            if(label.toString().contains("." + methodName)) {
                return label;
            }
        }
        return null;
    }

    public void createEqualsMethod() {
        compiler.addLabel(compiler.getLabelManager().getMethodLabel("Object", "equals"));
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), Register.R1));
        compiler.addInstruction(new LOAD(new RegisterOffset(-3, Register.LB), Register.R0));
        compiler.addInstruction(new CMP(Register.R1, Register.R0));
        compiler.addInstruction(new SEQ(Register.R0));
        compiler.addInstruction(new RTS());
    }

    public void constructor(ListDeclField listDeclField, String className, Identifier superClass) {
        compiler.addLabel(new Label("init." + className));
        listDeclField.codeGenListDeclField(compiler);

        //initialize the fields of the superclass
        if(!Objects.equals(superClass.getName().getName(), "Object")) {
            compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), Register.R0));
            compiler.addInstruction(new PUSH(Register.R0));
            compiler.addInstruction(new BSR(new Label("init." + superClass.getName().getName())));
            compiler.addInstruction(new SUBSP(1));
        }
        compiler.addInstruction(new RTS());
    }

    /**
     * create all the method of the class
     * @param listDeclMethod
     * @param className
     */
    public void createMethods(ListDeclMethod listDeclMethod, String className) {
        for(AbstractDeclMethod declMethod : listDeclMethod.getList()) {
            declMethod.codeGenDeclMethod(compiler, className);
        }
    }
}
