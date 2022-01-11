package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


    @Override
    protected String getOperatorName() {
        return "%";
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
                compiler.addInstruction(new REM(Register.R0, Register.getR(register)));
            } else {
                this.getRightOperand().codeGenExprOnRegister(compiler, register + 1);
                compiler.addInstruction(new REM(Register.getR(register + 1), Register.getR(register)));
            }
        } else {
            compiler.addInstruction(new REM(dVal, Register.getR(register)));
        }
    }
}
