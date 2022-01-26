package fr.ensimag.deca.tree;

import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.Label;
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
    protected void codeGenInst(IMACompiler compiler) {
        if (compiler.getCompilerOptions().getDoOptimizations()) {
            if (condition.isTriviallyTrue()) {
                thenBranch.codeGenListInst(compiler);
                return;
            }
            if (condition.isTriviallyFalse()) {
                elseBranch.codeGenListInst(compiler);
                return;
            }
        }

        Label endLabel = compiler.getLabelManager().getNextLabel("END", "IF");
        codeGenInst(compiler, endLabel);
        compiler.addLabel(endLabel);
    }

    @Override
    protected void codeGenInstByte(JavaCompiler javaCompiler) {
        org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();

        codeGenInstByte(javaCompiler, endLabel);

        javaCompiler.getMethodVisitor().visitLabel(endLabel);
    }

    @Override
    protected void codeGenInst(IMACompiler compiler, Label endLabel) {
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
    protected void codeGenInstByte(JavaCompiler javaCompiler, org.objectweb.asm.Label endLabel) {
        if (!elseBranch.isEmpty()) {
            org.objectweb.asm.Label elseLabel = new org.objectweb.asm.Label();

            condition.codeGenBoolByte(javaCompiler, false, elseLabel);
            thenBranch.codeGenListInstByte(javaCompiler, endLabel);
            javaCompiler.getMethodVisitor().visitJumpInsn(javaCompiler.GOTO, endLabel);
            javaCompiler.getMethodVisitor().visitLabel(elseLabel);
            elseBranch.codeGenListInstByte(javaCompiler, endLabel);
        } else {
            condition.codeGenBoolByte(javaCompiler, false, endLabel);
            thenBranch.codeGenListInstByte(javaCompiler, endLabel);
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
