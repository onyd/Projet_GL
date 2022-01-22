package fr.ensimag.deca.tree;

import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.objectweb.asm.MethodVisitor;

/**
 * Print statement (print, println, ...).
 *
 * @author gl28
 * @date 01/01/2022
 */
public abstract class AbstractPrint extends AbstractInst {

    private boolean printHex;
    private ListExpr arguments = new ListExpr();
    
    abstract String getSuffix();

    public AbstractPrint(boolean printHex, ListExpr arguments) {
        Validate.notNull(arguments);
        this.arguments = arguments;
        this.printHex = printHex;
    }

    public ListExpr getArguments() {
        return arguments;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        for(AbstractExpr expr: getArguments().getList()) {
            Type type = expr.verifyExpr(compiler, localEnv, currentClass);
            if(!(type.isInt() || type.isFloat() || type.isString())) {
                throw new ContextualError("(3.31) Wrong type for the print function. It should be int, float or string", expr.getLocation());
            }
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        for (AbstractExpr a : getArguments().getList()) {
            a.codeGenPrint(compiler, printHex);
        }
    }

    @Override
    protected void codeGenInstByte(JavaCompiler javaCompiler)
    {
        for (AbstractExpr a : getArguments().getList()) {
            MethodVisitor methodVisitor = javaCompiler.getMethodVisitor();
            // Instruction System.out.PrintStream.println
            methodVisitor.visitFieldInsn(javaCompiler.GETSTATIC,
                    "java/lang/System",
                    "out",
                    "Ljava/io/PrintStream;");
            a.codeGenExprByteOnStack(javaCompiler);
            methodVisitor.visitMethodInsn(javaCompiler.INVOKEVIRTUAL,
                    "java/io/PrintStream",
                    "print",
                    "(" + a.getJavaType() + ")V",
                    false);
        }
    }

    private boolean getPrintHex() {
        return printHex;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if (printHex) {
            s.print("print" + getSuffix() + "x(");
        }else{
            s.print("print" + getSuffix() + "(");
        }
        arguments.decompile(s);
        s.print(");");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        arguments.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        arguments.prettyPrint(s, prefix, true);
    }

}
