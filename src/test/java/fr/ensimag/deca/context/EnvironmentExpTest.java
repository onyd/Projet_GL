package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tree.Location;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentExpTest
{
    @Test
    void declareAndGetWithoutParents() throws EnvironmentExp.DoubleDefException
    {
        SymbolTable symbolTable;
        EnvironmentExp environmentExp;
        ExpDefinition expDefinition;

        symbolTable = new SymbolTable();
        environmentExp = new EnvironmentExp(null);
        assertTrue(environmentExp.currentExp.isEmpty());

        // tester int x;
        expDefinition = new VariableDefinition(new IntType(symbolTable.create("x")), new Location(0, 0, "test"));
        environmentExp.declare(symbolTable.create("x"), expDefinition);
        assertEquals(expDefinition,environmentExp.get(symbolTable.create("x")));

        // tester int y;
        expDefinition = new VariableDefinition(new IntType(symbolTable.create("y")), new Location(0, 0, "test"));
        environmentExp.declare(symbolTable.create("y"), expDefinition);
        assertEquals(expDefinition,environmentExp.get(symbolTable.create("y")));

        // tester string mot1;
        expDefinition = new VariableDefinition(new IntType(symbolTable.create("mot1")), new Location(0, 0, "test"));
        environmentExp.declare(symbolTable.create("mot1"), expDefinition);
        assertEquals(expDefinition,environmentExp.get(symbolTable.create("mot1")));

        // tester string mot2;
        expDefinition = new VariableDefinition(new IntType(symbolTable.create("mot2")), new Location(0, 0, "test"));
        environmentExp.declare(symbolTable.create("mot2"), expDefinition);
        assertEquals(expDefinition,environmentExp.get(symbolTable.create("mot2")));
    }

    @Test
    void declareAndGetWithOneParent() throws EnvironmentExp.DoubleDefException
    {
        SymbolTable symbolTableParent;
        EnvironmentExp environmentExpParent;
        ExpDefinition expDefinitionParent;

        symbolTableParent = new SymbolTable();
        environmentExpParent = new EnvironmentExp(null);
        assertTrue(environmentExpParent.currentExp.isEmpty());

        //Partie similaire à la méthode précédente (Environment sans parents).

        // tester int x;
        expDefinitionParent = new VariableDefinition(new IntType(symbolTableParent.create("x")), new Location(0, 0, "test"));
        environmentExpParent.declare(symbolTableParent.create("x"), expDefinitionParent);
        assertEquals(expDefinitionParent,environmentExpParent.get(symbolTableParent.create("x")));

        // tester int y;
        expDefinitionParent = new VariableDefinition(new IntType(symbolTableParent.create("y")), new Location(0, 0, "test"));
        environmentExpParent.declare(symbolTableParent.create("y"), expDefinitionParent);
        assertEquals(expDefinitionParent,environmentExpParent.get(symbolTableParent.create("y")));

        // tester string mot1;
        expDefinitionParent = new VariableDefinition(new IntType(symbolTableParent.create("mot1")), new Location(0, 0, "test"));
        environmentExpParent.declare(symbolTableParent.create("mot1"), expDefinitionParent);
        assertEquals(expDefinitionParent,environmentExpParent.get(symbolTableParent.create("mot1")));

        // tester string mot2;
        expDefinitionParent = new VariableDefinition(new IntType(symbolTableParent.create("mot2")), new Location(0, 0, "test"));
        environmentExpParent.declare(symbolTableParent.create("mot2"), expDefinitionParent);
        assertEquals(expDefinitionParent,environmentExpParent.get(symbolTableParent.create("mot2")));

        //Partie spécifique à cette méthode qui est la création/empilement de environment fils.

        SymbolTable symbolTableFils;
        EnvironmentExp environmentExpFils;
        ExpDefinition expDefinitionFils;

        symbolTableFils = new SymbolTable();
        environmentExpFils = new EnvironmentExp(environmentExpParent);
        assertTrue(environmentExpFils.currentExp.isEmpty());

        // tester int x;
        expDefinitionFils = new VariableDefinition(new IntType(symbolTableParent.create("x")), new Location(0, 0, "test"));
        environmentExpFils.declare(symbolTableFils.create("x"), expDefinitionFils);

        assertEquals(expDefinitionFils,environmentExpFils.get(symbolTableFils.create("x")));//x est dans l'environement fils.
        assertNotEquals(expDefinitionParent,environmentExpFils.get(symbolTableFils.create("x")));//mais ce x est different de celle qui est dans l'environement parent.

    }
}