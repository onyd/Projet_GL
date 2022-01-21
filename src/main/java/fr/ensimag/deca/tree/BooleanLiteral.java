package fr.ensimag.deca.tree;

import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import org.objectweb.asm.MethodVisitor;

import java.io.PrintStream;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass) throws ContextualError {
        Type type = compiler.getEnvironmentType().get(compiler.BOOLEAN_SYMBOL).getType();
        this.setType(type);
        return type;
    }

    @Override
    public void codeGenExprOnRegister(IMACompiler compiler, GPRegister register) {
        compiler.addInstruction(new LOAD(getDVal(), register));
    }

    @Override
    public void codeGenExprByteOnStack(JavaCompiler javaCompiler) {
        MethodVisitor methodVisitor = javaCompiler.getMethodVisitor();
        if (value) {
            methodVisitor.visitLdcInsn(1);
        } else {
            methodVisitor.visitLdcInsn(0);
        }
    }

    protected void codeGenBool(IMACompiler compiler, boolean negation, Label label) {
        if ((value && negation) || (!value && !negation)) {
                compiler.addInstruction(new BRA(label));
        }
    }

    @Override
    protected void codeGenBoolByte(JavaCompiler javaCompiler, boolean negation, org.objectweb.asm.Label label) {
        if ((value && negation) || (!value && !negation)) {
            javaCompiler.getMethodVisitor().visitJumpInsn(javaCompiler.GOTO, label);
        }
    }

    @Override
    public DVal getDVal() {
        if(value) {
            return new ImmediateInteger(1);
        }
        return new ImmediateInteger(0);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

}
