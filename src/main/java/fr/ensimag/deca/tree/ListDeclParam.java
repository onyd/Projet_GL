package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import org.apache.log4j.Logger;

public class ListDeclParam extends AbstractCommaSeparatedListTree<AbstractDeclParam> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public Signature verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        LOG.debug("[Pass 2] verify listDeclParam: start");
        Signature sig = new Signature();
        for (AbstractDeclParam p : getList()) {
            sig.add(p.verifyParamType(compiler));
        }
        LOG.debug("[Pass 2] verify listDeclParam: end");
        return sig;
    }

    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListParams(DecacCompiler compiler, EnvironmentExp envExpParams) throws ContextualError {
        LOG.debug("[Pass 3] verify listDeclParam: start");
        for (AbstractDeclParam p : getList()) {
            p.verifyParam(compiler, envExpParams); // TODO verify 3.12
        }
        LOG.debug("[Pass 3] verify listDeclParam: end");
    }

    public void codeGenListDeclParam(DecacCompiler compiler) {
        int paramOffset = -3;
        for(AbstractDeclParam param : getList()) {
            param.codeGenDeclParam(compiler, paramOffset);
            paramOffset--;
        }
    }
}
