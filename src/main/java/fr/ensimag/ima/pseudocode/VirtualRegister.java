package fr.ensimag.ima.pseudocode;

import fr.ensimag.deca.tools.DecacInternalError;

/**
 * Virtual register is a register which number is decided after its creation
 */
public class VirtualRegister extends GPRegister {

    public VirtualRegister() {
        super("R", -1);
    }

    public void setNumber(int number) throws  DecacInternalError {
        if (number > 0 && number < 16) {
            this.number = number;
            this.name = this.name + number;
        }
        else throw new DecacInternalError("Register cannot have number " + number);
    }
}
