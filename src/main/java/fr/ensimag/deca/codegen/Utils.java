package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

public class Utils {
    public static DVal ImmediateFromType(Type type) {
        if(type.isInt() || type.isString() || type.isBoolean()) {
            return new ImmediateInteger(0);
        }
        else if(type.isFloat()) {
            return new ImmediateFloat(0.0F);
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

        compiler.addLabel(new Label("cast_error"));
        compiler.addInstruction(new WSTR("Error: Impossible cast"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());
    }

    public static void codeGenBool(DecacCompiler compiler, int register, boolean negation, Label label) {
        compiler.addInstruction(new CMP(0, Register.getR(register)));
        if (negation) {
            compiler.addInstruction(new BNE(label));
        } else {
            compiler.addInstruction(new BEQ(label));
        }
    }
}
