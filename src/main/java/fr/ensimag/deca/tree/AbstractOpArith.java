package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WINT;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl28
 * @date 01/01/2022
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type leftType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rightType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if (leftType.isInt() && rightType.isInt()) {
            setType(compiler.getEnvironmentType().get(compiler.getSymbolTable().create("int")).getType());
        } else if (leftType.isInt() && rightType.isFloat()){
            setType(compiler.getEnvironmentType().get(compiler.getSymbolTable().create("float")).getType());
        } else if (leftType.isFloat() && rightType.isInt()) {
            setType(compiler.getEnvironmentType().get(compiler.getSymbolTable().create("float")).getType());
        } else if (leftType.isFloat() && leftType.isFloat()) {
            setType(compiler.getEnvironmentType().get(compiler.getSymbolTable().create("float")).getType());
        } else {
            throw new ContextualError("Arithmetic operation: " + getOperatorName() + " only accept ([int|float], [int|float]) as operands type", getLocation());
        }
        return getType();
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        codeGenExprOnR1(compiler);
        if(this.getType().isInt()) {
            compiler.addInstruction(new WINT());
        } else if(this.getType().isFloat()) {
            compiler.addInstruction(new WFLOAT());
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {

    }
}
