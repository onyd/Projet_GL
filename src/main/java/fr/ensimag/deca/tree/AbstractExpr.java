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

    /**
     * Set the type decoration associated to this expression (i.e. the type computed by contextual verification).
     */
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
            // TODO numeric cast ?
        } else {
            getType().asClassType("(3.28) expression type is not compatible", getLocation());
        }
        return this;
    }

    /**
     * Verify that context rules are respected and decorates the node accordingly
     * @param compiler contains the "env_types" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass
     *          corresponds to the "class" attribute (null in the main bloc).
     * @param returnType
     * @throws ContextualError
     */
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

    /**
     * Generate assembly for instructions
     * @param compiler
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Generate code to load the expression on the register R1
     * @param compiler
     */
    public void codeGenExprOnR1(DecacCompiler compiler) {
        this.codeGenExprOnRegister(compiler, 1);
    }

    /**
     * Generate code to load on the expression on the register i
     * @param compiler
     * @param register
     */
    public void codeGenExprOnRegister(DecacCompiler compiler, int register) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Generate assembly for global node of boolean/comparison expression
     * @param compiler
     * @param register the register where to store the result
     * @param isNegated should the expression be negated (use to eliminate negation at compile time)
     */
    public void codeGenExprOnRegister(DecacCompiler compiler, int register, boolean isNegated) {
        Label label = compiler.getManageCodeGen().getLabelManager().getNextLabel("E");
        Label endLabel = compiler.getManageCodeGen().getLabelManager().getNextLabel("E", "END");
        compiler.addInstruction(new LOAD(0, Register.getR(register))); // Default expr is evaluated to false

        codeGenBool(compiler, !isNegated, label);
        compiler.addInstruction(new BRA(endLabel));

        // True result label
        compiler.addLabel(label);
        compiler.addInstruction(new LOAD(1, Register.getR(register)));

        // False result label
        compiler.addLabel(endLabel);

    }

    /**
     * Generate assembly for inner boolean/comparison expression
     * @param compiler
     * @param negation should the expression evaluated tu true if negation
     * @param label the true label
     */
    protected void codeGenBool(DecacCompiler compiler, boolean negation, Label label) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Generate code to make the operation
     * @param dVal
     * @param register
     */
    public void codeMnemo(DecacCompiler compiler, DVal dVal, int register) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * return the DVal of the expression (if it is possible), else return null
     * @return
     */
    public DVal getDVal() {
        return null;
    }

    /**
     * Decompile the current instruction in the stream
     * @param s
     */
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
