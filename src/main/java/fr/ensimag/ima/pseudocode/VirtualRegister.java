package fr.ensimag.ima.pseudocode;

import fr.ensimag.deca.tools.DecacInternalError;

/**
 * Virtual register is a register which number is decided after its creation
 */
public class VirtualRegister extends GPRegister {

    public GPRegister getDst() {
        return dst;
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

    /**
     * Precise to RegisterAllocator where to restore old value
     */
    private GPRegister dst = null;

    public VirtualRegister() {
        super("R", -1);
    }

    public VirtualRegister(GPRegister dst) {
        super("R", -1);
        this.dst = dst;
    }

    public void setNumber(int number) throws  DecacInternalError {
        if (number > 0 && number < 16) {
            this.number = number;
            this.name = this.name + number;
        }
        else throw new DecacInternalError("Register cannot have number " + number);
    }

    @Override
    public int getNumber() {
        if (number == -1)
            throw new DecacInternalError("VirtualRegister has an invalid number, please call setNumber before using it");
        return super.getNumber();
    }
}
