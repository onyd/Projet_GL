package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.codegen.Utils;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.InlinePortion;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;
import java.util.Iterator;

public class MethodJavaBody extends AbstractMethodBody {
    private final StringLiteral java;

    private final String methodHeader;
    private AbstractIdentifier returnType;
    private final AbstractIdentifier methodIdent;
    private final ListDeclParam listParam;

    public MethodJavaBody(StringLiteral java, AbstractIdentifier type, AbstractIdentifier ident, ListDeclParam listParam) {
        Validate.notNull(java);
        Validate.notNull(type);
        Validate.notNull(ident);
        Validate.notNull(listParam);
        this.java = java;
        this.returnType = type;
        this.methodIdent = ident;
        this.listParam = listParam;

        String methodHeader =  "static " + type.getName().getName() + " " + ident.getName().getName() + "(";
        Iterator<AbstractDeclParam> it = listParam.getList().iterator();

        DeclParam param;
        if (it.hasNext()) {
            param = (DeclParam) it.next();
            methodHeader += param.getTypeName().getName().getName() + " " + param.getParamIdent().getName().getName();
        }

        while (it.hasNext()) {
            param = (DeclParam) it.next();
            methodHeader += ", " + param.getTypeName().getName().getName() + " " + param.getParamIdent().getName().getName();
        }
        methodHeader += ")";

        this.methodHeader = methodHeader;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("java (");
        java.decompile(s);
        s.print(");");
        s.println();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        java.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        java.iter(f);
    }

    @Override
    protected void verifyBody(DecacCompiler compiler, ClassDefinition currentClass, EnvironmentExp envExpParams, Type returnType) throws ContextualError {
        java.verifyExpr(compiler, envExpParams, currentClass);
        if (!compiler.getCompilerOptions().getJavaCompilation()) {
            throw new ContextualError("The option must be -java in order to use java compiler", getLocation());
        }
    }

    @Override
    protected void codeGenMethodBody(IMACompiler compiler) {
        throw new UnsupportedOperationException();
    }

    public void codeGenMethodBodyByte(JavaCompiler javaCompiler, int beginIndex) {
        javaCompiler.addJavaMethod(this.methodHeader + "{" + this.java.getValue() + "} %s");

        StringBuilder paramsJavaType = new StringBuilder();
        for (AbstractDeclParam param : listParam.getList()) {
            paramsJavaType.append(Utils.getJavaType(param.getParamIdent().getType()));
        }

        for (AbstractDeclParam p : listParam.getList()) {
            Identifier.loadFromType(javaCompiler, p.getParamIdent().getType(), p.getParamIdent().getExpDefinition().getIndexOnStack());
        }
        javaCompiler.getMethodVisitor().visitMethodInsn(javaCompiler.INVOKESTATIC, javaCompiler.getSourceName() + "MethodJavaBodies$" + javaCompiler.getClassName(), methodIdent.getName().getName(), "(" + paramsJavaType + ")" + methodIdent.getJavaType(), false);
        javaCompiler.getMethodVisitor().visitInsn(Return.getReturnCode(javaCompiler, returnType.getType()));
    }

}
