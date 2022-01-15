package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.Register;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type = getRightOperand().verifyRValue(compiler, localEnv, currentClass, getLeftOperand().getType()).getType();
        setType(type);
        setRightOperand(getRightOperand().verifyRValue(compiler, localEnv, currentClass, getLeftOperand().getType()));
        return getType();
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        this.getRightOperand().codeGenExprOnR1(compiler);
        compiler.getStack().setVariableOnStack((Identifier) this.getLeftOperand(), Register.R1);
    }

    @Override
    protected String getOperatorName() {
        return "=";
    }

}
