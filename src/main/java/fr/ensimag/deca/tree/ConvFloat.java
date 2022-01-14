package fr.ensimag.deca.tree;

import fr.ensimag.deca.codegen.LabelManager;
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
            ClassDefinition currentClass) {
        throw new UnsupportedOperationException("not yet implemented");
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
    protected String getOperatorName() {
        return "/* conv float */";
    }

}
