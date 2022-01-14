package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.LabelManager;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BOV;

/**
 * read...() statement.
 *
 * @author gl28
 * @date 01/01/2022
 */
public abstract class AbstractReadExpr extends AbstractExpr {

    public AbstractReadExpr() {
        super();
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        this.codeMnemo(compiler, null, Register.R0);
        if(!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(LabelManager.IO_ERROR));
        }
    }

    @Override
    public void codeGenExprOnRegister(DecacCompiler compiler, GPRegister register) {
        this.codeMnemo(compiler, null, Register.R0);
        if(!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(LabelManager.IO_ERROR));
        }
    }
}
