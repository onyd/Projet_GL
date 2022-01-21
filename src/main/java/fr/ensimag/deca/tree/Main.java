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

    @Override
    protected void codeGenMain(IMACompiler compiler) {
        compiler.addComment("Beginning of main instructions:");
        declVariables.codeGenListDeclVariable(compiler);
        insts.codeGenListInst(compiler);
    }
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

    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }
 
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }
}
