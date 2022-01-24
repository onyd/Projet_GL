package fr.ensimag.deca.tree;

import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;

/**
 * Instruction
 *
 * @author gl28
 * @date 01/01/2022
 */
public abstract class AbstractInst extends Tree {

    /**
     * Implements non-terminal "inst" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains the "env_types" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass 
     *          corresponds to the "class" attribute (null in the main bloc).
     * @param returnType
     *          corresponds to the "return" attribute (void in the main bloc).
     */    
    protected abstract void verifyInst(DecacCompiler compiler,
                                       EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError;

    /**
     * Generate assembly code for the instruction.
     * 
     * @param compiler
     */
    protected abstract void codeGenInst(IMACompiler compiler);

    /**
     * Generate assembly code for the instruction and give the endLabel of
     * the enclosing IfThenElse branch to the deeper IfThenElse
     *
     * @param compiler
     */
    protected void codeGenInst(IMACompiler compiler, Label ifEndLabel) {
        codeGenInst(compiler);
    };


    /**
     * Generate bytecode for the instruction.
     * @param javaCompiler
     */
    protected  void codeGenInstByte(JavaCompiler javaCompiler) {
        throw new UnsupportedOperationException();
    }

    /**
     * Generate bytecode for the instruction and give the endLabel of the enclosing IfThenElse branch to the deeper IfThenElse.
     *
     * @param javaCompiler
     */
    protected  void codeGenInstByte(JavaCompiler javaCompiler, org.objectweb.asm.Label endLabel){
        codeGenInstByte(javaCompiler);
    }

    /**
     * Decompile the tree, considering it as an instruction.
     *
     * In most case, this simply calls decompile(), but it may add a semicolon if needed
     */
    protected void decompileInst(IndentPrintStream s) {
        decompile(s);
    }
}
