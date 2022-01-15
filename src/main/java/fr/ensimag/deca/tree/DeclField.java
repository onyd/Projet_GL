package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class DeclField extends AbstractDeclField {
    private final AbstractIdentifier type;
    private final AbstractIdentifier fieldIdent;
    private  final AbstractInitialization initialization;

    public DeclField(AbstractIdentifier type, AbstractIdentifier fieldIdent, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(fieldIdent);
        Validate.notNull(initialization);
        this.type = type;
        this.fieldIdent = fieldIdent;
        this.initialization = initialization;
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

    @Override
    protected void verifyField(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
        // TODO verify field 2.5
    }

    @Override
    protected void verifyFieldInit(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
        // TODO verify field init 3.7
    }
}
