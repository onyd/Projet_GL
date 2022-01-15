package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import org.apache.log4j.Logger;

public class ListDeclParam extends AbstractCommaSeparatedListTree<AbstractDeclParam> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        LOG.debug("[Pass 2] verify listDeclParam: start");
        for (AbstractDeclParam p : getList()) {
            p.verifyParamType(compiler); // TODO verify 2.6
        }
        LOG.debug("[Pass 2] verify listDeclParam: end");
    }

    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        LOG.debug("[Pass 3] verify listDeclParam: start");
        for (AbstractDeclParam p : getList()) {
            p.verifyParam(compiler); // TODO verify 3.12
        }
        LOG.debug("[Pass 3] verify listDeclParam: end");
    }

}
