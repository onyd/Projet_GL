package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.SymbolTable;

public class TestDeclVar {
    public static AbstractProgram initTest1() {
        ListDeclVar ldecl = new ListDeclVar();
        AbstractProgram source =
                new Program(
                        new ListDeclClass(),
                        new Main(ldecl,new ListInst()));
        SymbolTable symb = new SymbolTable();
        Identifier ident = new Identifier(symb.create("x"));
        ident.setDefinition(new VariableDefinition(new FloatType(symb.create("float")), new Location(1, 1, "test.deca")));
        AbstractExpr expr = new FloatLiteral(12.5F);
        expr.setType(new FloatType(symb.create("float")));
        Initialization initialization = new Initialization(expr);
        ldecl.add(new DeclVar(new Identifier(symb.create("float")), ident, initialization));
        return source;
    }

    public static AbstractProgram initTest2() {
        ListDeclVar ldecl = new ListDeclVar();
        AbstractProgram source =
                new Program(
                        new ListDeclClass(),
                        new Main(ldecl,new ListInst()));
        SymbolTable symb = new SymbolTable();
        Identifier ident = new Identifier(symb.create("x"));
        ident.setDefinition(new VariableDefinition(new StringType(symb.create("string")), new Location(1, 1, "test.deca")));
        AbstractExpr expr = new StringLiteral("toto");
        expr.setType(new StringType(symb.create("string")));
        Initialization initialization = new Initialization(expr);
        ldecl.add(new DeclVar(new Identifier(symb.create("string")), ident, initialization));
        return source;
    }

    public static AbstractProgram initTest3() {
        ListDeclVar ldecl = new ListDeclVar();
        AbstractProgram source =
                new Program(
                        new ListDeclClass(),
                        new Main(ldecl,new ListInst()));
        SymbolTable symb = new SymbolTable();
        Identifier ident = new Identifier(symb.create("x"));
        ident.setDefinition(new VariableDefinition(new BooleanType(symb.create("boolean")), new Location(1, 1, "test.deca")));
        AbstractExpr expr = new BooleanLiteral(true);
        expr.setType(new BooleanType(symb.create("boolean")));
        Initialization initialization = new Initialization(expr);
        ldecl.add(new DeclVar(new Identifier(symb.create("boolean")), ident, initialization));
        return source;
    }

    public static String gencodeSource(AbstractProgram source) {
        DecacCompiler compiler = new DecacCompiler(null,null);
        source.codeGenProgram(compiler);
        return compiler.displayIMAProgram();
    }

    public static void test1() {
        AbstractProgram source = initTest1();
        System.out.println("---- From the following Abstract Syntax Tree ----");
        source.prettyPrint(System.out);
        System.out.println("---- We generate the following assembly code ----");
        String result = gencodeSource(source);
        System.out.println(result);
    }

    public static void test2() {
        AbstractProgram source = initTest2();
        System.out.println("---- From the following Abstract Syntax Tree ----");
        source.prettyPrint(System.out);
        System.out.println("---- We generate the following assembly code ----");
        String result = gencodeSource(source);
        System.out.println(result);
    }

    public static void test3() {
        AbstractProgram source = initTest3();
        System.out.println("---- From the following Abstract Syntax Tree ----");
        source.prettyPrint(System.out);
        System.out.println("---- We generate the following assembly code ----");
        String result = gencodeSource(source);
        System.out.println(result);
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }
}
