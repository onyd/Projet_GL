package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.Utils;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.*;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import javax.tools.*;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Program extends AbstractProgram {
    private static final Logger LOG = Logger.getLogger(Program.class);
    
    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }
    public ListDeclClass getClasses() {
        return classes;
    }
    public AbstractMain getMain() {
        return main;
    }
    private ListDeclClass classes;
    private AbstractMain main;

    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify program: start");

        // Pass 1
        classes.verifyListClass(compiler);

        // Pass 2
        classes.verifyListClassMembers(compiler);

        // Pass 3
        classes.verifyListClassBody(compiler);
        main.verifyMain(compiler);

        LOG.debug("verify program: end");
    }

    @Override
    public void codeGenProgram(IMACompiler compiler) {
        //create the vtable
        compiler.addComment("Creation of the virtual methods table");
        classes.codeGenListDeclClass(compiler);

        compiler.addComment("Main program");
        main.codeGenMain(compiler);
        compiler.addInstruction(new HALT());

        //crete all the constructors and methods
        compiler.appendMethodProg();

        //add all the error
        compiler.addComment("Handle the errors");
        Utils.handleError(compiler);
    }

    @Override
    public void codeGenProgramByte(JavaCompiler javaCompiler, String path, String className) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        classes.codeGenListDeclClassByte(javaCompiler, path);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        javaCompiler.setClassWriter(classWriter);

        classWriter.visit(javaCompiler.V1_8,
                javaCompiler.ACC_PUBLIC + javaCompiler.ACC_SUPER,
                className,
                null,
                "java/lang/Object",
                null);

        classWriter.visitSource(path + className + ".java", null);
        MethodVisitor methodVisitor = null;

        // default constructor
        methodVisitor = classWriter.visitMethod(javaCompiler.ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitVarInsn(javaCompiler.ALOAD, 0);
        methodVisitor.visitMethodInsn(javaCompiler.INVOKESPECIAL,
                "java/lang/Object",
                "<init>",
                "()V",false);
        methodVisitor.visitInsn(javaCompiler.RETURN);
        methodVisitor.visitMaxs(-1, -1);
        methodVisitor.visitEnd();
        main.codeGenMainByte(javaCompiler);

        classWriter.visitEnd();
        //System.out.println("Prog:" + javaCompiler.getMethods());

        String program = "class MethodJavaBodies {" + javaCompiler.getMethods() + "}";
        Iterable<? extends JavaFileObject> fileObjects = getJavaSourceFromString(program);

        javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, null, null);

        String[] separated = javaCompiler.getSource().toString().split("\\/");
        String filePath = "";
        for (int i = 0; i < separated.length - 1; i++) {
            filePath += separated[i] + "/";
        }

        String[] compileOptions = new String[]{"-d", filePath};
        Iterable<String> compilationOptions = Arrays.asList(compileOptions);


        compiler.getTask(null, null, null, compilationOptions, null, fileObjects).call();

        Class<?> clazz = Class.forName("MethodJavaBodies");
        Method m = clazz.getMethod("main", new Class[] { String[].class });
        Object[] _args = new Object[] { new String[0] };
        m.invoke(null, _args);
    }

    static Iterable<JavaSourceFromString> getJavaSourceFromString(String code) {
        final JavaSourceFromString jsfs;
        jsfs = new JavaSourceFromString("code", code);
        return new Iterable<JavaSourceFromString>() {
            public Iterator<JavaSourceFromString> iterator() {
                return new Iterator<JavaSourceFromString>() {
                    boolean isNext = true;

                    public boolean hasNext() {
                        return isNext;
                    }

                    public JavaSourceFromString next() {
                        if (!isNext)
                            throw new NoSuchElementException();
                        isNext = false;
                        return jsfs;
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }
    
    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}
