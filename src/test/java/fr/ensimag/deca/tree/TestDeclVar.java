package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.FloatType;
import fr.ensimag.deca.context.IntType;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.SymbolTable;

public class TestDeclVar {
    public static AbstractProgram initTest1() {
        ListDeclVar ldecl = new ListDeclVar();
        AbstractProgram source =
                new Program(
                        new ListDeclClass(),
                        new Main(ldecl,new ListInst()));
        ListExpr lexp1 = new ListExpr(), lexp2 = new ListExpr();
        SymbolTable symb = new SymbolTable();
        Identifier ident = new Identifier(symb.create("x"));
        ident.setDefinition(new VariableDefinition(new IntType(symb.create("int")), new Location(1, 1, "test.deca")));
        AbstractExpr expr = new FloatLiteral(12.5F);
        expr.setType(new FloatType(symb.create("float")));
        Initialization initialization = new Initialization(expr);
        ldecl.add(new DeclVar(new Identifier(symb.create("int")), ident, initialization));
        lexp1.add(new StringLiteral("Hello "));
        lexp2.add(new StringLiteral("everybody !"));
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



    public static void main(String[] args) {
        test1();
    }
}
