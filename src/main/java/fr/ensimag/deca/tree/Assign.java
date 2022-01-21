package fr.ensimag.deca.tree;

import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
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
    protected void codeGenInst(IMACompiler compiler) {
        this.getRightOperand().codeGenExprOnR1(compiler);
        if(this.getLeftOperand().isIdentifier()) {
            compiler.getStack().setVariableOnStack((Identifier) this.getLeftOperand(), Register.R1);
        } else if(this.getLeftOperand().isSelection()) {
            ((Selection) this.getLeftOperand()).codeGenAssignFromR1(compiler);
        }
    }

    @Override
    protected void codeGenInstByte(JavaCompiler javaCompiler) {
        getRightOperand().codeGenExprByteOnStack(javaCompiler);
        if(this.getLeftOperand().isIdentifier()) {
            if(this.getType().isFloat()) {
                javaCompiler.getMethodVisitor().visitVarInsn(javaCompiler.FSTORE, ((Identifier) this.getLeftOperand()).getExpDefinition().getIndexOnStack());
            } else if(this.getType().isInt()) {
                javaCompiler.getMethodVisitor().visitVarInsn(javaCompiler.ISTORE, ((Identifier) this.getLeftOperand()).getExpDefinition().getIndexOnStack());
            }
        }
    }

    @Override
    public void codeGenExprOnRegister(IMACompiler compiler, GPRegister register) {
        this.codeGenInst(compiler);
    }

    @Override
    protected void codeGenBool(IMACompiler compiler, boolean negation, Label label) {
        this.codeGenInst(compiler);
        getLeftOperand().codeGenBool(compiler, negation, label);
    }

    @Override
    protected void codeGenBoolByte(JavaCompiler javaCompiler, boolean negation, org.objectweb.asm.Label label) {
        this.codeGenInstByte(javaCompiler);
        getLeftOperand().codeGenBoolByte(javaCompiler, negation, label);
    }

    @Override
    protected String getOperatorName() {
        return "=";
    }

}
