package fr.ensimag.deca.tree;


import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Instruction;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.SEQ;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Equals extends AbstractOpExactCmp {

    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected Instruction getCompInstr(GPRegister register) {
        return new SEQ(register);
    }

    @Override
    protected Instruction getJumpInstr(Label label, boolean negation) {
        if (negation) {
            return new BEQ(label);
        } else {
            return  new BNE(label);
        }
    }

    @Override
    protected String getOperatorName() {
        return "==";
    }

    @Override
    public int getIntJumpInstrByte(JavaCompiler javaCompiler, boolean negation) {
        if (negation)
            return javaCompiler.IF_ICMPEQ;
        else
            return javaCompiler.IF_ICMPNE;
    }

    @Override
    public int getClassJumpInstrByte(JavaCompiler javaCompiler, boolean negation) {
        if (negation)
            return javaCompiler.IF_ACMPEQ;
        else
            return javaCompiler.IF_ACMPNE;
    }

    @Override
    public int getFloatJumpInstrByte(JavaCompiler javaCompiler, boolean negation) {
        if (negation)
            return javaCompiler.IFEQ;
        else
            return javaCompiler.IFNE;
    }

    @Override
    public boolean isTriviallyTrue() {
        if (getLeftOperand().isIntLiteral() && getRightOperand().isIntLiteral()) {
            return ((IntLiteral) getLeftOperand()).getValue() == ((IntLiteral) getRightOperand()).getValue();
        }
        if (getLeftOperand().isFloatLiteral() && getRightOperand().isFloatLiteral()) {
            return ((FloatLiteral) getLeftOperand()).getValue() == ((FloatLiteral) getRightOperand()).getValue();
        }
        return false;
    }

    @Override
    public boolean isTriviallyFalse() {
        if (getLeftOperand().isIntLiteral() && getRightOperand().isIntLiteral()) {
            return ((IntLiteral) getLeftOperand()).getValue() != ((IntLiteral) getRightOperand()).getValue();
        }
        if (getLeftOperand().isFloatLiteral() && getRightOperand().isFloatLiteral()) {
            return ((FloatLiteral) getLeftOperand()).getValue() != ((FloatLiteral) getRightOperand()).getValue();
        }
        return false;
    }
}
