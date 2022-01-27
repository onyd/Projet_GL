package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;
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

    @Override
    protected void codeGenMethodBody(IMACompiler compiler) {
        declVars.codeGenListDeclVariableFromMethod(compiler);
        instructions.codeGenListInst(compiler);
        if(!declVars.getList().isEmpty()) {
            compiler.addInstruction(new SUBSP(new ImmediateInteger(declVars.getList().size())));
        }
    }

    @Override
    protected void codeGenMethodBodyByte(JavaCompiler javaCompiler, int beginIndex) {
        declVars.codeGenListDeclVariableByte(javaCompiler, beginIndex);
        instructions.codeGenListInstByte(javaCompiler);
    }
}
