package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 * @author gl28
 * @date 01/01/2022
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "*";
    }

    @Override
    public void codeMnemo(DecacCompiler compiler, DVal dVal, GPRegister register) {
        compiler.addInstruction(new MUL(dVal, register));
    }

    @Override
    public int codeMnemoByte(DecacCompiler compiler, JavaCompiler javaCompiler) {
        if(this.getType().isFloat()) {
            return javaCompiler.FMUL;
        } else if(this.getType().isInt()) {
            return javaCompiler.IMUL;
        }
        return 0;
    }
}
