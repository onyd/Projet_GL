package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.Utils;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

    protected void codeGenBool(IMACompiler compiler, boolean negation, Label label) {
        Label endLabel = compiler.getLabelManager().getNextLabel(getClass().getSimpleName().toUpperCase(), "END");

        if (negation) {
            getLeftOperand().codeGenBool(compiler, false, endLabel);
            getRightOperand().codeGenBool(compiler, true, label);
        } else {
            getLeftOperand().codeGenBool(compiler, false, label);
            getRightOperand().codeGenBool(compiler, false, label);
        }
        compiler.addLabel(endLabel);
    }

    @Override
    protected void codeGenBoolByte(JavaCompiler javaCompiler, boolean negation, org.objectweb.asm.Label label) {
        org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();

        if (negation) {
            getLeftOperand().codeGenBoolByte(javaCompiler, false, endLabel);
            getRightOperand().codeGenBoolByte(javaCompiler, true, label);
        } else {
            getLeftOperand().codeGenBoolByte(javaCompiler, false, label);
            getRightOperand().codeGenBoolByte(javaCompiler, false, label);
        }
        javaCompiler.getMethodVisitor().visitLabel(endLabel);
    }

    @Override
    public int codeMnemoByte(JavaCompiler javaCompiler) {
        return javaCompiler.IAND;
    }
}
