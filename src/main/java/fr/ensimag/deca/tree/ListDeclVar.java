package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.LabelManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.TSTO;

/**
 * List of declarations (e.g. int x; float y,z).
 * 
 * @author gl28
 * @date 01/01/2022
 */
public class ListDeclVar extends AbstractListTree<AbstractDeclVar> {

    /**
     * Implements non-terminal "list_decl_var" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains the "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the "env_exp_r" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     */    
    void verifyListDeclVariable(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        for (AbstractDeclVar i: getList()) {
            i.verifyDeclVar(compiler, localEnv, currentClass);
        }
    }

    void codeGenListDeclVariable(DecacCompiler compiler) {
        compiler.addComment("Verify if we can add all the variable in the stack");
        compiler.addInstruction(new TSTO(this.getList().size()));
        if(!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(LabelManager.STACK_OVERFLOW_ERROR));
        }
        compiler.addComment("Beginning of the variable declaration");
        for(AbstractDeclVar declVar : this.getList()) {
            declVar.codeGenDeclVar(compiler);
        }
    }

    void codeGenListDeclVariableByte(JavaCompiler javaCompiler, int beginIndex)
    {
        int currentIndexVar = beginIndex;
        for (AbstractDeclVar declVar : this.getList()){
            declVar.codeGenDeclVarByte(javaCompiler, currentIndexVar);
            currentIndexVar++;
        }
    }
}
