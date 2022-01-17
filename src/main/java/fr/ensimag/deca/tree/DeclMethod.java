package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import org.apache.commons.lang.Validate;

public class DeclMethod extends AbstractDeclMethod {
    private final AbstractIdentifier type;
    private final AbstractIdentifier methodIdent;
    private ListDeclParam params;
    private final MethodBody body;

    public DeclMethod(AbstractIdentifier type, AbstractIdentifier methodIdent, ListDeclParam params, MethodBody body) {
        Validate.notNull(type);
        Validate.notNull(methodIdent);
        Validate.notNull(params);
        Validate.notNull(body);
        this.type = type;
        this.methodIdent = methodIdent;
        this.params = params;
        this.body = body;
    }

    public AbstractIdentifier getType() {
        return type;
    }

    @Override
    public AbstractIdentifier getMethodIdent() {
        return methodIdent;
    }

    public ListDeclParam getParams() {
        return params;
    }

    public MethodBody getBody() {
        return body;
    }

    @Override
    protected void verifyMethod(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
        // TODO verify method 2.7
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
        // TODO verify method body 3.11
    }
}
