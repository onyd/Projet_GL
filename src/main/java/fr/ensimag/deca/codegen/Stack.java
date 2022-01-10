package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.*;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import javax.xml.parsers.SAXParser;

public class Stack {
    private int currentStackPosition = 1;
    private int nbOfDecl = 0;
    private DecacCompiler decacCompiler;

    public Stack(DecacCompiler decacCompiler) {
        this.decacCompiler = decacCompiler;
    }

    public int getNbOfDecl() {
        return nbOfDecl;
    }

    public void setNbOfDecl(int nbOfDecl) {
        this.nbOfDecl = nbOfDecl;
    }

    public void setVariableOnStack(Identifier identifier, AbstractInitialization initialization) {
        if(initialization.noInitialization()) {
            this.decacCompiler.addComment("Push a no initialized declared variable on the stack");
            this.decacCompiler.addInstruction(new LOAD(Utils.ImmediateFromType(identifier.getType()), Register.R1));
            DAddr dAddr= new RegisterOffset(this.currentStackPosition, Register.GB);
            identifier.getVariableDefinition().setOperand(dAddr);
            this.currentStackPosition++;
            this.decacCompiler.addInstruction(new STORE(Register.R1, dAddr));
        } else {
            Initialization init = (Initialization) initialization;
            if (init.getExpression().getType().isInt()) {
                this.decacCompiler.addInstruction(new LOAD(new ImmediateInteger(((IntLiteral) init.getExpression()).getValue()), Register.R1));
            } else if (init.getExpression().getType().isFloat()) {
                this.decacCompiler.addInstruction(new LOAD(new ImmediateFloat(((FloatLiteral) init.getExpression()).getValue()), Register.R1));
            }
            DAddr dAddr = new RegisterOffset(this.currentStackPosition, Register.GB);
            identifier.getVariableDefinition().setOperand(dAddr);
            this.currentStackPosition++;
            this.decacCompiler.addInstruction(new STORE(Register.R1, dAddr));
        }
    }
}
