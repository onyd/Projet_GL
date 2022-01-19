package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import org.objectweb.asm.MethodVisitor;

/**
 * @author gl28
 * @date 01/01/2022
 */
public class Println extends AbstractPrint {

    /**
     * @param arguments arguments passed to the print(...) statement.
     * @param printHex if true, then float should be displayed as hexadecimal (printlnx)
     */
    public Println(boolean printHex, ListExpr arguments) {
        super(printHex, arguments);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new WNL());
    }
    @Override
    protected void codeGenInstByte(DecacCompiler compiler, JavaCompiler javaCompiler)
    {
        super.codeGenInstByte(compiler,javaCompiler);
        //javaCompiler.addInstruction(-1); // devra remplacer le code suivant

        MethodVisitor methodVisitor = javaCompiler.getMethodVisitor();
        // L'instruction System.out.PrintStream.println
        methodVisitor.visitFieldInsn(javaCompiler.GETSTATIC,
                "java/lang/System",
                "out",
                "Ljava/io/PrintStream;");
        methodVisitor.visitLdcInsn("\n");
        methodVisitor.visitMethodInsn(javaCompiler.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "print",
                "(Ljava/lang/String;)V",
                false);
    }

    @Override
    String getSuffix() {
        return "ln";
    }
}
