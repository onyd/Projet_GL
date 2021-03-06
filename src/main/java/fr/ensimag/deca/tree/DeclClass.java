package fr.ensimag.deca.tree;

import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.PrintStream;
import java.util.Objects;

/**
 * Declaration of a class.
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
    protected void codeGenDeclClassVTable(IMACompiler compiler) {
        compiler.getvTable().VTableFromIdent((Identifier) name, (Identifier) superClassName, methods);
    }

    @Override
    protected void codeGenDeclClassMethod(IMACompiler compiler) {
        compiler.setDeclareMethod(true);
        compiler.getvTable().constructor(fields, this.name.getName().getName(), (Identifier) this.superClassName);
        compiler.getvTable().createMethods(methods, this.name.getName().getName());
        compiler.setDeclareMethod(false);
    }

    @Override
    protected void codeGenDeclClassByte(JavaCompiler javaCompiler, String path) {
        // The javaCompiler specific to this class
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        javaCompiler.getDeclClass().put(name.getName().getName(), classWriter);
        javaCompiler.setClassWriter(classWriter);
        javaCompiler.setClassName(name.getName().getName());
        javaCompiler.addJavaJavaMethodClass(name.getName().getName());

        if(Objects.equals(superClassName.getName().getName(), "Object")) {
            classWriter.visit(javaCompiler.V1_8,
                    javaCompiler.ACC_PUBLIC + javaCompiler.ACC_SUPER,
                    name.getName().getName(),
                    null,
                    "java/lang/Object",
                    null);
        } else {
            classWriter.visit(javaCompiler.V1_8,
                    javaCompiler.ACC_PUBLIC + javaCompiler.ACC_SUPER,
                    name.getName().getName(),
                    null,
                    superClassName.getName().getName(),
                    null);
        }


        classWriter.visitSource(path + name.getName().getName() + ".java", null);

        // default constructor
        MethodVisitor methodVisitor = null;
        methodVisitor = classWriter.visitMethod(javaCompiler.ACC_PUBLIC, "<init>", "()V", null, null);
        javaCompiler.setMethodVisitor(methodVisitor);
        methodVisitor.visitVarInsn(javaCompiler.ALOAD, 0);

        if(Objects.equals(superClassName.getName().getName(), "Object")) {
            methodVisitor.visitMethodInsn(javaCompiler.INVOKESPECIAL,
                    "java/lang/Object",
                    "<init>",
                    "()V",false);
        } else {
            methodVisitor.visitMethodInsn(javaCompiler.INVOKESPECIAL,
                    superClassName.getName().getName(),
                    "<init>",
                    "()V",false);
        }


        //creation and initialization of the field in the constructor
        fields.codeGenListDeclFieldByte(javaCompiler);

        methodVisitor.visitInsn(javaCompiler.RETURN);
        methodVisitor.visitMaxs(-1, -1);
        methodVisitor.visitEnd();

        //create the methods
        methods.codeGenListDeclMethodByte(javaCompiler);

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
