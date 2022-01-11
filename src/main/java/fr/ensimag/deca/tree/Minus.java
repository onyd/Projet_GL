package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 * @author gl28
 * @date 01/01/2022
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "-";
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
                compiler.addInstruction(new SUB(Register.R0, Register.getR(register)));
            } else {
                this.getRightOperand().codeGenExprOnRegister(compiler, register + 1);
                compiler.addInstruction(new SUB(Register.getR(register + 1), Register.getR(register)));
            }
        } else {
            compiler.addInstruction(new SUB(dVal, Register.getR(register)));
        }
    }
}
