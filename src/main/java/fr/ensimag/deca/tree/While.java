package fr.ensimag.deca.tree;

import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.Utils;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import org.apache.commons.lang.Validate;

/**
 *
 * @author gl28
 * @date 01/01/2022
 */
public class While extends AbstractInst {
    private AbstractExpr condition;
    private ListInst body;

    public AbstractExpr getCondition() {
        return condition;
    }

    public ListInst getBody() {
        return body;
    }

    public While(AbstractExpr condition, ListInst body) {
        Validate.notNull(condition);
        Validate.notNull(body);
        this.condition = condition;
        this.body = body;
    }

    @Override
    protected void codeGenInst(IMACompiler compiler) {
        if (compiler.getCompilerOptions().getDoOptimizations() && condition.isTriviallyFalse()) {
            return;
        }
        Label condLabel = compiler.getLabelManager().getNextLabel("WHILE", "COND");
        Label beginLabel = compiler.getLabelManager().getNextLabel("WHILE", "BEGIN");
        compiler.addInstruction(new BRA(condLabel));
        compiler.addLabel(beginLabel);
        body.codeGenListInst(compiler);
        compiler.addLabel(condLabel);
        condition.codeGenBool(compiler, true, beginLabel);
    }

    @Override
    protected void codeGenInstByte(JavaCompiler javaCompiler) {
        org.objectweb.asm.Label condLabel = new org.objectweb.asm.Label();
        org.objectweb.asm.Label beginLabel = new org.objectweb.asm.Label();
        javaCompiler.getMethodVisitor().visitJumpInsn(javaCompiler.GOTO, condLabel);
        javaCompiler.getMethodVisitor().visitLabel(beginLabel);
        body.codeGenListInstByte(javaCompiler);
        javaCompiler.getMethodVisitor().visitLabel(condLabel);
        condition.codeGenBoolByte(javaCompiler, true, beginLabel);
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        getCondition().verifyCondition(compiler, localEnv, currentClass);
        getBody().verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("while (");
        getCondition().decompile(s);
        s.println(") {");
        s.indent();
        getBody().decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        condition.iter(f);
        body.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

}
