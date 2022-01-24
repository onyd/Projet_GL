package fr.ensimag.deca.tree;

import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
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

        return verifyExpr(compiler, leftType, rightType);
    }

    public Type verifyExpr(DecacCompiler compiler, Type type1, Type type2) throws ContextualError {
        if ((type1.isInt() || type1.isFloat()) && (type2.isInt() || type2.isFloat())) {
            setType(compiler.getEnvironmentType().get(compiler.BOOLEAN_SYMBOL).getType());
            if (type1.isInt() && type2.isFloat()) {
                setLeftOperand(new ConvFloat(getLeftOperand()));
            } else if (type1.isFloat() && type2.isInt()) {
                setRightOperand(new ConvFloat(getRightOperand()));
            }
        } else {
            throw new ContextualError("(3.33) Comparison operation: " + getOperatorName() + " only accept ([int|float], [int|float]) or objects for == and !=, as operands type", getLocation());
        }
        return getType();
    }

    @Override
    public void codeGenExprOnRegister(IMACompiler compiler, GPRegister register) {
        getLeftOperand().codeGenExprOnRegister(compiler, Register.R0);
        getRightOperand().codeGenExprOnRegister(compiler, register);
        compiler.addInstruction(new CMP(register, Register.R0));
        compiler.addInstruction(getCompInstr(register));
    }

    protected void codeGenBool(IMACompiler compiler, boolean negation, Label label) {
        Label endLabel = compiler.getLabelManager().getNextLabel(getClass().getSimpleName().toUpperCase(), "END");

        RegisterAllocator allocator = new RegisterAllocator();
        VirtualRegister leftOperand = new VirtualRegister();
        allocator.addVirtualRegister(leftOperand);

        allocator.allocateRegisters(compiler);

        getLeftOperand().codeGenExprOnRegister(compiler, leftOperand);
        getRightOperand().codeGenExprOnRegister(compiler, Register.R0);
        compiler.addInstruction(new CMP(Register.R0, leftOperand));

        allocator.restoreFromStack(compiler);

        // True result label
        compiler.addInstruction(getJumpInstr(label, negation));

        // False result label
        compiler.addLabel(endLabel);
    }

    protected void codeGenBoolByte(JavaCompiler javaCompiler, boolean negation, org.objectweb.asm.Label label) {
        org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();

        getLeftOperand().codeGenExprByteOnStack(javaCompiler);
        getRightOperand().codeGenExprByteOnStack(javaCompiler);

        // True result label
        if (getLeftOperand().getType().isInt()) {
            javaCompiler.getMethodVisitor().visitJumpInsn(getIntJumpInstrByte(javaCompiler, negation), label);
        } else if (getLeftOperand().getType().isClass()) {
            javaCompiler.getMethodVisitor().visitJumpInsn(getClassJumpInstrByte(javaCompiler, negation), label);
        } else {
            javaCompiler.getMethodVisitor().visitInsn(javaCompiler.FCMPL);
            javaCompiler.getMethodVisitor().visitJumpInsn(getFloatJumpInstrByte(javaCompiler, negation), label);
        }

        // False result label
        javaCompiler.getMethodVisitor().visitLabel(endLabel);
    }

    protected abstract Instruction getCompInstr(GPRegister register);

    protected abstract Instruction getJumpInstr(Label label, boolean negation);

    public abstract int getIntJumpInstrByte(JavaCompiler javaCompiler, boolean negation);

    public abstract int getFloatJumpInstrByte(JavaCompiler javaCompiler, boolean negation);

    public int getClassJumpInstrByte(JavaCompiler javaCompiler, boolean negation) {throw new UnsupportedOperationException(); };

    @Override
    public void codeGenExprByteOnStack(JavaCompiler javaCompiler) {
        super.codeGenExprByteOnStack(javaCompiler);
    }
}
