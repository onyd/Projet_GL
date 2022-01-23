package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

    @Override
    public void codeMnemo(IMACompiler compiler, DVal dVal, GPRegister register) {
        if(this.getType().isFloat()) {
            compiler.addInstruction(new DIV(dVal, register));
        } else if(this.getType().isInt()) {
            compiler.addInstruction(new QUO(dVal, register));
        }
    }

    @Override
    public int codeMnemoByte(JavaCompiler javaCompiler) {
        if(this.getType().isFloat()) {
            return javaCompiler.FDIV;
        } else if(this.getType().isInt()) {
            return javaCompiler.IDIV;
        }
        return 0;
    }

    public boolean codeGenPowerOfTwo(IMACompiler compiler, GPRegister register) {
        if (getRightOperand().isIntLiteral()) {
            getLeftOperand().codeGenExprOnRegister(compiler, register);
            int value = ((IntLiteral) getRightOperand()).getValue();

            // Trivial cases
            if (value == 0) {
                return false;
            }
            if (value == 1) {
                getLeftOperand().codeGenExprOnRegister(compiler, register);
                return true;
            }

            // Shifts
            IMAProgram shifts = new IMAProgram();
            while (value % 2 == 1) {
                shifts.addInstruction(new SHR(register));
                value /= 2;
            }

            if (value == 1) {
                compiler.append(shifts);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public Integer getDirectInt() {
        if (getType().isInt()) {
            Integer leftValue = getLeftOperand().getDirectInt();
            Integer rightValue = getRightOperand().getDirectInt();
            if (leftValue != null && rightValue != null && rightValue != 0)
                return leftValue / rightValue;
        }
        return null;
    }

    @Override
    public Float getDirectFloat() {
        if (getType().isFloat()) {
            Float leftValue = getLeftOperand().getDirectFloat();
            Float rightValue = getRightOperand().getDirectFloat();
            if (leftValue != null && rightValue != null && rightValue != 0.0)
                return leftValue / rightValue;
        }
        return null;
    }
}
