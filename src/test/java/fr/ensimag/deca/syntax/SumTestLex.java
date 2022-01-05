package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.CharStreams;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class SumTestLex {
    public static void main(String[] args) throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;

        System.out.println("Programme that make a sum:");
        String arg = "src/test/deca/syntax/valid/custom/sum.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        System.exit(lex.debugTokenStream() ? 1 : 0);
    }
}
