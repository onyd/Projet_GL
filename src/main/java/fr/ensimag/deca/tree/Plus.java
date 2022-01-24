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
    public void codeGenExprOnRegister(IMACompiler compiler, GPRegister register) {
        /*if (getType().isFloat()) {
            if (getLeftOperand().isMultiply()) {
                Multiply multiplication = (Multiply) getLeftOperand();
                doFMAOptimization(compiler, register, multiplication.getLeftOperand(), multiplication.getRightOperand(), getRightOperand());
                return;
            }
            if (getRightOperand().isMultiply()) {
                Multiply multiplication = (Multiply) getRightOperand();
                doFMAOptimization(compiler, register, multiplication.getLeftOperand(), multiplication.getRightOperand(), getLeftOperand());
                return;
            }
        }*/
        super.codeGenExprOnRegister(compiler, register);
    }

    private void doFMAOptimization(IMACompiler compiler, GPRegister register, AbstractExpr e1, AbstractExpr e2, AbstractExpr toAdd) {
        DVal e1DVal = e1.getDVal();
        if (e1DVal != null) {
            e2.codeGenExprOnRegister(compiler, register);
            toAdd.codeGenExprOnRegister(compiler, Register.R1);
            compiler.addInstruction(new FMA(e1DVal, register));
            return;
        }

        DVal e2DVal = e2.getDVal();
        if (e2DVal != null) {
            e1.codeGenExprOnRegister(compiler, register);
            toAdd.codeGenExprOnRegister(compiler, Register.R1);
            compiler.addInstruction(new FMA(e2DVal, register));
            return;
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
