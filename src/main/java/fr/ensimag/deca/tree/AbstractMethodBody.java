package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

public abstract class AbstractMethodBody extends  Tree {

    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that method body is OK.
     */
    protected abstract void verifyBody(DecacCompiler compiler)
            throws ContextualError;
}
