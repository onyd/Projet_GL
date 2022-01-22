package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.LabelManager;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.util.Objects;

/**
 * @author gl28
 * @date 01/01/2022
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "*";
    }

    @Override
    public void codeMnemo(IMACompiler compiler, DVal dVal, GPRegister register) {
        compiler.addInstruction(new MUL(dVal, register));
    }

    @Override
    public int codeMnemoByte(JavaCompiler javaCompiler) {
        if(this.getType().isFloat()) {
            return javaCompiler.FMUL;
        } else if(this.getType().isInt()) {
            return javaCompiler.IMUL;
        }
        return 0;
    }

    private boolean intMultPowerOfTwo(IMACompiler compiler, GPRegister register, AbstractExpr e1, AbstractExpr e2) {
        e2.codeGenExprOnRegister(compiler, register);
        int value = ((IntLiteral) e1).getValue();

        // Trivial cases
        if (value == 1) {
            e2.codeGenExprOnRegister(compiler, register);
            return true;
        }
        if (value == 0) {
            compiler.addInstruction(new LOAD(0, register));
        }

        // Shifts
        IMAProgram shifts = new IMAProgram();
        while (value % 2 == 0) {
            shifts.addInstruction(new SHL(register));
            value /= 2;
        }
        if (value == 1) {
            compiler.append(shifts);
            return true;
        } else {
            return false;
        }
    }

    public boolean codeGenPowerOfTwo(IMACompiler compiler, GPRegister register) {
        if (getLeftOperand().isIntLiteral()) {
            return intMultPowerOfTwo(compiler, register, getLeftOperand(), getRightOperand());
        } else if (getRightOperand().isIntLiteral()) {
            return intMultPowerOfTwo(compiler, register, getRightOperand(), getLeftOperand());
        }
        return false;
    }

    @Override
    public boolean codeGenConstants(IMACompiler compiler, GPRegister register) {
        if (getLeftOperand().isIntLiteral() && getRightOperand().isIntLiteral()) {
            int value = ((IntLiteral) getLeftOperand()).getValue() * ((IntLiteral) getRightOperand()).getValue();
            compiler.addInstruction(new LOAD(value, register));
            return true;
        } else if (getLeftOperand().isFloatLiteral() && getRightOperand().isFloatLiteral()) {
            float value = ((FloatLiteral) getLeftOperand()).getValue() * ((FloatLiteral) getRightOperand()).getValue();
            compiler.addInstruction(new LOAD(new ImmediateFloat(value), register));
            return true;
        }
        return false;
    }

}
