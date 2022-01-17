package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.*;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

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

    public void loadVariableFromExpr(AbstractExpr expr) {
        expr.codeGenExprOnR1(this.decacCompiler);
    }

    public void loadVariableOnR1FromIdentAndInit(Identifier identifier, AbstractInitialization initialization) {
        if(initialization.noInitialization()) {
            this.decacCompiler.addComment("Push a not initialized declared variable on the stack");
            this.decacCompiler.addInstruction(new LOAD(Utils.ImmediateFromType(identifier.getDefinition().getType()), Register.R1));
        } else {
            Initialization init = (Initialization) initialization;
            if(init.getExpression().getType().isString()) {
                setStringOnStack(identifier, init);
            } else {
                this.loadVariableFromExpr(init.getExpression());
            }
        }
    }

    public void declareVariableOnAddressStoreOnStack(Identifier identifier,
                                                     AbstractInitialization initialization,
                                                     RegisterOffset registerOffset) {
        loadVariableOnR1FromIdentAndInit(identifier, initialization);
        decacCompiler.addInstruction(new LOAD(registerOffset, Register.R0));
        decacCompiler.addInstruction(new STORE(Register.R1,
                new RegisterOffset(identifier.getFieldDefinition().getIndex(), Register.R0)));
    }

    public void declareVariableOnStack(Identifier identifier, AbstractInitialization initialization) {
        loadVariableOnR1FromIdentAndInit(identifier, initialization);
        DAddr dAddr= new RegisterOffset(this.currentStackPosition, Register.GB);
        identifier.getVariableDefinition().setOperand(dAddr);
        this.currentStackPosition++;
        this.decacCompiler.addInstruction(new ADDSP(1));
        this.decacCompiler.addInstruction(new STORE(Register.R1, dAddr));
    }

    public DAddr declareImmediateOnStackAndReturnDAddr(DVal dVal) {
        decacCompiler.addInstruction(new LOAD(dVal, Register.R1));
        DAddr dAddr= new RegisterOffset(this.currentStackPosition, Register.GB);
        this.currentStackPosition++;
        this.decacCompiler.addInstruction(new ADDSP(1));
        this.decacCompiler.addInstruction(new STORE(Register.R1, dAddr));
        return dAddr;
    }

    public DAddr declareLabelOnStackAndReturnDAddr(Label label) {
        decacCompiler.addInstruction(new LOAD(new LabelOperand(label), Register.R1));
        DAddr dAddr= new RegisterOffset(this.currentStackPosition, Register.GB);
        this.currentStackPosition++;
        this.decacCompiler.addInstruction(new ADDSP(1));
        this.decacCompiler.addInstruction(new STORE(Register.R1, dAddr));
        return dAddr;
    }

    public void setVariableOnStack(Identifier identifier, Register register) {
        this.decacCompiler.addInstruction(new STORE(register, identifier.getExpDefinition().getOperand()));
    }

    public void getVariableFromStackOnR1(Identifier identifier) {
        RegisterOffset addr = (RegisterOffset) identifier.getVariableDefinition().getOperand();
        this.decacCompiler.addInstruction(new LOAD(addr, Register.R1));
    }

    public void getVariableFromStackOnR1(Identifier identifier, int position) {
        if(identifier.getVariableDefinition().getType().isString()) {
            this.decacCompiler.addInstruction(new LOAD(new RegisterOffset(position, Register.GB), Register.R1));
        }
    }

    public void setStringOnStack(Identifier identifier, Initialization init) {
        String text = ((StringLiteral) init.getExpression()).getValue();
        DAddr dAddr;
        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            this.decacCompiler.addInstruction(new LOAD(new ImmediateInteger((int) c), Register.R0));
            dAddr = new RegisterOffset(this.currentStackPosition, Register.GB);
            if(i == 0) {
                identifier.getVariableDefinition().setOperand(dAddr);
                identifier.getVariableDefinition().setSizeOnStack(text.length());
            }
            this.currentStackPosition++;
            this.decacCompiler.addInstruction(new ADDSP(1));
            this.decacCompiler.addInstruction(new STORE(Register.R0, dAddr));
        }
    }
}
