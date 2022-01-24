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
 * 
 * @author gl28
 * @date 01/01/2022
 */
public class ListInst extends TreeList<AbstractInst> {

    /**
     * Implements non-terminal "list_inst" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains "env_types" attribute
     * @param localEnv corresponds to "env_exp" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     * @param returnType
     *          corresponds to "return" attribute (void in the main bloc).
     */    
    public void verifyListInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        for(AbstractInst i: getList()) {
            i.verifyInst(compiler, localEnv, currentClass, returnType);
        }
    }

    public void codeGenListInst(IMACompiler compiler) {
        compiler.addComment("Begin the list of instructions");
        for (AbstractInst i : getList()) {
            i.codeGenInst(compiler);
        }
    }

    public void codeGenListInstByte(JavaCompiler javaCompiler) {
        for (AbstractInst i : getList()) {
            i.codeGenInstByte(javaCompiler);
        }
    }

    public void codeGenListInstByte(JavaCompiler javaCompiler, org.objectweb.asm.Label endLabel) {
        for (AbstractInst i : getList()) {
            i.codeGenInstByte(javaCompiler, endLabel);
        }
    }

    public void codeGenListInst(IMACompiler compiler, Label endLabel) {
        for (AbstractInst i : getList()) {
            i.codeGenInst(compiler, endLabel);
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractInst i : getList()) {
            i.decompileInst(s);
            s.println();
        }
    }
}
