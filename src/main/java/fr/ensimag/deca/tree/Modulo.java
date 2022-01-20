package fr.ensimag.deca.tree;

import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type leftType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rightType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if (leftType.isInt() && rightType.isInt()) {
            setType(compiler.getEnvironmentType().get(compiler.INT_SYMBOL).getType());
        } else {
            throw new ContextualError("(3.33) Arithmetic operation only: " + getOperatorName() + " accept (int, int) as operands type", getLocation());
        }
        return getType();
    }


    @Override
    protected String getOperatorName() {
        return "%";
    }

    @Override
    public void codeMnemo(DecacCompiler compiler, DVal dVal, GPRegister register) {
        compiler.addInstruction(new REM(dVal, register));
    }

    @Override
    public int codeMnemoByte(DecacCompiler compiler, JavaCompiler javaCompiler) {
        if(this.getType().isFloat()) {
            return javaCompiler.FREM;
        } else if(this.getType().isInt()) {
            return javaCompiler.IREM;
        }
        return 0;
    }
}
