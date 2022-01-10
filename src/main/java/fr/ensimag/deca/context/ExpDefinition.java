package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;

/**
 * Definition associated to identifier in expressions.
 *
 * @author gl28
 * @date 01/01/2022
 */
public abstract class ExpDefinition extends Definition {

    public void setOperand(DAddr operand) {
        this.operand = operand;
    }

    public DAddr getOperand() {
        return operand;
    }
    private DAddr operand;

    private int sizeOnStack = 1;

    public void setSizeOnStack(int sizeOnStack) {
        this.sizeOnStack = sizeOnStack;
    }

    public int getSizeOnStack() {
        return sizeOnStack;
    }

    public ExpDefinition(Type type, Location location) {
        super(type, location);
    }

}
