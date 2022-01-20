package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.PrintStream;

/**
 * Empty main Deca program
 *
 * @author gl28
 * @date 01/01/2022
 */
public class EmptyMain extends AbstractMain {
    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
    }

    @Override
    protected void codeGenMain(DecacCompiler compiler) {
    }

    @Override
    protected void codeGenMainByte(JavaCompiler javaCompiler)
    {
        ClassWriter classWriter = javaCompiler.getClassWriter();
        MethodVisitor methodVisitor = null;
        methodVisitor = classWriter.visitMethod(javaCompiler.ACC_PUBLIC + javaCompiler.ACC_STATIC,
                "main",
                "([Ljava/lang/String;)V",
                null,
                null);

        methodVisitor.visitInsn(javaCompiler.RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
    }

    /**
     * Contains no real information => nothing to check.
     */
    @Override
    protected void checkLocation() {
        // nothing
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        // no main program => nothing
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }
}
