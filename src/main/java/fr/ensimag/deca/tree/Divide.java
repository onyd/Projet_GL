package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Register;
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
    public void codeGenExprOnRegister(DecacCompiler compiler, int register) {
        this.getLeftOperand().codeGenExprOnRegister(compiler, register);
        DVal dVal = this.getRightOperand().getDVal();
        if(dVal == null) {
            int newRegister = compiler.getManageCodeGen().getRegisterManager().getFreeRegister();
            if(newRegister == -1) {
                compiler.addInstruction(new PUSH(Register.getR(register)));
                this.getRightOperand().codeGenExprOnRegister(compiler, register);
                compiler.addInstruction(new LOAD(Register.getR(register), Register.R0));
                compiler.addInstruction(new POP(Register.getR(register)));
                if(this.getType().isInt()) {
                    compiler.addInstruction(new QUO(Register.R0, Register.getR(register)));
                } else if(this.getType().isFloat()) {
                    compiler.addInstruction(new DIV(Register.R0, Register.getR(register)));
                }
            } else {
                this.getRightOperand().codeGenExprOnRegister(compiler, newRegister);
                if(this.getType().isInt()) {
                    compiler.addInstruction(new QUO(Register.getR(newRegister), Register.getR(register)));
                } else if(this.getType().isFloat()) {
                    compiler.addInstruction(new DIV(Register.getR(newRegister), Register.getR(register)));
                }
                compiler.getManageCodeGen().getRegisterManager().releaseRegister(newRegister);
            }
        } else {
            if(this.getType().isInt()) {
                compiler.addInstruction(new QUO(dVal, Register.getR(register)));
            } else if(this.getType().isFloat()) {
                compiler.addInstruction(new DIV(dVal, Register.getR(register)));
            }
        }
    }
}
