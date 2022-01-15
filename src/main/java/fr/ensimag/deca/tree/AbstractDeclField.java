package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

public abstract class AbstractDeclField extends Tree {

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the class fields
     * are OK, without looking at field initialization.
     */
    protected abstract void verifyField(DecacCompiler compiler)
            throws ContextualError;

    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that field initialization are OK.
     */
    protected abstract void verifyFieldInit(DecacCompiler compiler)
            throws ContextualError;

}
