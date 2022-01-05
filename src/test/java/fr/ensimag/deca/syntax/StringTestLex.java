package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.CharStreams;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class StringTestLex {
    public static void main(String[] args) throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;

        System.out.println("Programme with a string");
        String arg = "src/test/deca/syntax/valid/custom/string.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.debugTokenStream();
    }
}
