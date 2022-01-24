package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;

public abstract class LValueDefinition extends ExpDefinition {
    public LValueDefinition(Type type, Location location) {
        super(type, location);
    }

    private boolean isConstant = true;

    public boolean isConstant() { return isConstant; }

    public void setConstant(boolean isConstant) {
        this.isConstant = isConstant;
    }

}
