package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

public abstract class AbstractDeclParam extends Tree {

    public abstract AbstractIdentifier getTypeName() ;

    public abstract AbstractIdentifier getParamIdent();

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the param
     * is OK
     */
    protected abstract Type verifyParamType(DecacCompiler compiler)
            throws ContextualError;

    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that the param is OK.
     */
    protected abstract void verifyParam(DecacCompiler compiler, EnvironmentExp envExpParams)
            throws ContextualError;

    protected abstract void codeGenDeclParam(IMACompiler compiler, int offset);
}
