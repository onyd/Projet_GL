package fr.ensimag.deca.tree;

import fr.ensimag.deca.codegen.Utils;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type = getOperand().verifyExpr(compiler, localEnv, currentClass);
        if (!type.isBoolean()) {
            throw new ContextualError("(3.37) Not operator only accept boolean operand", getLocation());
        }
        setType(type);
        return getType();
    }

    @Override
    public void codeGenExprOnRegister(DecacCompiler compiler, int register) {
        codeGenExprOnRegister(compiler, register, true);
    }

    protected void codeGenBool(DecacCompiler compiler, boolean negation, Label label) {
        getOperand().codeGenBool(compiler, !negation, label);
    }

    @Override
    protected String getOperatorName() {
        return "!";
    }
}
