package fr.ensimag.deca.tree;

import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import org.objectweb.asm.MethodVisitor;

import java.io.PrintStream;

/**
 * Integer literal
 *
 * @author gl28
 * @date 01/01/2022
 */
public class IntLiteral extends AbstractExpr {
    public int getValue() {
        return value;
    }

    private int value;

    public IntLiteral(int value) {
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type = compiler.getEnvironmentType().get(compiler.INT_SYMBOL).getType();
        this.setType(type);
        return getType();
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new LOAD(this.value, Register.R1));
        compiler.addInstruction(new WINT());
    }

    @Override
    public void codeGenExprOnRegister(DecacCompiler compiler, GPRegister register) {
        compiler.addInstruction(new LOAD(new ImmediateInteger(this.value), register));
    }

    @Override
    public void codeGenExprByteOnStack(JavaCompiler javaCompiler) {
        MethodVisitor methodVisitor = javaCompiler.getMethodVisitor();
        methodVisitor.visitLdcInsn(value);
    }

    @Override
    public DVal getDVal() {
        return new ImmediateInteger((this.value));
    }

    @Override
    String prettyPrintNode() {
        return "Int (" + getValue() + ")";
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Integer.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

}
