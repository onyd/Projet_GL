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

    private void intMultPowerOfTwo(IMACompiler compiler, GPRegister register, AbstractExpr e1, AbstractExpr e2) {
        IMAProgram shifts = new IMAProgram();
        e2.codeGenExprOnRegister(compiler, register);
        int value = ((IntLiteral) e1).getValue();
        while (value % 2 == 0 && value != 1) {
            shifts.addInstruction(new SHL(register));
            value /= 2;
        }

    }

    public boolean codeGenPowerOfTwo(IMACompiler compiler, GPRegister register) {
        if (getLeftOperand().isIntLiteral()) {
            intMultPowerOfTwo(compiler, register, getLeftOperand(), getRightOperand());
            return true;
        } else if (getRightOperand().isIntLiteral()) {
            intMultPowerOfTwo(compiler, register, getRightOperand(), getLeftOperand());
            return true;
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
