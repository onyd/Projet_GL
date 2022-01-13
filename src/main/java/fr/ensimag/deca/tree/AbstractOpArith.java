package fr.ensimag.deca.tree;

import fr.ensimag.deca.codegen.LabelManager;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.util.Objects;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl28
 * @date 01/01/2022
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    /**
     * Verify that arithmetic context is respected and decorates the node accordingly
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

        if (leftType.isInt() && rightType.isInt()) {
            setType(compiler.getEnvironmentType().get(compiler.getSymbolTable().create("int")).getType());
        } else if (leftType.isInt() && rightType.isFloat()){
            setType(compiler.getEnvironmentType().get(compiler.getSymbolTable().create("float")).getType());
        } else if (leftType.isFloat() && rightType.isInt()) {
            setType(compiler.getEnvironmentType().get(compiler.getSymbolTable().create("float")).getType());
        } else if (leftType.isFloat() && leftType.isFloat()) {
            setType(compiler.getEnvironmentType().get(compiler.getSymbolTable().create("float")).getType());
        } else {
            throw new ContextualError("Arithmetic operation: " + getOperatorName() + " only accept ([int|float], [int|float]) as operands type", getLocation());
        }
        return getType();
    }

    /**
     * Generate assembly for numeric print
     * @param compiler
     */
    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        codeGenExprOnR1(compiler);
        if(this.getType().isInt()) {
            compiler.addInstruction(new WINT());
        } else if(this.getType().isFloat()) {
            compiler.addInstruction(new WFLOAT());
        }
    }


    @Override
    protected void codeGenInst(DecacCompiler compiler) {

    }

    /**
     * Generate assembly for arithmetic operations
     * @param compiler
     * @param register
     */
    @Override
    public void codeGenExprOnRegister(DecacCompiler compiler, int register) {
        this.getLeftOperand().codeGenExprOnRegister(compiler, register);
        DVal dVal = this.getRightOperand().getDVal();
        if(dVal == null) {
            int newRegister = compiler.getManageCodeGen().getRegisterManager().getFreeRegister();
            if(newRegister == -1) {
                compiler.addInstruction(new PUSH(Register.getR(register)));
                this.getRightOperand().codeGenExprOnRegister(compiler, register);
                compiler.addInstruction(new LOAD(Register.getR(register), Register.R0));
                compiler.addInstruction(new POP(Register.getR(register)));
                this.codeMnemo(compiler, Register.R0, register);
                if(!compiler.getCompilerOptions().getNoCheck()) {
                    if(Objects.equals(this.getOperatorName(), "/")) {
                        compiler.addInstruction(new BOV(LabelManager.DIV_ERROR));
                    } else {
                        compiler.addInstruction(new BOV(LabelManager.OVERFLOW_ERROR));
                    }
                }
            } else {
                this.getRightOperand().codeGenExprOnRegister(compiler, newRegister);
                this.codeMnemo(compiler, Register.getR(newRegister), register);
                if(!compiler.getCompilerOptions().getNoCheck()) {
                    if(Objects.equals(this.getOperatorName(), "/")) {
                        compiler.addInstruction(new BOV(LabelManager.DIV_ERROR));
                    } else {
                        compiler.addInstruction(new BOV(LabelManager.OVERFLOW_ERROR));
                    }
                }
                compiler.getManageCodeGen().getRegisterManager().releaseRegister(newRegister);
            }
        } else {
            this.codeMnemo(compiler, dVal, register);
            if(!compiler.getCompilerOptions().getNoCheck()) {
                if(Objects.equals(this.getOperatorName(), "/")) {
                    compiler.addInstruction(new BOV(LabelManager.DIV_ERROR));
                } else {
                    compiler.addInstruction(new BOV(LabelManager.OVERFLOW_ERROR));
                }
            }
        }
    }
}
