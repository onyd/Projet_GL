package fr.ensimag.deca.tree;

import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.LabelManager;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;

/**
 * Conversion of an int into a float. Used for implicit conversions.
 * 
 * @author gl28
 * @date 01/01/2022
 */
public class ConvFloat extends AbstractUnaryExpr {
    public ConvFloat(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError{
        setType(compiler.getEnvironmentType().get(compiler.FLOAT_SYMBOL).getType());
        return getType();
    }

    @Override
    public void codeGenExprOnRegister(DecacCompiler compiler, GPRegister register) {
        this.getOperand().codeGenExprOnRegister(compiler, register);
        compiler.addInstruction(new FLOAT(register, register));
        if(!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(LabelManager.CAST_ERROR));
        }
    }

    @Override
    public void codeGenExprByteOnStack(DecacCompiler compiler, JavaCompiler javaCompiler) {
        this.getOperand().codeGenExprByteOnStack(compiler, javaCompiler);
        javaCompiler.getMethodVisitor().visitInsn(javaCompiler.I2F);
    }

    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }

}
