package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.InlinePortion;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class MethodJavaBody extends AbstractMethodBody {
    private final StringLiteral assembly;

    public MethodJavaBody(StringLiteral assembly) {
        Validate.notNull(assembly);
        this.assembly = assembly;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("java (");
        assembly.decompile(s);
        s.print(");");
        s.println();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        assembly.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        assembly.iter(f);
    }

    @Override
    protected void verifyBody(DecacCompiler compiler, ClassDefinition currentClass, EnvironmentExp envExpParams, Type returnType) throws ContextualError {
        assembly.verifyExpr(compiler, envExpParams, currentClass);
        if (!compiler.getCompilerOptions().getJavaCompilation()) {
            throw new ContextualError("The option must be -java in order to use java compiler", getLocation());
        }
    }

    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        compiler.add(new InlinePortion(assembly.getValue()));
    }
}
