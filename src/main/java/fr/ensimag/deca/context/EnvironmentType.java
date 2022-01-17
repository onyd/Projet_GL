package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;

import java.util.HashMap;

/**
 * Dictionary associating identifier's TypeDefinition to their names.
 *
 * Searching a definition (through method get)
 *
 * Insertion (through method declare)
 *
 * @author gl28
 * @date 01/01/2022
 **/
public class EnvironmentType extends Environment<TypeDefinition> {

    public EnvironmentType(Environment<TypeDefinition> parentEnvironment) {
        super(parentEnvironment);
    }
}
