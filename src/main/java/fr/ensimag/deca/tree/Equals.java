package fr.ensimag.deca.tree;


import fr.ensimag.ima.pseudocode.Instruction;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.SEQ;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Equals extends AbstractOpExactCmp {

    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected Instruction getCompInstr(int register) {
        return new SEQ(Register.getR(register));
    }

    @Override
    protected Instruction getJumpInstr(Label label, boolean negation) {
        if (negation) {
            return new BEQ(label);
        } else {
            return  new BNE(label);
        }
    }


    @Override
    protected String getOperatorName() {
        return "==";
    }    
    
}
