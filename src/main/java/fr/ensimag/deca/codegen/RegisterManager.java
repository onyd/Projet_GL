package fr.ensimag.deca.codegen;

public class RegisterManager {
    private int nbRegister;
    private boolean[] occupiedRegister;

    public RegisterManager(int nbRegister) {
        this.nbRegister = nbRegister;
        this.occupiedRegister = new boolean[nbRegister];
    }

    /**
     * get a register that isn't occupied.
     * @return the register number or -1 if no register are available
     */
    private int freeRegister() {
        int i = 0;
        while(i < nbRegister) {
            if(!this.occupiedRegister[i]) {
                return i;
            }
        }
        return -1;
    }
}
