package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

public abstract class AbstractDeclParam extends Tree {

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the param
     * is OK
     */
    protected abstract void verifyParamType(DecacCompiler compiler)
            throws ContextualError;

    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that the param is OK.
     */
    protected abstract void verifyParam(DecacCompiler compiler)
            throws ContextualError;
}
