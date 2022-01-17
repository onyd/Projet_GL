package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractDeclMethod;
import fr.ensimag.deca.tree.Identifier;
import fr.ensimag.deca.tree.ListDeclField;
import fr.ensimag.deca.tree.ListDeclMethod;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class VTable {
    private HashMap<String, List<Label>> VTables;
    private List<Label> currentLabelList;
    private DecacCompiler compiler;

    public VTable(DecacCompiler compiler) {
        this.compiler = compiler;
        this.VTables = new HashMap<>();
    }

    /**
     * add the label of the current methodName in the method table. It replaces an exiting method by the new one
     * @param methodName
     * @param className
     */
    private void addLabelInVTable(String methodName, String className) {
        Label label = compiler.getLabelManager().getMethodLabel(className, methodName);
        for(int i = 0; i < currentLabelList.size(); i++) {
            if(currentLabelList.get(i).toString().contains(methodName)) {
                currentLabelList.set(i, label);
                return;
            }
        }
        currentLabelList.add(label);
    }

    /**
     * create the method table for Object
     */
    public void VTableForObject() {
        compiler.addComment("For Oject:");
        this.currentLabelList = new ArrayList<>();
        compiler.getStack().declareImmediateOnStackAndReturnDAddr(new NullOperand());
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
            this.addLabelInVTable(declMethod.getMethodIdent().getName().getName(), className);
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
            if(label.toString().contains(methodName)) {
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

    public void constructor(ListDeclField listDeclField, String className) {
        compiler.addLabel(new Label("init." + className));
        listDeclField.codeGenListDeclField(compiler);
    }

    /**
     * create all the method of the class
     * @param listDeclMethod
     * @param className
     */
    public void createMethods(ListDeclMethod listDeclMethod, String className) {

    }
}
