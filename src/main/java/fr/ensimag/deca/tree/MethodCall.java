package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public class MethodCall extends AbstractExpr {
    private AbstractIdentifier identifierLeft;
    private AbstractIdentifier identifierRight;
    private ListExpr arguments;

    public MethodCall(AbstractIdentifier identifierLeft, AbstractIdentifier identifierRight, ListExpr arguments) {
        super();
        this.identifierLeft = identifierLeft;
        this.identifierRight = identifierRight;
        this.arguments = arguments;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        return null;
    }

    @Override
    public void decompile(IndentPrintStream s) {

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {

    }

    @Override
    protected void iterChildren(TreeFunction f) {

    }
}
