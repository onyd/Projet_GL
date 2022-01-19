package fr.ensimag.deca.tree;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.SymbolTable;

public class TestVirtualTable {
    public static AbstractProgram initTest1() {
        SymbolTable symb = new SymbolTable();
        // create classes
        ListDeclClass listDeclClass = new ListDeclClass();
        Identifier className = new Identifier(symb.create("A"));
        className.setDefinition(new ClassDefinition(new ClassType(symb.create("A"), new Location(0, 0, ""), null),
                new Location(0, 0, ""), null));
        Identifier superClassName = new Identifier(symb.create("Object"));
        DeclClass class1 = new DeclClass(className, superClassName);
        class1.addMethod(new DeclMethod(new Identifier(symb.create("int")), new Identifier(symb.create("getX")),
                new ListDeclParam(), new MethodBody(new ListDeclVar(), new ListInst())));
        listDeclClass.add(class1);

        ListDeclVar ldecl = new ListDeclVar();
        ListInst linst = new ListInst();
        AbstractProgram source =
                new Program(
                        listDeclClass,
                        new Main(ldecl, linst));
        Identifier ident = new Identifier(symb.create("x"));
        ident.setDefinition(new VariableDefinition(new FloatType(symb.create("float")), new Location(1, 1, "primary_expr_nopar.deca")));
        AbstractExpr expr = new FloatLiteral(12.5F);
        expr.setType(new FloatType(symb.create("float")));
        Initialization initialization = new Initialization(expr);
        ldecl.add(new DeclVar(new Identifier(symb.create("float")), ident, initialization));
        ListExpr listExpr = new ListExpr();
        listExpr.add(ident);
        linst.add(new Println(false, listExpr));
        linst.add(new Assign(ident, new ReadFloat()));
        return source;
    }

    public static AbstractProgram initTest2() {
        SymbolTable symb = new SymbolTable();
        // create classes
        ListDeclClass listDeclClass = new ListDeclClass();
        Identifier className = new Identifier(symb.create("A"));
        ClassDefinition classDefinition = new ClassDefinition(new ClassType(symb.create("A"), new Location(0, 0, ""), null),
                new Location(0, 0, ""), null);
        className.setDefinition(classDefinition);
        Identifier superClassName = new Identifier(symb.create("Object"));
        DeclClass class1 = new DeclClass(className, superClassName);
        class1.addMethod(new DeclMethod(new Identifier(symb.create("void")), new Identifier(symb.create("setX")),
                new ListDeclParam(), new MethodBody(new ListDeclVar(), new ListInst())));
        class1.addMethod(new DeclMethod(new Identifier(symb.create("int")), new Identifier(symb.create("getX")),
                new ListDeclParam(), new MethodBody(new ListDeclVar(), new ListInst())));
        listDeclClass.add(class1);

        //class B extend A
        Identifier class2Name = new Identifier(symb.create("B"));
        class2Name.setDefinition(new ClassDefinition(new ClassType(symb.create("B"), new Location(0, 0, ""), classDefinition),
                new Location(0, 0, ""), classDefinition));
        DeclClass class2 = new DeclClass(class2Name, className);
        class2.addMethod(new DeclMethod(new Identifier(symb.create("int")), new Identifier(symb.create("getX")),
                new ListDeclParam(), new MethodBody(new ListDeclVar(), new ListInst())));
        class2.addMethod(new DeclMethod(new Identifier(symb.create("void")), new Identifier(symb.create("setY")),
                new ListDeclParam(), new MethodBody(new ListDeclVar(), new ListInst())));
        listDeclClass.add(class2);

        ListDeclVar ldecl = new ListDeclVar();
        ListInst linst = new ListInst();
        AbstractProgram source =
                new Program(
                        listDeclClass,
                        new Main(ldecl, linst));
        Identifier ident = new Identifier(symb.create("x"));
        ident.setDefinition(new VariableDefinition(new FloatType(symb.create("float")), new Location(1, 1, "primary_expr_nopar.deca")));
        AbstractExpr expr = new FloatLiteral(12.5F);
        expr.setType(new FloatType(symb.create("float")));
        Initialization initialization = new Initialization(expr);
        ldecl.add(new DeclVar(new Identifier(symb.create("float")), ident, initialization));
        ListExpr listExpr = new ListExpr();
        listExpr.add(ident);
        linst.add(new Println(false, listExpr));
        linst.add(new Assign(ident, new ReadFloat()));
        return source;
    }

    public static String gencodeSource(AbstractProgram source) {
        DecacCompiler compiler = new DecacCompiler(new CompilerOptions(),null);
        source.codeGenProgram(compiler);
        return compiler.displayIMAProgram();
    }

    public static void test1() {
        AbstractProgram source = initTest1();
        System.out.println("---- From the following Abstract Syntax Tree ----");
        //source.prettyPrint(System.out);
        System.out.println("---- We generate the following assembly code ----");
        String result = gencodeSource(source);
        System.out.println(result);
    }

    public static void test2() {
        AbstractProgram source = initTest2();
        System.out.println("---- From the following Abstract Syntax Tree ----");
        //source.prettyPrint(System.out);
        System.out.println("---- We generate the following assembly code ----");
        String result = gencodeSource(source);
        System.out.println(result);
    }

    public static void main(String[] args) {
        test1();
        test2();
    }
}
