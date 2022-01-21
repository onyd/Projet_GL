package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.SymbolTable;
import org.apache.log4j.Logger;

public class ListDeclMethod extends AbstractListTree<AbstractDeclMethod> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        LOG.debug("[Pass 2] verify listDeclMethod: start");
        for (AbstractDeclMethod m : getList()) {
            m.verifyMethod(compiler, currentClass);
        }
        LOG.debug("[Pass 2] verify listDeclMethod: end");
    }

    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        LOG.debug("[Pass 3] verify listDeclMethod: start");
        for (AbstractDeclMethod m : getList()) {
            m.verifyMethodBody(compiler, currentClass);
        }
        LOG.debug("[Pass 3] verify listDeclMethod: end");
    }

    public void codeGenListDeclMethodByte(JavaCompiler javaCompiler) {
        for(AbstractDeclMethod declMethod : getList()) {
            declMethod.codeGenDeclMethodByte(javaCompiler);
        }
    }
}
