package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Utils;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }

    public void codeGenExprOnRegister(DecacCompiler compiler, GPRegister register) {
        codeGenExprOnRegister(compiler, register);
    }

    protected void codeGenBool(DecacCompiler compiler, boolean negation, Label label) {
        Label endLabel = compiler.getManageCodeGen().getLabelManager().getNextLabel(getClass().getSimpleName().toUpperCase(), "END");

        if (!negation) {
            getLeftOperand().codeGenBool(compiler, true, endLabel);
            getRightOperand().codeGenBool(compiler, false, label);
        } else {
            getLeftOperand().codeGenBool(compiler, true, label);
            getRightOperand().codeGenBool(compiler, true, label);
        }
        compiler.addLabel(endLabel);
    }

}
