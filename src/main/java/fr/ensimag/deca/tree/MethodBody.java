package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class MethodBody extends AbstractMethodBody {
    private ListDeclVar declVars;
    private ListInst instructions;

    public MethodBody(ListDeclVar declVars,
                ListInst instructions) {
        Validate.notNull(declVars);
        Validate.notNull(instructions);
        this.declVars = declVars;
        this.instructions = instructions;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(" {");
        s.println();
        s.indent();
        declVars.decompile(s);
        instructions.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVars.prettyPrint(s, prefix, false);
        instructions.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVars.iter(f);
        instructions.iter(f);
    }

    @Override
    protected void verifyBody(DecacCompiler compiler, ClassDefinition currentClass, EnvironmentExp envExpParams, Type returnType) throws ContextualError {
        declVars.verifyListDeclVariable(compiler, envExpParams, currentClass);
        instructions.verifyListInst(compiler, envExpParams, currentClass, returnType);
    }
}
