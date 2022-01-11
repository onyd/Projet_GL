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
    public int getFreeRegister() {
        //R0 and R1 not important
        int i = 2;
        while(i < nbRegister) {
            if(!this.occupiedRegister[i]) {
                this.occupiedRegister[i] = true;
                return i;
            }
        }
        return -1;
    }

    public void releaseRegister(int i) {
        this.occupiedRegister[i] = false;
    }
}
