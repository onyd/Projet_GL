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
            int registerNumber = compiler.getManageCodeGen().getRegisterManager().getFreeRegister();
            if (registerNumber == -1) {
                compiler.addInstruction(new PUSH(Register.getR(i)));
                toRestore.addFirst(i);
                register.setNumber(i);
                i++;
            } else {
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
