package fr.ensimag.deca.tree;

import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.ima.pseudocode.instructions.WINT;

/**
 * @author gl28
 * @date 01/01/2022
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass) throws ContextualError {
        Type type = getOperand().verifyExpr(compiler, localEnv, currentClass);
        if (!type.isInt() && !type.isFloat()) {
            throw new ContextualError("(3.37) UnaryMinus operator only accept int or float operand", getLocation());
        }
        setType(type);
        return getType();
    }

    @Override
    protected String getOperatorName() {
        return "-";
    }

    @Override
    public void codeGenExprOnRegister(IMACompiler compiler, GPRegister register) {
        this.getOperand().codeGenExprOnRegister(compiler, register);
        compiler.addInstruction(new OPP(register, register));
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
    public void codeGenExprByteOnStack(JavaCompiler javaCompiler) {
        getOperand().codeGenExprByteOnStack(javaCompiler);
        if(this.getType().isInt()) {
            javaCompiler.getMethodVisitor().visitInsn(javaCompiler.INEG);
        } else if(this.getType().isFloat()) {
            javaCompiler.getMethodVisitor().visitInsn(javaCompiler.FNEG);
        }
    }

    @Override
    public Integer getDirectInt() {
        if (getType().isInt()) {
            Integer value = getOperand().getDirectInt();
            if (value != null)
                return value;
        }
        return null;
    }

    @Override
    public Float getDirectFloat() {
        if (getType().isInt()) {
            Float value = getOperand().getDirectFloat();
            if (value != null)
                return -value;
        }
        return null;
    }
}
