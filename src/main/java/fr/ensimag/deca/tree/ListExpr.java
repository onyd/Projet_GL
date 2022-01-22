package fr.ensimag.deca.tree;

import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl28
 * @date 01/01/2022
 */
public class ListExpr extends AbstractCommaSeparatedListTree<AbstractExpr> {
    public void verifyRValueStar(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Signature sig) throws ContextualError {
        for (int i = 0; i < sig.size(); i++) {
            getList().get(i).verifyRValue(compiler, localEnv, currentClass, sig.paramNumber(i));
        }
    }
}
