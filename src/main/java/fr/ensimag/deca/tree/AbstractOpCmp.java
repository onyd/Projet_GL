package fr.ensimag.deca.tree;

import fr.ensimag.deca.codegen.LabelManager;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.Instruction;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.util.Objects;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    /**
     * Verify that comparison context is respected and decorates the node accordingly
     * @param compiler  (contains the "env_types" attribute)
     * @param localEnv
     *            Environment in which the expression should be checked
     *            (corresponds to the "env_exp" attribute)
     * @param currentClass
     *            Definition of the class containing the expression
     *            (corresponds to the "class" attribute)
     *             is null in the main bloc.
     * @return
     * @throws ContextualError
     */
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

    /**
     * Generate assembly for comparison expression on the specified register
     * @param compiler
     * @param register
     */
    @Override
    public void codeGenExprOnRegister(DecacCompiler compiler, int register) {
        getLeftOperand().codeGenExprOnRegister(compiler, 0);
        getRightOperand().codeGenExprOnRegister(compiler, register);
        compiler.addInstruction(new CMP(Register.getR(register), Register.R0));
        compiler.addInstruction(getCompInstr(register));
    }

    /**
     * Generate assembly for inner comparison expression
     * @param compiler
     * @param negation should the expression evaluated tu true if negation
     * @param label the true label
     */
    protected void codeGenBool(DecacCompiler compiler, boolean negation, Label label) {
        Label endLabel = compiler.getManageCodeGen().getLabelManager().getNextLabel(getClass().getSimpleName().toUpperCase(), "END");

        getLeftOperand().codeGenExprOnRegister(compiler, 0);

        // Store current expr result
        this.getRightOperand().codeGenExprOnRegister(compiler, 2);
        compiler.addInstruction(new CMP(Register.getR(2), Register.R0));

        // True result label
        compiler.addInstruction(getJumpInstr(label, negation));

        // False result label
        compiler.addLabel(endLabel);
    }

    /**
     * Return the comparison Instruction used in codeGenExprOnRegister
     * @param register
     * @return
     */
    protected abstract Instruction getCompInstr(int register);

    /**
     * Return the jump Instruction used in codeGenBool
     * @param label
     * @param negation
     * @return
     */
    protected abstract Instruction getJumpInstr(Label label, boolean negation);
}
