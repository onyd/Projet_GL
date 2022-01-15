package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.VirtualRegister;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

import java.util.*;

/**
 * Representation of an IMA program which registers can be VirtualRegister allocated safely by pushing on stack if necessary
 */
public class RegisterAllocator {
    private Set<VirtualRegister> registers;
    private LinkedList<Integer> toRelease = new LinkedList<>();
    private LinkedList<Integer> toRestore = new LinkedList<>();

    public RegisterAllocator() {
        this.registers = new HashSet<>();
    }

    public void addVirtualRegister(VirtualRegister register) {
        this.registers.add(register);
    }

    public void allocateRegisters(DecacCompiler compiler) {
        int i = 2;
        for (VirtualRegister register : registers) {
            // Try to find a regsiter
            int registerNumber = compiler.getManageCodeGen().getRegisterManager().getFreeRegister();
            if (registerNumber == -1) {
                // No register is available so we save on stack
                compiler.addInstruction(new PUSH(Register.getR(i)));
                register.setNumber(i);
                if (register.getDst() == null) {
                    // No destination precised => we'll restore it on the same register
                    toRestore.addFirst(i);
                    i++;
                } else {
                    // Destination has been precised => we retore old value on it
                    toRestore.addFirst(register.getDst().getNumber());
                }
            } else {
                // The register was free => we just release it later
                toRelease.add(registerNumber);
                register.setNumber(registerNumber);
            }

        }
    }

    public void restoreFromStack(DecacCompiler compiler) {
        for (Integer registerNumber : toRestore) {
            compiler.addInstruction(new POP(Register.getR(registerNumber)));
        }
        for(Integer registerNumber : toRelease) {
            compiler.getManageCodeGen().getRegisterManager().releaseRegister(registerNumber);
        }
    }


}
