package fr.ensimag.deca.tree;

import fr.ensimag.deca.codegen.Utils;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import org.apache.commons.lang.Validate;

/**
 * Full if/else if/else statement.
 *
 * @author gl28
 * @date 01/01/2022
 */
public class IfThenElse extends AbstractInst {
    
    private final AbstractExpr condition;
    private final ListInst thenBranch;
    private ListInst elseBranch;

    public IfThenElse(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        condition.verifyCondition(compiler, localEnv,currentClass);
        thenBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
        elseBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        Label endLabel = compiler.getLabelManager().getNextLabel("END", "IF");

        codeGenInst(compiler, endLabel);

        compiler.addLabel(endLabel);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, Label endLabel) {
        if (!elseBranch.isEmpty()) {
            Label elseLabel = compiler.getLabelManager().getNextLabel("ELSE");

            condition.codeGenBool(compiler, false, elseLabel);
            thenBranch.codeGenListInst(compiler, endLabel);
            compiler.addInstruction(new BRA(endLabel));
            compiler.addLabel(elseLabel);
            elseBranch.codeGenListInst(compiler, endLabel);
        } else {
            condition.codeGenBool(compiler, false, endLabel);
            thenBranch.codeGenListInst(compiler, endLabel);
        }
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("if(" );
        condition.decompile(s);
        s.println(") {");
        s.indent();
        thenBranch.decompile(s);
        s.unindent();

        s.println("} else {");
        s.indent();
        elseBranch.decompile(s);
        s.unindent();
        s.println();
        s.print("}");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }
}
