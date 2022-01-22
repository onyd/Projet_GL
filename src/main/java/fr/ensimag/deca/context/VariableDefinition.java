package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;

/**
 * Definition of a variable.
 *
 * @author gl28
 * @date 01/01/2022
 */
public class VariableDefinition extends ExpDefinition {
    public VariableDefinition(Type type, Location location) {
        super(type, location);
    }
    private boolean isConstant = true;

    @Override
    public String getNature() {
        return "variable";
    }

    @Override
    public boolean isExpression() {
        return true;
    }

    public boolean isConstant() { return isConstant; }

    public void setConstant(boolean isConstant) {
        this.isConstant = isConstant;
    }
}
