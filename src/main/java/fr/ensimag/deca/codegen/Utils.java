package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

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
    }
}
