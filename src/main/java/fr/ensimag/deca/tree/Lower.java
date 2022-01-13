package fr.ensimag.deca.tree;


import fr.ensimag.ima.pseudocode.Instruction;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Lower extends AbstractOpIneq {

    public Lower(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected Instruction getCompInstr(int register) {
        return new SLT(Register.getR(register));
    }

    @Override
    protected Instruction getJumpInstr(Label label, boolean negation) {
        if (negation) {
            return new BLT(label);
        } else {
            return  new BGE(label);
        }
    }


    @Override
    protected String getOperatorName() {
        return "<";
    }

}
