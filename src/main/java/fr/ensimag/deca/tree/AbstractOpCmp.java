package fr.ensimag.deca.tree;

import fr.ensimag.deca.codegen.LabelManager;
import fr.ensimag.deca.codegen.RegisterAllocator;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.*;
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

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type leftType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rightType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if ((leftType.isInt() || leftType.isFloat()) && (rightType.isInt() || rightType.isFloat())) {
            setType(compiler.getEnvironmentType().get(compiler.getSymbolTable().create("boolean")).getType());
        } else {
            throw new ContextualError("(3.33) Comparison operation: " + getOperatorName() + " only accept ([int|float], [int|float]) or objects for == and !=, as operands type", getLocation());
        }
        return getType();
    }

    @Override
    public void codeGenExprOnRegister(DecacCompiler compiler, GPRegister register) {
        getLeftOperand().codeGenExprOnRegister(compiler, Register.R0);
        getRightOperand().codeGenExprOnRegister(compiler, register);
        compiler.addInstruction(new CMP(register, Register.R0));
        compiler.addInstruction(getCompInstr(register));
    }

    protected void codeGenBool(DecacCompiler compiler, boolean negation, Label label) {
        Label endLabel = compiler.getManageCodeGen().getLabelManager().getNextLabel(getClass().getSimpleName().toUpperCase(), "END");

        RegisterAllocator allocator = new RegisterAllocator();
        VirtualRegister leftOperand = new VirtualRegister();
        allocator.addVirtualRegister(leftOperand);

        allocator.allocateRegisters(compiler);

        getLeftOperand().codeGenExprOnRegister(compiler, leftOperand);
        this.getRightOperand().codeGenExprOnRegister(compiler, Register.R0);
        compiler.addInstruction(new CMP(Register.R0, leftOperand));

        allocator.restoreFromStack(compiler);

        // True result label
        compiler.addInstruction(getJumpInstr(label, negation));

        // False result label
        compiler.addLabel(endLabel);
    }

    protected abstract Instruction getCompInstr(GPRegister register);

    protected abstract Instruction getJumpInstr(Label label, boolean negation);
}
