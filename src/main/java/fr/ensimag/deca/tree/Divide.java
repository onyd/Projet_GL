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
    public void codeMnemo(DecacCompiler compiler, DVal dVal, int register) {
        if(this.getType().isFloat()) {
            compiler.addInstruction(new DIV(dVal, Register.getR(register)));
        } else if(this.getType().isInt()) {
            compiler.addInstruction(new QUO(dVal, Register.getR(register)));
        }
    }
}
