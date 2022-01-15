package fr.ensimag.deca.codegen;

import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;

public class RegisterManager {
    private final int nbRegister;
    private boolean[] occupiedRegister;

    public RegisterManager(int nbRegister) {
        this.nbRegister = nbRegister;
        this.occupiedRegister = new boolean[nbRegister];
    }

    /**
     * Get a register that isn't occupied.
     * @return the register number or -1 if no register are available
     */
    public GPRegister getFreeRegister() {
        //R0 and R1 not important
        int i = 2;
        while(i < nbRegister) {
            if(!isOccupied(i)) {
                useRegister(i);
                return Register.getR(i);
            }
            i++;
        }
        return null;
    }

    public boolean isOccupied(int i) {
        return occupiedRegister[i];
    }
    public boolean isOccupied(GPRegister register) {
        return isOccupied(register.getNumber());
    }

    public void useRegister(int i) {
        if (i == 0 || i == 1) {
            throw new DecacInternalError("Registers R0 and R1 cannot be set occupied");
        }
        this.occupiedRegister[i] = true;
    }

    public void useRegister(GPRegister register) {
        useRegister(register.getNumber());
    }

    public void releaseRegister(int i) {
        this.occupiedRegister[i] = false;
    }

    public void releaseRegister(GPRegister register) {
        releaseRegister(register.getNumber());
    }

}
