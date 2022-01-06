package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.CharStreams;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class AutomaticTestLex {
    public static void main(String[] args) throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;

        System.out.println("Empty Program:");
        String arg = "src/test/deca/syntax/valid/custom/empty.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.debugTokenStream();
        System.out.println();

        System.out.println("\nHello_World program:");
        arg = "src/test/deca/syntax/valid/provided/hello.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.debugTokenStream();
        System.out.println();

        System.out.println("Programme that make a sum:");
        arg = "src/test/deca/syntax/valid/custom/sum.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.debugTokenStream();
        System.out.println();

        System.out.println("Programme with a class that increment an integer");
        arg = "src/test/deca/syntax/valid/custom/incr.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.debugTokenStream();
        System.out.println();

        System.out.println("Programme with an if");
        arg = "src/test/deca/syntax/valid/custom/if.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.debugTokenStream();
        System.out.println();

        System.out.println("Programme with a while");
        arg = "src/test/deca/syntax/valid/custom/while.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.debugTokenStream();
        System.out.println();

        System.out.println("Programme with a string");
        arg = "src/test/deca/syntax/valid/custom/string.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.debugTokenStream();
        System.exit(0);
    }
}
