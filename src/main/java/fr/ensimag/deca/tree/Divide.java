package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

    @Override
    public void codeMnemo(DecacCompiler compiler, DVal dVal, GPRegister register) {
        if(this.getType().isFloat()) {
            compiler.addInstruction(new DIV(dVal, register));
        } else if(this.getType().isInt()) {
            compiler.addInstruction(new QUO(dVal, register));
        }
    }

    @Override
    public int codeMnemoByte(JavaCompiler javaCompiler) {
        if(this.getType().isFloat()) {
            return javaCompiler.FDIV;
        } else if(this.getType().isInt()) {
            return javaCompiler.IDIV;
        }
        return 0;
    }
}
