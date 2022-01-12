package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;

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
        this.codeMnemo(compiler, null, 0);
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        this.codeMnemo(compiler, null, 0);
    }

    @Override
    public void codeGenExprOnRegister(DecacCompiler compiler, int register) {
        this.codeMnemo(compiler, null, 0);
    }
}
