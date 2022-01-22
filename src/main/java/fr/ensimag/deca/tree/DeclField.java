package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import org.apache.commons.lang.Validate;
import org.objectweb.asm.FieldVisitor;

import java.io.PrintStream;

public class DeclField extends AbstractDeclField {
    private final AbstractIdentifier typeName;
    private final AbstractIdentifier fieldIdent;
    private  final AbstractInitialization initialization;
    private final Visibility visibility;

    public DeclField(AbstractIdentifier typeName, AbstractIdentifier fieldIdent, AbstractInitialization initialization, Visibility visibility) {
        Validate.notNull(typeName);
        Validate.notNull(fieldIdent);
        Validate.notNull(initialization);
        Validate.notNull(visibility);
        this.typeName = typeName;
        this.fieldIdent = fieldIdent;
        this.initialization = initialization;
        this.visibility = visibility;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if (visibility == Visibility.PROTECTED)
            s.print("protected ");
        typeName.decompile(s);
        s.print(" ");
        fieldIdent.decompile(s);
        initialization.decompile(s);
        s.print(";");
        s.println();
    }

    @Override
    String prettyPrintNode() {
        return "[visibility=" + visibility.toString() + "] " + super.prettyPrintNode();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        typeName.prettyPrint(s, prefix, false);
        fieldIdent.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        typeName.iter(f);
        fieldIdent.iter(f);
        initialization.iter(f);
    }

    @Override
    protected void verifyField(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        Type type = typeName.verifyType(compiler);
        if (type.isVoid()) {
            throw new ContextualError("(2.5) Field type cannot be void", getLocation());
        }

        // Ensure that the name is not used
        ExpDefinition def = currentClass.getSuperClass().getMembers().get(fieldIdent.getName());
        if (def != null) {
            if (!def.isField()) {
                throw new ContextualError("(2.5) Name has already been declared", getLocation());
            }
        }
        currentClass.incNumberOfFields();
        FieldDefinition fieldDef = new FieldDefinition(type, fieldIdent.getLocation(), visibility, currentClass, currentClass.getNumberOfFields());
        try {
            currentClass.getMembers().declare(fieldIdent.getName(), fieldDef);

        } catch (Environment.DoubleDefException e) {
            throw new ContextualError("Field has already been declared", getLocation());
        }
        fieldIdent.verifyExpr(compiler, currentClass.getMembers(), currentClass);
    }

    @Override
    protected void verifyFieldInit(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        initialization.verifyInitialization(compiler, typeName.getType(), currentClass.getMembers(), currentClass);
    }

    @Override
    protected void codeGenDeclField(IMACompiler compiler) {
        compiler.getStack().declareVariableOnAddressStoreOnHeap((Identifier) fieldIdent,
                initialization, new RegisterOffset(-2, Register.LB));
    }

    @Override
    protected void codeGenDeclFieldByte(JavaCompiler javaCompiler) {
        Object initialValue = null;
        if(typeName.getType().isInt() || typeName.getType().isBoolean()) {
            initialValue = 0;
        } else if(typeName.getType().isFloat()) {
            initialValue = 0.0;
        }
        FieldVisitor fv = javaCompiler.getClassWriter().visitField(
                            javaCompiler.ACC_PUBLIC,
                            fieldIdent.getName().getName(),
                            typeName.getJavaType(),
                            null,
                            initialValue);
        fv.visitEnd();
        if(!initialization.noInitialization()) {
            Initialization init = (Initialization) initialization;
            javaCompiler.getMethodVisitor().visitIntInsn(javaCompiler.ALOAD, 0);
            init.getExpression().codeGenExprByteOnStack(javaCompiler);
            javaCompiler.getMethodVisitor().visitFieldInsn(javaCompiler.PUTFIELD,
                    javaCompiler.getClassName(),
                    fieldIdent.getName().getName(),
                    typeName.getJavaType());
        }
    }
}
