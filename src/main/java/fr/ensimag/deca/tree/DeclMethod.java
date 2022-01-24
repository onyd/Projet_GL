package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;
import org.objectweb.asm.MethodVisitor;

import java.io.PrintStream;
import java.util.ArrayList;

public class DeclMethod extends AbstractDeclMethod {
    private final AbstractIdentifier returnType;
    private final AbstractIdentifier methodIdent;
    private ListDeclParam params;
    private final AbstractMethodBody body;

    public DeclMethod(AbstractIdentifier returnType, AbstractIdentifier methodIdent, ListDeclParam params, AbstractMethodBody body) {
        Validate.notNull(returnType);
        Validate.notNull(methodIdent);
        Validate.notNull(params);
        Validate.notNull(body);
        this.returnType = returnType;
        this.methodIdent = methodIdent;
        this.params = params;
        this.body = body;
    }

    public AbstractIdentifier getType() {
        return returnType;
    }

    @Override
    public AbstractIdentifier getMethodIdent() {
        return methodIdent;
    }

    public ListDeclParam getParams() {
        return params;
    }

    public AbstractMethodBody getBody() {
        return body;
    }

    @Override
    protected void verifyMethod(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        Type type = returnType.verifyType(compiler);
        Signature sig = params.verifyListClassMembers(compiler);
        int methodIndex;

        // Check for redefinition
        ExpDefinition def = currentClass.getSuperClass().getMembers().get(methodIdent.getName());
        if (def != null) {
            MethodDefinition superMethodDef = (MethodDefinition) def;
            if (!sig.equals(superMethodDef.getSignature()) || !type.isSubType(superMethodDef.getType())) {
                throw new ContextualError("(2.7) Redefinition of a method must match the signature and return a subtype of the super definition", getLocation());
            }
            methodIndex = superMethodDef.getIndex();
        } else {
            currentClass.incNumberOfMethods();
            methodIndex = currentClass.getNumberOfMethods();
        }

        MethodDefinition methodDef = new MethodDefinition(type, getLocation(), sig, methodIndex);
        try {
            currentClass.getMembers().declare(methodIdent.getName(), methodDef);
        } catch (Environment.DoubleDefException e) {
            throw new ContextualError("(2.7) Method has already been declared", getLocation());
        }
        methodIdent.verifyExpr(compiler, currentClass.getMembers(), currentClass);
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        EnvironmentExp envExpParams = new EnvironmentExp(null);
        params.verifyListParams(compiler, envExpParams);
        body.verifyBody(compiler, currentClass, envExpParams, returnType.getType());
    }

    @Override
    public void codeGenDeclMethod(IMACompiler compiler, String className) {
        Label methodLabel = compiler.getLabelManager().getMethodLabel(className, methodIdent.getName().getName());
        Label endLabel = compiler.getLabelManager().getEndMethodLabel(className, methodIdent.getName().getName());
        compiler.setCurrentMethod(methodIdent.getMethodDefinition());
        methodIdent.getMethodDefinition().setEndLabel(endLabel);

        compiler.addLabel(methodLabel);
        //save the registers
        compiler.addComment("Save All used registers");
        ArrayList<Integer> usedRegisters = compiler.getRegisterManager().allUsedRegisters();
        compiler.addInstruction(new TSTO(new ImmediateInteger(usedRegisters.size())));
        if(compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(new Label("stack_overflow_error")));
        }
        for(int registerNb : usedRegisters) {
            compiler.addInstruction(new PUSH(Register.getR(registerNb)));
        }

        params.codeGenListDeclParam(compiler);
        body.codeGenMethodBody(compiler);

        // No return error
        if (!compiler.getCompilerOptions().getNoCheck()) {
            if (!returnType.getType().isVoid()) {
                compiler.addInstruction(new WSTR("Erreur : sortie de la methode" + className + "." + methodIdent.getName() + " sans return"));
                compiler.addInstruction(new WNL());
                compiler.addInstruction(new ERROR());
            }
        }

        //restore the registers
        compiler.addLabel(endLabel);
        compiler.addComment("restore Registers");
        for(int i = usedRegisters.size() - 1; i >= 0; i--) {
            compiler.addInstruction(new POP(Register.getR(i)));
        }
        compiler.addInstruction(new RTS());
    }

    @Override
    public void codeGenDeclMethodByte(JavaCompiler javaCompiler) {
        MethodVisitor methodVisitor = javaCompiler.getClassWriter().visitMethod(javaCompiler.ACC_PUBLIC,
                                                    methodIdent.getName().getName(),
                                                    getDescSigAndInitParam(), null, null);
        javaCompiler.setMethodVisitor(methodVisitor);

        body.codeGenMethodBodyByte(javaCompiler, params.size() + 1);

        methodVisitor.visitInsn(javaCompiler.RETURN);
        methodVisitor.visitMaxs(-1, -1);

        methodVisitor.visitEnd();
    }

    private String getDescSigAndInitParam() {
        StringBuilder res = new StringBuilder();
        res.append("(");
        if(!getParams().getList().isEmpty()) {
            int index = 1;
            for(AbstractDeclParam param : getParams().getList()) {
                ((DeclParam) param).setIndexInLocals(index);
                res.append(((DeclParam) param).getParamIdent().getJavaType());
                index++;
            }
        }
        res.append(")");
        res.append(this.returnType.getJavaType());
        return res.toString();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        returnType.decompile(s);
        s.print(" ");
        methodIdent.decompile(s);
        s.print("(");
        params.decompile(s);
        s.print(")");
        body.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnType.prettyPrint(s, prefix, false);
        methodIdent.prettyPrint(s, prefix, false);
        params.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        returnType.iter(f);
        methodIdent.iter(f);
        params.iter(f);
        body.iter(f);
    }
}
