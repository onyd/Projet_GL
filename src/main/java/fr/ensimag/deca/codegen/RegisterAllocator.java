package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.VirtualRegister;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

import java.util.*;

/**
 * Representation of an IMA program which registers can be VirtualRegister allocated safely by pushing on stack if necessary
 */
public class RegisterAllocator {
    private Set<VirtualRegister> virtualRegisters;
    private LinkedList<GPRegister> toRelease = new LinkedList<>();
    private LinkedList<GPRegister> toRestore = new LinkedList<>();

    public RegisterAllocator() {
        this.virtualRegisters = new HashSet<>();
    }

    public void addVirtualRegister(VirtualRegister register) {
        this.virtualRegisters.add(register);
    }


    public void allocateRegisters(IMACompiler compiler) {
        int i = 2;
        for (VirtualRegister register : virtualRegisters) {
            // Try to find a register
            GPRegister newRegister = compiler.getRegisterManager().getFreeRegister();
            if (newRegister == null) {
                // No register is available so we save on stack
                compiler.addInstruction(new PUSH(Register.getR(i)));
                register.setNumber(i);
                if (register.getDst() == null) {
                    // No destination specified => we'll restore it on the same register
                    toRestore.addFirst(Register.getR(i));
                    i++;
                } else {
                    // Destination has been specified => we restore old value on it
                    toRestore.addFirst(register.getDst());
                }
            } else {
                // The register was free => we just release it later
                toRelease.add(newRegister);
                register.setNumber(newRegister.getNumber());
            }

        }
    }

    public void restoreFromStack(IMACompiler compiler) {
        for (GPRegister register : toRestore) {
            compiler.addInstruction(new POP(register));
        }
        for(GPRegister register : toRelease) {
            compiler.getRegisterManager().releaseRegister(register);
        }
    }


}
