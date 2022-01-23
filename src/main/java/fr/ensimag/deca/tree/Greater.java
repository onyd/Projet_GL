package fr.ensimag.deca.tree;


import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Instruction;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Greater extends AbstractOpIneq {

    public Greater(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected Instruction getCompInstr(GPRegister register) {
        return new SGT(register);
    }

    @Override
    protected Instruction getJumpInstr(Label label, boolean negation) {
        if (negation) {
            return new BGT(label);
        } else {
            return  new BLE(label);
        }
    }


    @Override
    protected String getOperatorName() {
        return ">";
    }

    @Override
    public int getIntJumpInstrByte(JavaCompiler javaCompiler, boolean negation) {
        if (negation)
            return javaCompiler.IF_ICMPGT;
        else
            return javaCompiler.IF_ICMPLE;
    }

    @Override
    public int getFloatJumpInstrByte(JavaCompiler javaCompiler, boolean negation) {
        if (negation)
            return javaCompiler.IFGT;
        else
            return javaCompiler.IFLE;
    }

    @Override
    public boolean isTriviallyTrue() {
        if (getLeftOperand().isIntLiteral() && getRightOperand().isIntLiteral()) {
            return ((IntLiteral) getLeftOperand()).getValue() > ((IntLiteral) getRightOperand()).getValue();
        }
        if (getLeftOperand().isFloatLiteral() && getRightOperand().isFloatLiteral()) {
            return ((FloatLiteral) getLeftOperand()).getValue() > ((FloatLiteral) getRightOperand()).getValue();
        }
        return false;
    }

    @Override
    public boolean isTriviallyFalse() {
        if (getLeftOperand().isIntLiteral() && getRightOperand().isIntLiteral()) {
            return ((IntLiteral) getLeftOperand()).getValue() <= ((IntLiteral) getRightOperand()).getValue();
        }
        if (getLeftOperand().isFloatLiteral() && getRightOperand().isFloatLiteral()) {
            return ((FloatLiteral) getLeftOperand()).getValue() <= ((FloatLiteral) getRightOperand()).getValue();
        }
        return false;
    }
}
