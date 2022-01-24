package fr.ensimag.deca.tree;

import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.LabelManager;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
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

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass) throws ContextualError {
        Type leftType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rightType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if (leftType.isInt() && rightType.isInt()) {
            setType(compiler.getEnvironmentType().get(compiler.INT_SYMBOL).getType());
        } else if (leftType.isInt() && rightType.isFloat()){
            setType(compiler.getEnvironmentType().get(compiler.FLOAT_SYMBOL).getType());
            ConvFloat newLeftOperand = new ConvFloat(getLeftOperand());
            setLeftOperand(newLeftOperand);
            newLeftOperand.verifyExpr(compiler, localEnv, currentClass);
        } else if (leftType.isFloat() && rightType.isInt()) {
            setType(compiler.getEnvironmentType().get(compiler.FLOAT_SYMBOL).getType());
            ConvFloat newRightOperand = new ConvFloat(getRightOperand());
            setRightOperand(newRightOperand);
            newRightOperand.verifyExpr(compiler, localEnv, currentClass);
        } else if (leftType.isFloat() && leftType.isFloat()) {
            setType(compiler.getEnvironmentType().get(compiler.FLOAT_SYMBOL).getType());
        } else {
            throw new ContextualError("(3.33) Arithmetic operation: " + getOperatorName() + " only accept ([int|float], [int|float]) as operands type", getLocation());
        }
        return getType();
    }

    @Override
    protected void codeGenPrint(IMACompiler compiler, boolean printHex) {
        codeGenExprOnR1(compiler);
        if(this.getType().isInt()) {
            compiler.addInstruction(new WINT());
        } else if(this.getType().isFloat()) {
            if (printHex) {
                compiler.addInstruction(new WFLOATX());
            } else {
                compiler.addInstruction(new WFLOAT());
            }
        }
    }

    @Override
    protected void codeGenInst(IMACompiler compiler) {
    }

    @Override
    public void codeGenExprOnRegister(IMACompiler compiler, GPRegister register) {
        // Constant optimization
        if (codeGenConstInt(compiler, register)) {
            return;
        }
        if (codeGenConstFloat(compiler, register)) {
            return;
        }

        // If power of 2 => shifts are faster for * and /
        if (this.codeGenPowerOfTwo(compiler, register))
            return;

        // Classic code generation
        this.getLeftOperand().codeGenExprOnRegister(compiler, register);
        DVal dVal = this.getRightOperand().getDVal();
        if(dVal == null) {
            GPRegister newRegister = compiler.getRegisterManager().getFreeRegister();
            if(newRegister == null) {
                compiler.addInstruction(new PUSH(register));
                this.getRightOperand().codeGenExprOnRegister(compiler, register);
                compiler.addInstruction(new LOAD(register, Register.R0));
                compiler.addInstruction(new POP(register));
                this.codeMnemo(compiler, Register.R0, register);
                if(!compiler.getCompilerOptions().getNoCheck()) {
                    if(Objects.equals(this.getOperatorName(), "/")) {
                        compiler.addInstruction(new BOV(LabelManager.DIV_ERROR));
                    } else if (getType().isFloat()){
                        compiler.addInstruction(new BOV(LabelManager.OVERFLOW_ERROR));
                    }
                }
            } else {
                this.getRightOperand().codeGenExprOnRegister(compiler, newRegister);
                this.codeMnemo(compiler, newRegister, register);
                if(!compiler.getCompilerOptions().getNoCheck()) {
                    if(Objects.equals(this.getOperatorName(), "/")) {
                        compiler.addInstruction(new BOV(LabelManager.DIV_ERROR));
                    } else if (getType().isFloat()){
                        compiler.addInstruction(new BOV(LabelManager.OVERFLOW_ERROR));
                    }
                }
                compiler.getRegisterManager().releaseRegister(newRegister);
            }
        } else {
            this.codeMnemo(compiler, dVal, register);
            if(!compiler.getCompilerOptions().getNoCheck()) {
                if(Objects.equals(this.getOperatorName(), "/")) {
                    compiler.addInstruction(new BOV(LabelManager.DIV_ERROR));
                } else if (getType().isFloat()){
                    compiler.addInstruction(new BOV(LabelManager.OVERFLOW_ERROR));
                }
            }
        }
    }

    @Override
    public void codeGenExprByteOnStack(JavaCompiler javaCompiler) {
        getLeftOperand().codeGenExprByteOnStack(javaCompiler);
        getRightOperand().codeGenExprByteOnStack(javaCompiler);
        javaCompiler.getMethodVisitor().visitInsn(codeMnemoByte(javaCompiler));
    }

    protected boolean codeGenPowerOfTwo(IMACompiler compiler, GPRegister register) {
        return false;
    }

    protected boolean codeGenConstFloat(IMACompiler compiler, GPRegister register) {
        Float fValue = getDirectFloat();
        if (fValue != null) {
            compiler.addInstruction(new LOAD(new ImmediateFloat(fValue), register));
            return true;
        }

        return false;
    }

    protected boolean codeGenConstInt(IMACompiler compiler, GPRegister register) {
        Integer iValue = getDirectInt();
        if (iValue != null) {
            compiler.addInstruction(new LOAD(iValue, register));
            return true;
        }

        return false;
    }

}
