package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

public class Utils {
    public static DVal ImmediateFromType(Type type) {
        if(type.isInt() || type.isString() || type.isBoolean()) {
            return new ImmediateInteger(0);
        }
        else if(type.isFloat()) {
            return new ImmediateFloat(0.0F);
        } else if(type.isClass()) {
            return new NullOperand();
        } else {
            return null;
        }
    }

    public static void handleError(DecacCompiler compiler) {
        compiler.addLabel(new Label("stack_overflow_error"));
        compiler.addInstruction(new WSTR("Error: StackOverflow"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("overflow_error"));
        compiler.addInstruction(new WSTR("Error: StackOverflow"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("io_error"));
        compiler.addInstruction(new WSTR("Error: Input / Output error"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("div_error"));
        compiler.addInstruction(new WSTR("Error: Division by zero"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("seg_fault"));
        compiler.addInstruction(new WSTR("Error: Segmentation Fault"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("heap_overflow_error"));
        compiler.addInstruction(new WSTR("Error: the heap is full"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("cast_error"));
        compiler.addInstruction(new WSTR("Error: Impossible Cast"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());
    }

    public static void loadExpr(DecacCompiler compiler, AbstractExpr expr, GPRegister register) {
        DVal val = expr.getDVal();
        if (val != null) {
            compiler.addInstruction(new LOAD(val, register));
        } else {
            expr.codeGenExprOnRegister(compiler, register);
        }
    }

    public static void instanceOf(DecacCompiler compiler, AbstractExpr expr, ClassType type, boolean negation, Label label, GPRegister tmpRegister) {
        loadExpr(compiler, expr, tmpRegister);
        compiler.addInstruction(new LOAD(new RegisterOffset(0, tmpRegister), tmpRegister));
        Label endLabel = compiler.getLabelManager().getNextLabel("cast", "end");

        if (negation) {
            instanceOfComputation(compiler, expr, type, label, endLabel, tmpRegister);
        } else {
            instanceOfComputation(compiler, expr, type, endLabel, label, tmpRegister);
        }
        compiler.addLabel(endLabel);
    }

    private static void instanceOfComputation(DecacCompiler compiler, AbstractExpr expr, ClassType type, Label trueLabel, Label falseLabel, GPRegister tmpRegister) {
        boolean first = true;
        while (type.getDefinition().getSuperClass() != null) {
            compiler.addInstruction(new LEA(type.getDefinition().getdAddrVTable(), Register.R0));
            compiler.addInstruction(new CMP(tmpRegister, Register.R0));
            if (first) {
                compiler.addInstruction(new BEQ(trueLabel));
                first = false;
            } else {
                compiler.addInstruction(new BEQ(falseLabel));
            }
            type = type.getDefinition().getSuperClass().getType();
        }
        compiler.addInstruction(new BRA(trueLabel));
    }

}
