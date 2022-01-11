package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type leftType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rightType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if ((leftType.isInt() || leftType.isFloat()) && (rightType.isInt() || rightType.isFloat())) {
            setType(compiler.getEnvironmentType().get(compiler.getSymbolTable().create("boolean")).getType());
        } else {
            throw new ContextualError("Comparison operation: " + getOperatorName() + " only accept ([int|float], [int|float]) or objects for == and !=, as operands type", getLocation());
        }
        return getType();
    }


}
