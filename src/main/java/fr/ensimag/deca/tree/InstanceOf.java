package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.codegen.Utils;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class InstanceOf extends AbstractExpr {
    private final AbstractExpr expr;
    private final  AbstractIdentifier type;

    public InstanceOf(AbstractExpr expr, AbstractIdentifier type) {
        Validate.notNull(expr);
        Validate.notNull(type);
        this.expr = expr;
        this.type = type;
    }

    @Override
    public void decompile(IndentPrintStream s) {

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        type.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        type.iter(f);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        ClassType type1 = expr.verifyExpr(compiler, localEnv, currentClass).asClassType("(3.40) Left side of instanceof must have class type", getLocation());
        ClassType type2 = type.verifyType(compiler).asClassType("(3.40) Right side of instanceof must be a class identifier", getLocation());

        if (type2.isClass() && type1.isClassOrNull()) {
            setType(compiler.getEnvironmentType().get(compiler.BOOLEAN_SYMBOL).getType());
        }

        return getType();
    }

    @Override
    public void codeGenExprOnRegister(IMACompiler compiler, GPRegister register) {
        System.out.println(expr.getType().isSubType(type.getType()));
        if (expr.getType().isSubType(type.getType())) {
            compiler.addInstruction(new LOAD(1, register));
        } else {
            compiler.addInstruction(new LOAD(0, register));
        }
    }

    @Override
    protected void codeGenBool(IMACompiler compiler, boolean negation, Label label) {
        if (negation) {
            if (expr.getType().isSubType(type.getType())) {
                compiler.addInstruction(new BRA(label));
            }
        } else {
            if (!expr.getType().isSubType(type.getType())) {
                compiler.addInstruction(new BRA(label));
            }
        }
    protected void codeGenBool(DecacCompiler compiler, boolean negation, Label label) {
        Utils.instanceOf(compiler, expr, (ClassType) type.getType(), negation, label, Register.R1);
    }
}
