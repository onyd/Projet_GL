package fr.ensimag.deca.context;

import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

/**
 * Deca Type (internal representation of the compiler)
 *
 * @author gl28
 * @date 01/01/2022
 */

public abstract class Type {


    /**
     * True if this and otherType represent the same type (in the case of
     * classes, this means they represent the same class).
     */
    public abstract boolean sameType(Type otherType);

    private final Symbol name;

    public Type(Symbol name) {
        this.name = name;
    }

    public Symbol getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName().toString();
    }

    public boolean isClass() {
        return false;
    }

    public boolean isInt() {
        return false;
    }

    public boolean isFloat() {
        return false;
    }

    public boolean isBoolean() {
        return false;
    }

    public boolean isVoid() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public boolean isClassOrNull() {
        return false;
    }

    /**
     * Returns the same object, as type ClassType, if possible. Throws
     * ContextualError(errorMessage, l) otherwise.
     *
     * Can be seen as a cast, but throws an explicit contextual error when the
     * cast fails.
     */
    public ClassType asClassType(String errorMessage, Location l)
            throws ContextualError {
        throw new ContextualError(errorMessage, l);
    }

    public boolean isSubType(Type otherType) {
        if (this.isNull() && otherType.isClass())
            return true;
        if (this.isClass() && otherType.isClass()) {
            return ((ClassType) this).isSubClassOf((ClassType) otherType);
        } else {
            return this.sameType(otherType);
        }
    }

    public boolean isAssignCompatible(Type otherType) {
        if (this.isFloat() && otherType.isInt())
            return true;
        if (otherType.isSubType(this))
            return true;

        return false;
    }

    public boolean isCastCompatible(Type otherType) {
        return this.isAssignCompatible(otherType) || otherType.isAssignCompatible(this);
    }

}
