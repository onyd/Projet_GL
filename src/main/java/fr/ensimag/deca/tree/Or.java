package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.Utils;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }

    protected void codeGenBool(IMACompiler compiler, boolean negation, Label label) {
        Label endLabel = compiler.getLabelManager().getNextLabel(getClass().getSimpleName().toUpperCase(), "END");

        if (!negation) {
            getLeftOperand().codeGenBool(compiler, true, endLabel);
            getRightOperand().codeGenBool(compiler, false, label);
        } else {
            getLeftOperand().codeGenBool(compiler, true, label);
            getRightOperand().codeGenBool(compiler, true, label);
        }
        compiler.addLabel(endLabel);
    }

    @Override
    protected void codeGenBoolByte(JavaCompiler javaCompiler, boolean negation, org.objectweb.asm.Label label) {
        org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();

        if (negation) {
            getLeftOperand().codeGenBoolByte(javaCompiler, true, endLabel);
            getRightOperand().codeGenBoolByte(javaCompiler, false, label);
        } else {
            getLeftOperand().codeGenBoolByte(javaCompiler, true, label);
            getRightOperand().codeGenBoolByte(javaCompiler, true, label);
        }
        javaCompiler.getMethodVisitor().visitLabel(endLabel);
    }

    @Override
    public int codeMnemoByte(JavaCompiler javaCompiler) {
        return javaCompiler.IOR;
    }
}
