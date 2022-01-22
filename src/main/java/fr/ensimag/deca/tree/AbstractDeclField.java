package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.SymbolTable;

public abstract class AbstractDeclField extends Tree {

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the class fields
     * are OK, without looking at field initialization.
     */
    protected abstract void verifyField(DecacCompiler compiler, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that field initialization are OK.
     */
    protected abstract void verifyFieldInit(DecacCompiler compiler, ClassDefinition currentClass)
            throws ContextualError;

    protected abstract void codeGenDeclField(IMACompiler compiler);

    protected abstract void codeGenDeclFieldByte(JavaCompiler javaCompiler);
}
