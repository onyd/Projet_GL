package fr.ensimag.deca.tree;

import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.objectweb.asm.MethodVisitor;

/**
 * String literal
 *
 * @author gl28
 * @date 01/01/2022
 */
public class StringLiteral extends AbstractStringLiteral {

    @Override
    public String getValue() {
        return value;
    }

    private String value;

    public StringLiteral(String value) {
        Validate.notNull(value);
        this.value = value;
        this.formatValue();
    }

    public void formatValue() {
        StringBuilder res = new StringBuilder();
        char[] chars = value.toCharArray();
        int i = 0;
        while(i < chars.length) {
            if(i+1 != chars.length) {
                if(((chars[i] == '\\') && (chars[i+1] == '\\')) || ((chars[i] == '\\') && (chars[i+1] == '\"'))) {
                    i++;
                }
            }
            res.append(chars[i]);
            i++;
        }
        this.value = res.toString();
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass) throws ContextualError {
        Type type = new StringType(compiler.getSymbolTable().create("string"));
        this.setType(type);
        this.value = this.value.substring(1, this.value.length() - 1);
        return type;
    }

    @Override
    protected void codeGenPrint(IMACompiler compiler, boolean printHex) {
        compiler.addInstruction(new WSTR(new ImmediateString(value)));
    }

    @Override
    public void codeGenExprByteOnStack(JavaCompiler javaCompiler) {
        MethodVisitor methodVisitor = javaCompiler.getMethodVisitor();
        methodVisitor.visitLdcInsn(value);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(this.value);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }
    
    @Override
    String prettyPrintNode() {
        return "StringLiteral (" + value + ")";
    }
}
