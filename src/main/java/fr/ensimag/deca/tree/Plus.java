package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 * @author gl28
 * @date 01/01/2022
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 

    @Override
    protected String getOperatorName() {
        return "+";
    }

    @Override
    public void codeMnemo(IMACompiler compiler, DVal dVal, GPRegister register) {
        compiler.addInstruction(new ADD(dVal, register));
    }

    @Override
    public int codeMnemoByte(JavaCompiler javaCompiler) {
        if (this.getType().isInt()) {
            return javaCompiler.IADD;
        } else {
            return javaCompiler.FADD;
        }
    }

    @Override
    public Integer getDirectInt() {
        if (getType().isInt()) {
            Integer leftValue = getLeftOperand().getDirectInt();
            Integer rightValue = getRightOperand().getDirectInt();
            if (leftValue != null && rightValue != null)
                return leftValue + rightValue;
        }
        return null;
    }

    @Override
    public Float getDirectFloat() {
        if (getType().isFloat()) {
            Float leftValue = getLeftOperand().getDirectFloat();
            Float rightValue = getRightOperand().getDirectFloat();
            if (leftValue != null && rightValue != null)
                return leftValue + rightValue;
        }
        return null;
    }
}
