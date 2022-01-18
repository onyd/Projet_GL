package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class MethodCall extends AbstractExpr {
    private AbstractExpr expr;
    private AbstractIdentifier methodIdent;
    private ListExpr arguments;

    public MethodCall(AbstractExpr expr, AbstractIdentifier methodIdent, ListExpr arguments) {
        super();
        Validate.notNull(expr);
        Validate.notNull(methodIdent);
        Validate.notNull(arguments);
        this.expr = expr;
        this.methodIdent = methodIdent;
        this.arguments = arguments;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type typeClass2 = expr.verifyExpr(compiler, localEnv, currentClass);
        ClassDefinition class2Def = typeClass2.asClassType("(3.71) Method can only be called on class type", getLocation()).getDefinition();
        Type type = methodIdent.verifyField(compiler, class2Def.getMembers());
        setType(type);
        arguments.verifyRValueStar(compiler, localEnv, currentClass, methodIdent.getMethodDefinition().getSignature());
        return getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(".");
        methodIdent.decompile(s);
        s.print("(");
        arguments.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        methodIdent.prettyPrint(s, prefix, false);
        arguments.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        methodIdent.iter(f);
        arguments.iter(f);
    }
}
