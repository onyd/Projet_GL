package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class Selection extends AbstractLValue {
    private AbstractExpr expr;
    private AbstractIdentifier fieldIdent;

    public Selection(AbstractExpr expr, AbstractIdentifier fieldIdent) {
        super();
        Validate.notNull(expr);
        Validate.notNull(fieldIdent);
        this.expr = expr;
        this.fieldIdent = fieldIdent;
    }

    @Override
    public boolean isSelection() {
        return true;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type type = expr.verifyExpr(compiler, localEnv, currentClass);
        ClassDefinition class2 = type.asClassType("Cannot select field on " + type, getLocation()).getDefinition();
        fieldIdent.verifyField(compiler, class2.getMembers());
        setType(fieldIdent.getType());
        return getType();
    }

    /**
     * Assign the selection with the value stored on R1
     * @param compiler
     */
    protected void codeGenAssignFromR1(DecacCompiler compiler) {
        compiler.addInstruction(new LOAD(expr.getDVal(), Register.R0));
        compiler.addInstruction(new CMP(new NullOperand(), Register.R0));
        compiler.addInstruction(new BEQ(new Label("seg_fault")));
        compiler.addInstruction(new STORE(Register.R1, new RegisterOffset(fieldIdent.getFieldDefinition().getIndex(), Register.R0)));
    }

    @Override
    public void codeGenExprOnRegister(DecacCompiler compiler, GPRegister register) {
        compiler.addInstruction(new LOAD(expr.getDVal(), register));
        compiler.addInstruction(new CMP(new NullOperand(), register));
        compiler.addInstruction(new BEQ(new Label("seg_fault")));
        compiler.addInstruction(new LOAD(new RegisterOffset(fieldIdent.getFieldDefinition().getIndex(), register), register));
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        codeGenExprOnR1(compiler);
        if(fieldIdent.getFieldDefinition().getType().isInt()) {
            compiler.addInstruction(new WINT());
        } else if(fieldIdent.getFieldDefinition().getType().isFloat()) {
            compiler.addInstruction(new WFLOAT());
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(".");
        fieldIdent.decompile(s);
        s.println();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        fieldIdent.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        fieldIdent.iter(f);
    }
}
