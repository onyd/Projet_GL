package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.Utils;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class MethodCall extends AbstractExpr {
    private AbstractExpr expr;
    private AbstractIdentifier methodIdent;
    private ListExpr arguments;

    public MethodCall(AbstractExpr expr, AbstractIdentifier methodIdent, ListExpr arguments) {
        super();
        Validate.notNull(expr);
        Validate.notNull(methodIdent);
        Validate.notNull(arguments);
        this.expr = expr;
        this.methodIdent = methodIdent;
        this.arguments = arguments;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type typeClass2 = expr.verifyExpr(compiler, localEnv, currentClass);
        ClassDefinition class2Def = typeClass2.asClassType("(3.71) Method can only be called on class type", getLocation()).getDefinition();
        Type type = methodIdent.verifyMethod(compiler, class2Def.getMembers());
        setType(type);

        // Verify given arguments
        if (arguments.size() != methodIdent.getMethodDefinition().getSignature().size()) {
            throw new ContextualError("(3.71) Method call must match the number of arguments", getLocation());
        }
        arguments.verifyRValueStar(compiler, localEnv, currentClass, methodIdent.getMethodDefinition().getSignature());
        return getType();
    }

    @Override
    protected void codeGenInst(IMACompiler compiler) {
        codeGenExprOnR1(compiler);
    }

    @Override
    protected void codeGenPrint(IMACompiler compiler) {
        codeGenExprOnR1(compiler);
        if(methodIdent.getMethodDefinition().getType().isInt()) {
            compiler.addInstruction(new WINT());
        } else if(methodIdent.getMethodDefinition().getType().isFloat()) {
            compiler.addInstruction(new WFLOAT());
        }
    }

    @Override
    public void codeGenExprOnRegister(IMACompiler compiler, GPRegister register) {
        compiler.addInstruction(new ADDSP(arguments.size() + 1));
        Utils.loadExpr(compiler, expr, register);
        compiler.addInstruction(new STORE(register, new RegisterOffset(0, Register.SP)));
        int offset = -1;
        for(AbstractExpr expr : arguments.getList()) {
            expr.codeGenExprOnR1(compiler);
            compiler.addInstruction(new STORE(register, new RegisterOffset(offset, Register.SP)));
            offset--;
        }
        compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.SP), register));
        compiler.addInstruction(new CMP(new NullOperand(), register));
        if(compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BEQ(new Label("seg_fault")));
        }
        compiler.addInstruction(new LOAD(new RegisterOffset(0, register), register));
        compiler.addInstruction(new BSR(new RegisterOffset(methodIdent.getMethodDefinition().getIndex(), register)));
        compiler.addInstruction(new SUBSP(arguments.size() + 1));
        compiler.addInstruction(new LOAD(Register.R0, register));
    }

    @Override
    protected void codeGenBool(IMACompiler compiler, boolean negation, Label label) {
        codeGenExprOnR1(compiler);
        compiler.addInstruction(new CMP(0, Register.R1));
        if (negation)
            compiler.addInstruction(new BNE(label));
        else
            compiler.addInstruction(new BEQ(label));
    }

    @Override
    protected void codeGenBoolByte(JavaCompiler javaCompiler, boolean negation, org.objectweb.asm.Label label) {
        codeGenExprByteOnStack(javaCompiler);
        if (negation)
            javaCompiler.getMethodVisitor().visitJumpInsn(javaCompiler.IF_ICMPNE, label);
        else
            javaCompiler.getMethodVisitor().visitJumpInsn(javaCompiler.IF_ICMPEQ, label);
    }

    @Override
    public void codeGenExprByteOnStack(JavaCompiler javaCompiler) {
        expr.codeGenExprByteOnStack(javaCompiler);
        javaCompiler.getMethodVisitor().visitMethodInsn(javaCompiler.INVOKEVIRTUAL,
                expr.getType().getName().getName(),
                methodIdent.getName().getName(),
                "(" + this.getJavaArgType() + ")" + methodIdent.getJavaType(),
                false);
    }

    private String getJavaArgType() {
        StringBuilder res = new StringBuilder();
        for(AbstractExpr expr : arguments.getList()) {
            res.append(expr.getJavaType());
        }
        return res.toString();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(".");
        methodIdent.decompile(s);
        s.print("(");
        arguments.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        methodIdent.prettyPrint(s, prefix, false);
        arguments.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        methodIdent.iter(f);
        arguments.iter(f);
    }
}
