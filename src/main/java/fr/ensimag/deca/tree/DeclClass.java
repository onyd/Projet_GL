package fr.ensimag.deca.tree;

import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.PrintStream;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl28
 * @date 01/01/2022
 */
public class DeclClass extends AbstractDeclClass {


    private AbstractIdentifier name;
    private AbstractIdentifier superClassName;
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

    public DeclClass() {
        this.fields = new ListDeclField();
        this.methods = new ListDeclMethod();
    }

    public AbstractIdentifier getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(AbstractIdentifier superClassName) {
        this.superClassName = superClassName;
    }

    public AbstractIdentifier getName() {
        return name;
    }

    public void setName(AbstractIdentifier name) {
        this.name = name;
    }

    public void addField(AbstractDeclField field) {
        fields.add(field);
    }

    public void addMethod(AbstractDeclMethod method) {
        methods.add(method);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class ");
        name.decompile(s);
        s.print(" extends ");
        superClassName.decompile(s);
        s.print(" {");
        s.println();
        s.indent();
        fields.decompile(s);
        methods.decompile(s);
        s.unindent();
        s.println("}");
        s.println();
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        ClassDefinition superDef = (ClassDefinition) compiler.getEnvironmentType().get(superClassName.getName());
        superClassName.setDefinition(superDef);
        if (superDef == null){
            throw new ContextualError("(1.3) Super class must be declared before its children", getLocation());
        }
        try {
            ClassType type = new ClassType(name.getName(), getLocation(), superDef);
            compiler.getEnvironmentType().declare(name.getName(), type.getDefinition());
            name.setDefinition(type.getDefinition());
            name.setType(type);

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
        // Initialize number of fields/methods according to its super class
        ClassDefinition superClassDef = superClassName.getClassDefinition();
        name.getClassDefinition().setNumberOfFields(superClassDef.getNumberOfFields());
        name.getClassDefinition().setNumberOfMethods(superClassDef.getNumberOfMethods());

        // Verification of children
        fields.verifyListClassMembers(compiler, name.getClassDefinition());
        methods.verifyListClassMembers(compiler, name.getClassDefinition());

    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        fields.verifyListClassBody(compiler, name.getClassDefinition());
        methods.verifyListClassBody(compiler, name.getClassDefinition());
    }

    @Override
    protected void codeGenDeclClass(DecacCompiler compiler) {
        compiler.getvTable().VTableFromIdent((Identifier) name, (Identifier) superClassName, methods);
        compiler.setDeclareMethod(true);
        compiler.getvTable().constructor(fields, this.name.getName().getName(), (Identifier) this.superClassName);
        compiler.getvTable().createMethods(methods, this.name.getName().getName());
        compiler.setDeclareMethod(false);
    }

    @Override
    protected void codeGenDeclClassByte(JavaCompiler javaCompiler, String path) {
        // The javaCompiler specific to this class
        JavaCompiler classJavaCompiler = new JavaCompiler();
        javaCompiler.getDeclClass().put(name.getName().getName(), classJavaCompiler);

        ClassWriter classWriter = classJavaCompiler.getClassWriter();
        classWriter.visit(classJavaCompiler.V1_8,
                classJavaCompiler.ACC_PUBLIC + classJavaCompiler.ACC_SUPER,
                name.getName().getName(),
                null,
                "java/lang/Object",
                null);

        classWriter.visitSource(path + name.getName().getName() + ".java", null);
        classJavaCompiler.setClassName(name.getName().getName());

        // default constructor
        MethodVisitor methodVisitor = null;
        methodVisitor = classWriter.visitMethod(classJavaCompiler.ACC_PUBLIC, "<init>", "()V", null, null);
        classJavaCompiler.setMethodVisitor(methodVisitor);
        methodVisitor.visitVarInsn(classJavaCompiler.ALOAD, 0);
        methodVisitor.visitMethodInsn(classJavaCompiler.INVOKESPECIAL,
                "java/lang/Object",
                "<init>",
                "()V",false);

        //creation and initialization of the field in the constructor
        fields.codeGenListDeclFieldByte(classJavaCompiler);

        methodVisitor.visitInsn(classJavaCompiler.RETURN);
        methodVisitor.visitMaxs(-1, -1);
        methodVisitor.visitEnd();

        //create the methods
        methods.codeGenListDeclMethodByte(classJavaCompiler);

        classWriter.visitEnd();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        name.prettyPrint(s, prefix, false);
        superClassName.prettyPrint(s, prefix, false);
        fields.prettyPrint(s, prefix, false);
        methods.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        name.iter(f);
        superClassName.iter(f);
        fields.iter(f);
        methods.iter(f);
    }

}
