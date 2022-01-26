package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * Represents the main method in a deca program.
 * @author gl28
 * @date 01/01/2022
 */
public class Main extends AbstractMain {
    private static final Logger LOG = Logger.getLogger(Main.class);
    
    private ListDeclVar declVariables;
    private ListInst insts;

    public Main(ListDeclVar declVariables,
            ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    /**
     * Apply contextual verifications.
     * @param compiler
     * @throws ContextualError
     */
    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify Main: start");
        EnvironmentExp envExp = new EnvironmentExp(null);

        try {
            envExp.declare(compiler.EQUALS_SYMBOL, compiler.EQUALS_DEF);
        } catch (Environment.DoubleDefException e) {
            // Never happen
        }
        this.declVariables.verifyListDeclVariable(compiler, envExp, null);
        this.insts.verifyListInst(compiler, envExp, null, compiler.getEnvironmentType().get(compiler.VOID_SYMBOL).getType());
        LOG.debug("verify Main: end");

    }

    /**
     * Generate the IMA instructions for the entire program.
     * @param compiler
     */
    @Override
    protected void codeGenMain(IMACompiler compiler) {
        compiler.addComment("Beginning of main instructions:");
        compiler.setCurrentMethod(null);
        declVariables.codeGenListDeclVariable(compiler);
        insts.codeGenListInst(compiler);
    }

    /**
     * Generate bytecode for the program when the option -java is given.
     * @param javaCompiler Buffer to which the result will be written.
     */
    @Override
    protected void codeGenMainByte(JavaCompiler javaCompiler)
    {
        ClassWriter classWriter = javaCompiler.getClassWriter();
        MethodVisitor methodVisitor = null;

        // main function
        methodVisitor = classWriter.visitMethod(javaCompiler.ACC_PUBLIC + javaCompiler.ACC_STATIC,
                "main",
                "([Ljava/lang/String;)V",
                null,
                null);

        javaCompiler.setMethodVisitor(methodVisitor);

        //declVariables
        declVariables.codeGenListDeclVariableByte(javaCompiler, 1);

        //decl inst
        insts.codeGenListInstByte(javaCompiler);

        methodVisitor.visitInsn(javaCompiler.RETURN);
        methodVisitor.visitMaxs(-1, -1);

        methodVisitor.visitEnd();
    }

    /**
     * @param s Buffer to which the result will be written.
     */
    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
    }

    /**
     * Apply the tree function on all the variables and instructions.
     * @param f
     */
    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }

    /**
     *
     * @param s
     * @param prefix
     */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }
}
