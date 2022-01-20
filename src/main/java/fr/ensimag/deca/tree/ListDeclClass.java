package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.log4j.Logger;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class ListDeclClass extends AbstractListTree<AbstractDeclClass> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        LOG.debug("[Pass 1] verify listClass: start");
        for (AbstractDeclClass cls : getList()) {
            cls.verifyClass(compiler);
        }
        LOG.debug("[Pass 1] verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        LOG.debug("[Pass 2] verify listClass: start");
        for (AbstractDeclClass cls : getList()) {
            cls.verifyClassMembers(compiler);
        }
        LOG.debug("[Pass 2] verify listClass: end");
    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        LOG.debug("[Pass 3] verify listClass: start");
        for (AbstractDeclClass cls : getList()) {
            cls.verifyClassBody(compiler);
        }
        LOG.debug("[Pass 3] verify listClass: end");
    }

    protected void codeGenListDeclClass(DecacCompiler compiler) {
        //for the Object class
        compiler.getvTable().VTableForObject();
        compiler.setDeclareMethod(true);
        compiler.getvTable().createEqualsMethod();
        compiler.setDeclareMethod(false);

        //for the other class
        for(AbstractDeclClass declClass : getList()) {
            declClass.codeGenDeclClassVTable(compiler);
        }
        for(AbstractDeclClass declClass : getList()) {
            declClass.codeGenDeclClassMethod(compiler);
        }
    }
}
