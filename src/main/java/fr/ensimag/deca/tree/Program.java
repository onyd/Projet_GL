package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.Utils;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.*;
import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

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
        getMain().verifyMain(compiler);
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
        compiler.addComment("Main program");
        main.codeGenMain(compiler);
        compiler.addInstruction(new HALT());
        Utils.handleError(compiler);
    }

    @Override
    public void codeGenProgramByte(DecacCompiler compiler, JavaCompiler javaCompiler, String destByteName, String className)
    {
        ClassWriter classWriter = javaCompiler.getClassWriter();
        classWriter.visit(javaCompiler.V1_5,
                javaCompiler.ACC_PUBLIC + javaCompiler.ACC_SUPER,
                destByteName.substring(0, destByteName.length()-6),
                null,
                "java/lang/Object",
                null);

        classWriter.visitSource(destByteName.substring(0, destByteName.length()-4) + "java", null);
        MethodVisitor methodVisitor = null;

        // Création du constructeur par défaut.
        methodVisitor = classWriter.visitMethod(javaCompiler.ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitVarInsn(javaCompiler.ALOAD, 0);
        methodVisitor.visitMethodInsn(javaCompiler.INVOKESPECIAL,
                "java/lang/Object",
                "<init>",
                "()V",false);
        methodVisitor.visitInsn(javaCompiler.RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();

        main.codeGenMainByte(compiler,javaCompiler);// similaire à main.codeGenMain(compiler); de la méthode codeGenProgram.
        classWriter.visitEnd();
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
