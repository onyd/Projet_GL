package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl28
 * @date 01/01/2022
 */
public class DeclClass extends AbstractDeclClass {
    private final AbstractIdentifier name;
    private final AbstractIdentifier superClassName;
    private ListDeclField fields;
    private ListDeclMethod methods;

    public DeclClass(AbstractIdentifier name, AbstractIdentifier superClassName) {
        Validate.notNull(name);
        Validate.notNull(superClassName);
        this.name = name;
        this.superClassName = superClassName;
    }

    public void addFields(AbstractDeclField field) {
        fields.add(field);
    }

    public void addMethod(AbstractDeclMethod method) {
        methods.add(method);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class { ... A FAIRE ... }");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        ClassDefinition def = (ClassDefinition) compiler.getEnvironmentType().get(superClassName.getName());
        if (def == null){
            throw new ContextualError("(1.3) Super class must be declared before its children", getLocation());
        }
        try {
            ClassType type = new ClassType(name.getName(), getLocation(), def);
            compiler.getEnvironmentType().declare(name.getName(), type.getDefinition());
        } catch (Environment.DoubleDefException e) {
            throw new ContextualError("(1.3) The class has already been declared", getLocation());
        }
        fields.verifyListClassMembers(compiler);
        methods.verifyListClassMembers(compiler);

    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        fields.verifyListClassBody(compiler);
        methods.verifyListClassBody(compiler);

    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
        // TODO verify 3.5
    }

    @Override
    protected void codeGenDeclClassVTable(DecacCompiler compiler) {

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not yet supported");
    }

}
