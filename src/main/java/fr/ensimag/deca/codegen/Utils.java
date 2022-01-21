package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.JavaCompiler;
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
        } else if(type.isClass()) {
            return new NullOperand();
        } else {
            return null;
        }
    }

    public static void handleError(IMACompiler compiler) {
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

    public static void codeGenBool(IMACompiler compiler, GPRegister register, boolean negation, Label label) {
        compiler.addInstruction(new CMP(0, register));
        if (negation) {
            compiler.addInstruction(new BNE(label));
        } else {
            compiler.addInstruction(new BEQ(label));
        }
    }

    //for java bytecode
    public static void loadVariableOnStack(int index, Type type, JavaCompiler javaCompiler) {
        if(type.isInt() || type.isBoolean()) {
            javaCompiler.getMethodVisitor().visitIntInsn(javaCompiler.BIPUSH, 0);
            javaCompiler.getMethodVisitor().visitVarInsn(javaCompiler.ISTORE, index);
        } else if(type.isFloat()) {
            javaCompiler.getMethodVisitor().visitInsn(javaCompiler.FCONST_0);
            javaCompiler.getMethodVisitor().visitVarInsn(javaCompiler.FSTORE, index);
        }
    }
}
