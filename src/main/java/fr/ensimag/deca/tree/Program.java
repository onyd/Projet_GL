package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.Utils;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.*;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.objectweb.asm.ClassWriter;

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
    public void codeGenProgramByte(DecacCompiler compiler, JavaCompiler javaCompiler)
    {
        ClassWriter classWriter = javaCompiler.getClassWriter();
        classWriter.visit(javaCompiler.V1_5,
                javaCompiler.ACC_PUBLIC + javaCompiler.ACC_SUPER,
                "ClasseMain",
                null,
                "java/lang/Object",
                null);
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
