package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;

import java.util.HashMap;
import java.util.Map;

public abstract class Environment<T extends Definition> {
    private HashMap<Symbol, T> defs = new HashMap<Symbol, T>();
    private Environment<T> parentEnvironment;

    public Environment(Environment<T> parentEnvironment) {
        this.parentEnvironment = parentEnvironment;
    }

    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public T get(Symbol key) {
        T def = defs.get(key);
        if (def == null){
            if (parentEnvironment != null){
                return this.parentEnvironment.get(key);
            }
            return null;
        }
        return def;
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
    public void declare(Symbol name, T def) throws DoubleDefException {
        if (this.get(name) != null) {
            throw new DoubleDefException();
        }
        this.defs.put(name, def);
    }

    public void stack(Environment<T> env) {
        env.parentEnvironment = this;
    }

    public boolean isEmpty() {
        return defs.isEmpty();
    }
}
