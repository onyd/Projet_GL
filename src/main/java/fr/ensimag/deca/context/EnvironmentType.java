package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;

import java.util.HashMap;

/**
 * Dictionary associating identifier's TypeDefinition to their names.
 *
 * This is actually a linked list of dictionaries: each EnvironmentType has a
 * pointer to a parentEnvironment, corresponding to superblock (eg superclass).
 *
 * The dictionary at the head of this list thus corresponds to the "current"
 * block (eg class).
 *
 * Searching a definition (through method get) is done in the "current"
 * dictionary and in the parentEnvironment if it fails.
 *
 * Insertion (through method declare) is always done in the "current" dictionary.
 *
 * @author gl28
 * @date 01/01/2022
 **/
public class EnvironmentType {
    HashMap<String, Type> currentExp = new HashMap<String, Type>();

    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public Type get(SymbolTable.Symbol key) {
        return currentExp.get(key.getName());
    }

    /**
     * Add the definition def associated to the symbol name in the environment.
     *
     * Adding a symbol which is already defined in the environment,
     * - throws DoubleDefException if the symbol is in the "current" dictionary
     * - or, hides the previous declaration otherwise.
     *
     * @param name
     *            Name of the symbol to define
     * @param def
     *            Definition of the symbol
     * @throws DoubleDefException
     *             if the symbol is already defined at the "current" dictionary
     *
     */
    public void declare(SymbolTable.Symbol name, Type def) throws DoubleDefException {
        if (this.currentExp.containsKey(name.getName())){
            throw new DoubleDefException();
        }
        this.currentExp.put(name.getName(), def);

    }
}
