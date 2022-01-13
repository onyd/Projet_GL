package fr.ensimag.deca.tree;


import fr.ensimag.ima.pseudocode.Instruction;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.SEQ;
import fr.ensimag.ima.pseudocode.instructions.SNE;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class NotEquals extends AbstractOpExactCmp {

    public NotEquals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected Instruction getCompInstr(int register) {
        return new SNE(Register.getR(register));
    }

    @Override
    protected Instruction getJumpInstr(Label label, boolean negation) {
        if (negation) {
            return new BNE(label);
        } else {
            return  new BEQ(label);
        }
    }


    @Override
    protected String getOperatorName() {
        return "!=";
    }

}
