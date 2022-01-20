package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 * @author gl28
 * @date 01/01/2022
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

    @Override
    public void codeMnemo(DecacCompiler compiler, DVal dVal, GPRegister register) {
        compiler.addInstruction(new SUB(dVal, register));
    }
    @Override
    public int codeMnemoByte(DecacCompiler compiler, JavaCompiler javaCompiler) {
        Type leftType = getLeftOperand().getType();
        Type rightType = getRightOperand().getType();

        if (leftType.isInt() && rightType.isInt()) {
            return javaCompiler.ISUB;
        } else {
            return javaCompiler.FSUB;
        }
    }
}
