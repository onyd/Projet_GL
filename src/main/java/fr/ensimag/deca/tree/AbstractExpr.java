package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;

import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;

/**
 * Expression, i.e. anything that has a value.
 *
 * @author gl28
 * @date 01/01/2022
 */
public abstract class AbstractExpr extends AbstractInst {
    /**
     * @return true if the expression does not correspond to any concrete token
     * in the source code (and should be decompiled to the empty string).
     */
    boolean isImplicit() {
        return false;
    }

    public boolean isReadFloat() {
        return false;
    }

    public boolean isReadInt() {
        return false;
    }

    /**
     * Get the type decoration associated to this expression (i.e. the type computed by contextual verification).
     */
    public Type getType() {
        return type;
    }

    protected void setType(Type type) {
        Validate.notNull(type);
        this.type = type;
    }
    private Type type;

    @Override
    protected void checkDecoration() {
        if (getType() == null) {
            throw new DecacInternalError("Expression " + decompile() + " has no Type decoration");
        }
    }

    /**
     * Verify the expression for contextual error.
     * 
     * implements non-terminals "expr" and "lvalue" 
     *    of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  (contains the "env_types" attribute)
     * @param localEnv
     *            Environment in which the expression should be checked
     *            (corresponds to the "env_exp" attribute)
     * @param currentClass
     *            Definition of the class containing the expression
     *            (corresponds to the "class" attribute)
     *             is null in the main bloc.
     * @return the Type of the expression
     *            (corresponds to the "type" attribute)
     */
    public abstract Type verifyExpr(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * Verify the expression in right hand-side of (implicit) assignments 
     * 
     * implements non-terminal "rvalue" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  contains the "env_types" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass corresponds to the "class" attribute
     * @param expectedType corresponds to the "type1" attribute            
     * @return this with an additional ConvFloat if needed...
     */
    public AbstractExpr verifyRValue(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass, 
            Type expectedType)
            throws ContextualError {
        verifyExpr(compiler, localEnv, currentClass);
        if (expectedType.sameType(getType())) {
        } else if (getType().isInt() && expectedType.isFloat()) {
            ConvFloat newExpr = new ConvFloat(this);
            setType(compiler.getEnvironmentType().get(compiler.FLOAT_SYMBOL).getType());
            return newExpr;
        } else {
            getType().asClassType("(3.28) Expression type is not compatible", getLocation());
        }
        return this;
    }
    
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        verifyExpr(compiler, localEnv, currentClass);
    }

    /**
     * Verify the expression as a condition, i.e. check that the type is
     * boolean.
     *
     * @param localEnv
     *            Environment in which the condition should be checked.
     * @param currentClass
     *            Definition of the class containing the expression, or null in
     *            the main program.
     */
    void verifyCondition(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type = verifyExpr(compiler, localEnv, currentClass);
        if (!type.isBoolean()) {
            throw new ContextualError("(3.29) Condition must return a boolean", getLocation());
        }
    }

    /**
     * Generate code to print the expression
     *
     * @param compiler
     */
    protected void codeGenPrint(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Generate code to load the expression on the register R1
     * @param compiler
     */
    public void codeGenExprOnR1(DecacCompiler compiler) {
        this.codeGenExprOnRegister(compiler, Register.R1);
    }

    /**
     * generate code to load on the expression on the register
     * @param compiler
     * @param register
     */
    public void codeGenExprOnRegister(DecacCompiler compiler, GPRegister register) {
        Label label = compiler.getLabelManager().getNextLabel("E");
        Label endLabel = compiler.getLabelManager().getNextLabel("E", "END");
        compiler.addInstruction(new LOAD(0, register)); // Default expr is evaluated to false

        codeGenBool(compiler, true, label);
        compiler.addInstruction(new BRA(endLabel));

        // True result label
        compiler.addLabel(label);
        compiler.addInstruction(new LOAD(1, register));

        // False result label
        compiler.addLabel(endLabel);

    }

    protected void codeGenBool(DecacCompiler compiler, boolean negation, Label label) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * generate code to make the operation
     * @param dVal
     * @param register
     */
    public void codeMnemo(DecacCompiler compiler, DVal dVal, GPRegister register) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * return the DVal of the expression (if it is possible), else return null
     * @return
     */
    public DVal getDVal() {
        return null;
    }

    @Override
    protected void decompileInst(IndentPrintStream s) {
        decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Type t = getType();
        if (t != null) {
            s.print(prefix);
            s.print("type: ");
            s.print(t);
            s.println();
        }
    }
}
