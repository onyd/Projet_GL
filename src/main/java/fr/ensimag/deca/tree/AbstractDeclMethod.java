package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public abstract class AbstractDeclMethod extends Tree {

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the class method
     * are OK, without looking at method body.
     */
    protected abstract void verifyMethod(DecacCompiler compiler)
            throws ContextualError;

    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that method body are OK.
     */
    protected abstract void verifyMethodBody(DecacCompiler compiler)
            throws ContextualError;

    @Override
    public void decompile(IndentPrintStream s) {

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {

    }

    @Override
    protected void iterChildren(TreeFunction f) {

    }
}
