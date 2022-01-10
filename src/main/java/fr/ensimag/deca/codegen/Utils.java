package fr.ensimag.deca.codegen;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

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
}
