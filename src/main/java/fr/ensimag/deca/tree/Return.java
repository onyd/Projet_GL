package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class Return extends  AbstractInst {
    private AbstractExpr expr;

    public Return(AbstractExpr expr) {
        Validate.notNull(expr);
        this.expr = expr;
    }

    public void setExpression(AbstractExpr expr) {
        this.expr = expr;
    }

    public AbstractExpr getExpression() {
        return expr;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        setExpression(expr.verifyRValue(compiler, localEnv, currentClass, returnType));
        if (expr.getType().isVoid()) {
            throw new ContextualError("(3.24) Return value can't be void", getLocation());
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        expr.codeGenExprOnRegister(compiler, Register.R0);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        expr.decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
    }
}
