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
        this.fields = new ListDeclField();
        this.methods = new ListDeclMethod();
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
        ClassDefinition superDef = (ClassDefinition) compiler.getEnvironmentType().get(superClassName.getName());
        if (superDef == null){
            throw new ContextualError("(1.3) Super class must be declared before its children", getLocation());
        }
        try {
            ClassType type = new ClassType(name.getName(), getLocation(), superDef);
            compiler.getEnvironmentType().declare(name.getName(), type.getDefinition());
            name.setDefinition(type.getDefinition());

            // Stack the super environment
            EnvironmentExp envExpSuper = superClassName.getClassDefinition().getMembers();
            type.getDefinition().getMembers().stack(envExpSuper);
        } catch (Environment.DoubleDefException e) {
            throw new ContextualError("(1.3) The class has already been declared", getLocation());
        }
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        fields.verifyListClassMembers(compiler, superClassName.getName(), name.getClassDefinition());
        methods.verifyListClassMembers(compiler, superClassName.getName(), name.getClassDefinition());

    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        fields.verifyListClassBody(compiler, name.getClassDefinition());
        methods.verifyListClassBody(compiler, name.getClassDefinition());
    }

    @Override
    protected void codeGenDeclClass(DecacCompiler compiler) {
        compiler.getvTable().VTableFromIdent((Identifier) name, (Identifier) superClassName, methods);
        compiler.getvTable().constructor(fields, this.name.getName().getName());
        compiler.getvTable().createMethods(methods, this.name.getName().getName());
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
