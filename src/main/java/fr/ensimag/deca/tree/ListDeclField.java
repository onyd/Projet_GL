package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.LabelManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import org.apache.log4j.Logger;

public class ListDeclField extends AbstractListTree<AbstractDeclField> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        LOG.debug("[Pass 2] verify listDeclField: start");
        for (AbstractDeclField f : getList()) {
            f.verifyField(compiler, currentClass);
        }
        LOG.debug("[Pass 2] verify listDeclField: end");
    }

    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        LOG.debug("[Pass 3] verify listDeclField: start");
        for (AbstractDeclField f : getList()) {
            f.verifyFieldInit(compiler, currentClass);
        }
        LOG.debug("[Pass 3] verify listDeclField: end");
    }

    public void codeGenListDeclField(DecacCompiler compiler) {
        for(AbstractDeclField declField : this.getList()) {
            declField.codeGenDeclField(compiler);
        }
    }

    public void codeGenListDeclFieldByte(JavaCompiler javaCompiler) {
        for(AbstractDeclField declField : getList()) {
            declField.codeGenDeclFieldByte(javaCompiler);
        }
    }
}
