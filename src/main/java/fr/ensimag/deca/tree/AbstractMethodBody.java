package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

public abstract class AbstractMethodBody extends  Tree {

    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that method body is OK.
     */
    protected abstract void verifyBody(DecacCompiler compiler, ClassDefinition currentClass, EnvironmentExp envExpParams, Type returnType)
            throws ContextualError;

    protected abstract void codeGenMethodBody(IMACompiler compiler);

    protected void codeGenMethodBodyByte(JavaCompiler javaCompiler, int beginIndex) {}
}
