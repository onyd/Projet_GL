package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

import java.io.PrintStream;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type = compiler.getEnvironmentType().get(compiler.getSymbolTable().create("boolean")).getType();
        this.setType(type);
        return type;
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        if(value) {
            compiler.addInstruction(new WSTR("true"));
        } else {
            compiler.addInstruction(new WSTR("false"));
        }
    }

    @Override
    public void codeGenExprOnRegister(DecacCompiler compiler, int register) {
        if(value) {
            compiler.addInstruction(new LOAD(1, Register.getR(register)));
        } else {
            compiler.addInstruction(new LOAD(1, Register.getR(register)));
        }
    }

    @Override
    public DVal getDVal() {
        if(value) {
            return new ImmediateInteger(1);
        }
        return new ImmediateInteger(0);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

}
