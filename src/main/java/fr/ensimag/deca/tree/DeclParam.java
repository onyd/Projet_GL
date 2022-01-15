package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class DeclParam extends AbstractDeclParam {
    private final AbstractIdentifier type;
    private final  AbstractIdentifier paramIdent;

    public DeclParam(AbstractIdentifier type, AbstractIdentifier paramIdent) {
        Validate.notNull(type);
        Validate.notNull(paramIdent);
        this.type = type;
        this.paramIdent = paramIdent;
    }

    @Override
    protected void verifyParamType(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
        // TODO verify 2.9
    }

    @Override
    protected void verifyParam(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
        // TODO verify param 3.13
    }


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
