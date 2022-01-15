package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import org.apache.log4j.Logger;

public class ListDeclField extends AbstractListTree<AbstractDeclField> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        LOG.debug("[Pass 2] verify listDeclField: start");
        for (AbstractDeclField f : getList()) {
            f.verifyField(compiler); // TODO verify 2.4
        }
        LOG.debug("[Pass 2] verify listDeclField: end");
    }

    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        LOG.debug("[Pass 3] verify listDeclField: start");
        for (AbstractDeclField f : getList()) {
            f.verifyFieldInit(compiler);
        }
        LOG.debug("[Pass 3] verify listDeclField: end");
    }


}
