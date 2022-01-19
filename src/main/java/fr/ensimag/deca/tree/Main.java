package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

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
        Signature signature = new Signature();
        signature.add(compiler.getEnvironmentType().get(compiler.BOOLEAN_SYMBOL).getType());
        try {
            envExp.declare(compiler.EQUALS_SYMBOL, new MethodDefinition(compiler.getEnvironmentType().get(compiler.VOID_SYMBOL).getType(), getLocation(), signature, 0));
        } catch (Environment.DoubleDefException e) {
            // Never happen
        }
        this.declVariables.verifyListDeclVariable(compiler, envExp, null);
        this.insts.verifyListInst(compiler, envExp, null, compiler.getEnvironmentType().get(compiler.VOID_SYMBOL).getType());
        LOG.debug("verify Main: end");

    }

    @Override
    protected void codeGenMain(DecacCompiler compiler) {
        compiler.addComment("Beginning of main instructions:");
        declVariables.codeGenListDeclVariable(compiler);
        insts.codeGenListInst(compiler);
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
