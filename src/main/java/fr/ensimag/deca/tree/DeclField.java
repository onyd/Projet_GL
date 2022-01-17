package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class DeclField extends AbstractDeclField {
    private final AbstractIdentifier typeName;
    private final AbstractIdentifier fieldIdent;
    private  final AbstractInitialization initialization;

    public DeclField(AbstractIdentifier typeName, AbstractIdentifier fieldIdent, AbstractInitialization initialization) {
        Validate.notNull(typeName);
        Validate.notNull(fieldIdent);
        Validate.notNull(initialization);
        this.typeName = typeName;
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
    protected void verifyField(DecacCompiler compiler, SymbolTable.Symbol superName, ClassDefinition currentClass) throws ContextualError {
        Type type = typeName.verifyType(compiler);
        if (type.isVoid()) {
            throw new ContextualError("(2.5) Field type cannot be void", getLocation());
        }
        fieldIdent.setDefinition(new FieldDefinition(type, fieldIdent.getLocation(), Visibility.PUBLIC, currentClass, 0));

        try {
            currentClass.getMembers().declare(fieldIdent.getName(), fieldIdent.getFieldDefinition());
        } catch (Environment.DoubleDefException e) {
            throw new ContextualError("Field has already been declared", getLocation());
        }
    }

    @Override
    protected void verifyFieldInit(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        initialization.verifyInitialization(compiler, typeName.getType(), currentClass.getMembers(), currentClass);
    }
}
