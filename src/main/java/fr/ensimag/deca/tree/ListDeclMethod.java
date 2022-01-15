package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import org.apache.log4j.Logger;

public class ListDeclMethod extends AbstractListTree<AbstractDeclMethod> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        LOG.debug("[Pass 2] verify listDeclMethod: start");
        for (AbstractDeclMethod m : getList()) {
            m.verifyMethod(compiler); // TODO verify 2.6
        }
        LOG.debug("[Pass 2] verify listDeclMethod: end");
    }

    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        LOG.debug("[Pass 3] verify listDeclMethod: start");
        for (AbstractDeclMethod m : getList()) {
            m.verifyMethodBody(compiler);
        }
        LOG.debug("[Pass 3] verify listDeclMethod: end");
    }
}
