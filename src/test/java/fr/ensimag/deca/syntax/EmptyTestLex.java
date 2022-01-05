package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.CharStreams;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class EmptyTestLex {
    public static void main(String[] args) throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;

        System.out.println("Empty Program:");
        String arg = "src/test/deca/syntax/valid/custom/empty.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.debugTokenStream();
        System.out.println();
    }
}
