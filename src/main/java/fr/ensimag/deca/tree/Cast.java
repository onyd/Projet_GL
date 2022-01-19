package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class Cast extends AbstractExpr {
    private final AbstractIdentifier type;
    private final AbstractExpr expr;

    public Cast(AbstractIdentifier type, AbstractExpr expr) {
        Validate.notNull(type);
        Validate.notNull(expr);
        this.type = type;
        this.expr = expr;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type type2 = type.verifyType(compiler);
        Type type1 = expr.verifyExpr(compiler, localEnv, currentClass);
        if (type1.isVoid()) {
            throw new ContextualError("(3.39) Can't cast void type", getLocation());
        }

        if (!type1.isCastCompatible(type2))
            throw new ContextualError("(3.39) Can't cast " + type2 + " to " + type1, getLocation());

        setType(type2);
        return getType();
    }

    @Override
    public void codeGenExprOnRegister(DecacCompiler compiler, GPRegister register) {
        if(type.getType().isInt()) {
            expr.codeGenExprOnRegister(compiler, register);
            compiler.addInstruction(new INT(register, register));
        } else if(type.getType().isFloat()) {
            expr.codeGenExprOnRegister(compiler, register);
            compiler.addInstruction(new FLOAT(register, register));
        } else if(type.getType().isClass()) {
            if(expr.getType().isClass()) {
                if(!((ClassType) expr.getType()).isSubClassOf((ClassType) type.getType())) {
                    compiler.addInstruction(new BRA(new Label("cast_error")));
                } else {
                    compiler.addInstruction(new LOAD(expr.getDVal(), register));
                    compiler.addInstruction(new CMP( new NullOperand(), register));
                    if(compiler.getCompilerOptions().getNoCheck()) {
                        compiler.addInstruction(new BEQ(new Label("seg_fault")));
                    }
                }
            }
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // TODO cast decompile
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        expr.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        expr.iter(f);
    }
}
