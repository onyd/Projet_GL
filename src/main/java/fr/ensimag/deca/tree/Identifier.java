package fr.ensimag.deca.tree;

import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import java.io.PrintStream;


import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;

/**
 * Deca Identifier
 *
 * @author gl28
 * @date 01/01/2022
 */
public class Identifier extends AbstractIdentifier {
    
    @Override
    protected void checkDecoration() {
        if (getDefinition() == null) {
            throw new DecacInternalError("Identifier " + this.getName() + " has no attached Definition");
        }
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a class definition.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a method definition.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * LValueDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError
     *             if the definition is not an lvalue definition.
     */
    @Override
    public LValueDefinition getLValueDefinition() {
        try {
            return (LValueDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getLValueDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ParamDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public ParamDefinition getParamDefinition() {
        try {
            return (ParamDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a param identifier, you can't call getParamDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a ExpDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }

    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Symbol getName() {
        return name;
    }

    private Symbol name;

    @Override
    public boolean isIdentifier() {
        return true;
    }

    public Identifier(Symbol name) {
        Validate.notNull(name);
        this.name = name;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass) throws ContextualError {
        // Try to find the variable in local env
        ExpDefinition expDef = localEnv.get(getName());
        if(expDef == null && currentClass == null) {
            throw new ContextualError("(0.1) The identifier is not declared", getLocation());
        } else if (expDef == null) {
            // Try to find variable as class field
            expDef = currentClass.getMembers().get(getName());
            if (expDef == null) {
                throw new ContextualError("(0.1) The identifier is not declared", this.getLocation());
            }
            if (!expDef.isField())
                throw new ContextualError("(0.1) The field is not declared", getLocation());
        }

        setType(expDef.getType());
        setDefinition(expDef);
        return this.getType();
    }

    /**
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     * @param compiler contains "env_types" attribute
     */
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
        TypeDefinition typeDef = compiler.getEnvironmentType().get(getName());
        if (typeDef == null) {
            throw new ContextualError("(0.2) The identifier has an invalid type", this.getLocation());
        }
        setType(typeDef.getType());
        setDefinition(typeDef);
        return getType();
    }

    @Override
    public Type verifyField(DecacCompiler compiler, EnvironmentExp localEnv) throws ContextualError {
        ExpDefinition def = localEnv.get(getName());
        if (def == null)
            throw new ContextualError("(3.70) Invalid field identifier", getLocation());
        FieldDefinition fieldDef = def.asFieldDefinition("(3.70) Identifier must be a field", getLocation());
        setType(fieldDef.getType());
        setDefinition(fieldDef);
        return getType();
    }

    @Override
    public Type verifyMethod(DecacCompiler compiler, EnvironmentExp localEnv) throws ContextualError {
        ExpDefinition def = localEnv.get(getName());
        if (def == null) {
            throw new ContextualError("(3.72) Invalid identifier", getLocation());
        }
        MethodDefinition methodDef = def.asMethodDefinition("(3.72) Identifier must be a method", getLocation());
        setType(methodDef.getType());
        setDefinition(methodDef);
        return getType();
    }

    @Override
    protected void codeGenPrint(IMACompiler compiler, boolean printHex) {
        if(this.getExpDefinition().getType().isInt()) {
            compiler.getStack().getVariableFromStackOnR1(this);
            compiler.addInstruction(new WINT());
        } else if(this.getExpDefinition().getType().isFloat()) {
            compiler.getStack().getVariableFromStackOnR1(this);
            if (printHex) {
                compiler.addInstruction(new WFLOATX());
            } else {
                compiler.addInstruction(new WFLOAT());
            }
        } else if(this.getExpDefinition().getType().isString()) {
            int position = ((RegisterOffset) this.getExpDefinition().getOperand()).getOffset();
            for(int i = 0; i < this.getExpDefinition().getSizeOnStack(); i++) {
                compiler.getStack().getVariableFromStackOnR1(this, position);
                compiler.addInstruction(new WUTF8());
                position++;
            }
        }
    }

    @Override
    public void codeGenExprOnRegister(IMACompiler compiler, GPRegister register) {
        if(this.definition.isField()) {
            // implicit selection of a field in a class
            compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), register));
            compiler.addInstruction(new LOAD(new RegisterOffset(this.getFieldDefinition().getIndex(), register), register));
        } else {
            compiler.addInstruction(new LOAD(getExpDefinition().getOperand(), register));
        }
    }

    protected void codeGenBool(IMACompiler compiler, boolean negation, Label label) {
        compiler.addInstruction(new LOAD(getExpDefinition().getOperand(), Register.R0));
        compiler.addInstruction(new CMP(0, Register.R0));
        if (negation) {
            compiler.addInstruction(new BNE(label));
        } else {
            compiler.addInstruction(new BEQ(label));
        }
    }

    @Override
    public void codeGenExprByteOnStack(JavaCompiler javaCompiler) {
        if(this.getExpDefinition().isField()) {
            javaCompiler.getMethodVisitor().visitIntInsn(javaCompiler.ALOAD, 0);
            javaCompiler.getMethodVisitor().visitFieldInsn(javaCompiler.GETFIELD,
                    this.getFieldDefinition().getClassName(),
                    name.getName(),
                    this.getJavaType());
        } else {
            loadFromType(javaCompiler, getType(), getExpDefinition().getIndexOnStack());
        }
    }

    public static void loadFromType(JavaCompiler javaCompiler, Type type, int index) {
        if (type.isInt()) {
            javaCompiler.getMethodVisitor().visitVarInsn(javaCompiler.ILOAD, index);
        } else if(type.isFloat()) {
            javaCompiler.getMethodVisitor().visitVarInsn(javaCompiler.FLOAD, index);
        } else if (type.isBoolean()) {
            javaCompiler.getMethodVisitor().visitVarInsn(javaCompiler.ILOAD, index);
        } else if(type.isClass()) {
            javaCompiler.getMethodVisitor().visitIntInsn(javaCompiler.ALOAD, index);
        }
    }

    @Override
    protected void codeGenBoolByte(JavaCompiler javaCompiler, boolean negation, org.objectweb.asm.Label label) {
        codeGenExprByteOnStack(javaCompiler);
        if (negation) {
            javaCompiler.getMethodVisitor().visitJumpInsn(javaCompiler.IFNE, label);
        } else {
            javaCompiler.getMethodVisitor().visitJumpInsn(javaCompiler.IFEQ, label);
        }
    }

    @Override
    public DVal getDVal() {
        return this.getExpDefinition().getOperand();
    }

    private Definition definition;

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(name.toString());
    }

    @Override
    String prettyPrintNode() {
        return "Identifier (" + getName() + ")";
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }

}
