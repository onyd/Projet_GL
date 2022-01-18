package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class DeclMethod extends AbstractDeclMethod {
    private final AbstractIdentifier returnType;
    private final AbstractIdentifier methodIdent;
    private ListDeclParam params;
    private final AbstractMethodBody body;

    public DeclMethod(AbstractIdentifier returnType, AbstractIdentifier methodIdent, ListDeclParam params, AbstractMethodBody body) {
        Validate.notNull(returnType);
        Validate.notNull(methodIdent);
        Validate.notNull(params);
        Validate.notNull(body);
        this.returnType = returnType;
        this.methodIdent = methodIdent;
        this.params = params;
        this.body = body;
    }

    public AbstractIdentifier getType() {
        return returnType;
    }

    @Override
    public AbstractIdentifier getMethodIdent() {
        return methodIdent;
    }

    public ListDeclParam getParams() {
        return params;
    }

    public AbstractMethodBody getBody() {
        return body;
    }

    @Override
    protected void verifyMethod(DecacCompiler compiler, SymbolTable.Symbol superName, ClassDefinition currentClass) throws ContextualError {
        Type type = returnType.verifyType(compiler);
        Signature sig = params.verifyListClassMembers(compiler);
        currentClass.incNumberOfMethods();
        methodIdent.setDefinition(new MethodDefinition(type, getLocation(), sig, currentClass.getNumberOfMethods()));
        try {
            currentClass.getMembers().declare(methodIdent.getName(), methodIdent.getMethodDefinition());
        } catch (Environment.DoubleDefException e) {
            throw new ContextualError("Method has already been declared", getLocation());
        }
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        EnvironmentExp envExpParams = new EnvironmentExp(null);
        params.verifyListParams(compiler, envExpParams);
        body.verifyBody(compiler, currentClass, envExpParams, returnType.getType());
    }

    @Override
    public void decompile(IndentPrintStream s) {
        returnType.decompile(s);
        s.print(" ");
        methodIdent.decompile(s);
        s.print("(");
        params.decompile(s);
        s.print(")");
        body.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnType.prettyPrint(s, prefix, false);
        methodIdent.prettyPrint(s, prefix, false);
        params.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        returnType.iter(f);
        methodIdent.iter(f);
        params.iter(f);
        body.iter(f);
    }
}
