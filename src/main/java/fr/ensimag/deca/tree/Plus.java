package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
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
                compiler.addInstruction(new ADD(Register.R0, Register.getR(register)));
            } else {
                this.getRightOperand().codeGenExprOnRegister(compiler, newRegister);
                compiler.addInstruction(new ADD(Register.getR(newRegister), Register.getR(register)));
                compiler.getManageCodeGen().getRegisterManager().releaseRegister(newRegister);
            }
        } else {
            compiler.addInstruction(new ADD(dVal, Register.getR(register)));
        }
    }
}
