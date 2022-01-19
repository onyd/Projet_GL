package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import fr.ensimag.deca.tools.SymbolTable;
import org.apache.commons.lang.Validate;

/**
 * @author gl28
 * @date 01/01/2022
 */
public class DeclVar extends AbstractDeclVar {

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        type.verifyType(compiler);
        if (type.getType().isVoid()) {
            throw new ContextualError("(3.17) Variable declaration with type void is forbidden", getLocation());
        }
        VariableDefinition varDef = new VariableDefinition(type.getType(), this.getLocation());
        try {
            localEnv.declare(varName.getName(), varDef);
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError("(3.17) The identifier is already declared", this.getLocation());
        }
        varName.verifyExpr(compiler, localEnv, currentClass);
        initialization.verifyInitialization(compiler, type.getType(), localEnv, currentClass);
    }

    @Override
    protected void codeGenDeclVar(DecacCompiler compiler) {
        compiler.getStack().declareVariableOnStack((Identifier) this.varName, this.initialization);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
        initialization.decompile(s);
        s.print(";");
        s.println();
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
}
