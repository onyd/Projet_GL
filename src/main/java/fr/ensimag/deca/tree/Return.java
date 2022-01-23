package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class Return extends  AbstractInst {
    private AbstractExpr expr;

    public Return(AbstractExpr expr) {
        Validate.notNull(expr);
        this.expr = expr;
    }

    public void setExpression(AbstractExpr expr) {
        this.expr = expr;
    }

    public AbstractExpr getExpression() {
        return expr;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        setExpression(expr.verifyRValue(compiler, localEnv, currentClass, returnType));
        if (expr.getType().isVoid()) {
            throw new ContextualError("(3.24) Return value can't be void", getLocation());
        }
    }

    @Override
    protected void codeGenInst(IMACompiler compiler) {
        expr.codeGenExprOnRegister(compiler, Register.R0);
        MethodDefinition methDef = compiler.getCurrentMethod();
        if (methDef != null)
            compiler.addInstruction(new BRA(methDef.getEndLabel()));
    }

    @Override
    protected void codeGenInstByte(JavaCompiler javaCompiler) {
        expr.codeGenExprByteOnStack(javaCompiler);
        if(expr.getType().isInt() || expr.getType().isBoolean()) {
            javaCompiler.getMethodVisitor().visitInsn(javaCompiler.IRETURN);
        } else if(expr.getType().isFloat()) {
            javaCompiler.getMethodVisitor().visitInsn(javaCompiler.FRETURN);
        } else if(expr.getType().isClass()) {
            javaCompiler.getMethodVisitor().visitInsn(javaCompiler.ARETURN);
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        expr.decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
    }
}
