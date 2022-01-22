package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.codegen.LabelManager;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BOV;

/**
 * read...() statement.
 *
 * @author gl28
 * @date 01/01/2022
 */
public abstract class AbstractReadExpr extends AbstractExpr {

    public AbstractReadExpr() {
        super();
    }

    @Override
    protected void codeGenInst(IMACompiler compiler) {
        this.codeMnemo(compiler, null, Register.R0);
        if(!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(LabelManager.IO_ERROR));
        }
    }

    @Override
    public void codeGenExprOnRegister(IMACompiler compiler, GPRegister register) {
        this.codeMnemo(compiler, null, Register.R0);
        if(!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(LabelManager.IO_ERROR));
        }
    }

    @Override
    public void codeGenExprByteOnStack(JavaCompiler javaCompiler) {
        javaCompiler.getMethodVisitor().visitTypeInsn(javaCompiler.NEW, "java/util/Scanner");
        javaCompiler.getMethodVisitor().visitInsn(javaCompiler.DUP);
        javaCompiler.getMethodVisitor().visitFieldInsn(javaCompiler.GETSTATIC,
                "java/lang/System",
                "in",
                "Ljava/io/InputStream;");
        javaCompiler.getMethodVisitor().visitMethodInsn(javaCompiler.INVOKESPECIAL,
                "java/util/Scanner",
                "<init>",
                "(Ljava/io/InputStream;)V",false);
        javaCompiler.getMethodVisitor().visitMethodInsn(javaCompiler.INVOKEVIRTUAL,
                "java/util/Scanner",
                readFunc(),
                "()" + readType(),
                false);
    }

    public abstract String readType();
    public abstract String readFunc();
}
