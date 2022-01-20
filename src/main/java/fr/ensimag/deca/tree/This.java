package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

public class This extends AbstractExpr {
    private final boolean implicit;

    public This(boolean implicit) {
        this.implicit = implicit;
    }

    @Override
    public boolean isImplicit() {
        return implicit;
    }

    @Override
    public DVal getDVal() {
        return new RegisterOffset(-2, Register.LB);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        if (currentClass == null) {
            throw new ContextualError("This cannot be used in main program", getLocation());
        }
        setType(currentClass.getType());
        return getType();
    }

    @Override
    public void codeGenExprOnRegister(DecacCompiler compiler, GPRegister register) {
        compiler.addInstruction(new LOAD(getDVal(), register));
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if (!implicit) {
            s.print("this");
        }
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }
}
