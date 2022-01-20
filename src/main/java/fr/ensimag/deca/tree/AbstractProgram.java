package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Entry point for contextual verifications and code generation from outside the package.
 * 
 * @author gl28
 * @date 01/01/2022
 *
 */
public abstract class AbstractProgram extends Tree {
    public abstract void verifyProgram(DecacCompiler compiler) throws ContextualError;
    public abstract void codeGenProgram(DecacCompiler compiler);
    public abstract void codeGenProgramByte(JavaCompiler javaCompiler, String name, String className);
}
