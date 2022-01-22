package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.InlinePortion;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class MethodJavaBody extends AbstractMethodBody {
    private final StringLiteral java;

    public MethodJavaBody(StringLiteral assembly) {
        Validate.notNull(assembly);
        this.java = assembly;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("java (");
        java.decompile(s);
        s.print(");");
        s.println();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        java.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        java.iter(f);
    }

    @Override
    protected void verifyBody(DecacCompiler compiler, ClassDefinition currentClass, EnvironmentExp envExpParams, Type returnType) throws ContextualError {
        java.verifyExpr(compiler, envExpParams, currentClass);
        if (!compiler.getCompilerOptions().getJavaCompilation()) {
            throw new ContextualError("The option must be -java in order to use java compiler", getLocation());
        }
    }

    @Override
    protected void codeGenMethodBody(IMACompiler compiler) {
        compiler.add(new InlinePortion(java.getValue()));
    }

    public void codeGenByte(JavaCompiler javaCompiler) {
        javaCompiler.addJavaMethod(this.java.getValue());
    }

}
