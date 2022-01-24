package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Main block of a Deca program.
 *
 * @author gl28
 * @date 01/01/2022
 */
public abstract class AbstractMain extends Tree {

    protected abstract void codeGenMain(IMACompiler compiler);

    protected abstract void codeGenMainByte(JavaCompiler javaCompiler);


    /**
     * Implements non-terminal "main" of [SyntaxeContextuelle] in pass 3 
     */
    protected abstract void verifyMain(DecacCompiler compiler) throws ContextualError;
}
