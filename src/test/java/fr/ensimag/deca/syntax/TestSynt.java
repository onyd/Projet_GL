package fr.ensimag.deca.syntax;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractProgram;
import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

public class TestSynt {
    @Test
    public void testCircularInclude() throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;

        System.out.println("Programme with a circular include");
        String arg = "src/test/deca/syntax/invalid/custom/circular_include.deca/";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));

        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        File file = null;
        if (lex.getSourceName() != null) {
            file = new File(lex.getSourceName());
        }
        final DecacCompiler decacCompiler = new DecacCompiler(new CompilerOptions(), file);
        parser.setDecacCompiler(decacCompiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        if (prog == null) {
            System.exit(1);
        } else {
            prog.prettyPrint(System.out);
        }
    }

    @Test
    public void testEmpty() throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;

        System.out.println("Empty Program:");
        String arg = "src/test/deca/syntax/valid/custom/empty.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));

        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        File file = null;
        if (lex.getSourceName() != null) {
            file = new File(lex.getSourceName());
        }
        final DecacCompiler decacCompiler = new DecacCompiler(new CompilerOptions(), file);
        parser.setDecacCompiler(decacCompiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        if (prog == null) {
            System.exit(1);
        } else {
            prog.prettyPrint(System.out);
        }
    }

    @Test
    public void testIf() throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;

        System.out.println("Programme with an if");
        String arg = "src/test/deca/syntax/valid/custom/if.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));

        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        File file = null;
        if (lex.getSourceName() != null) {
            file = new File(lex.getSourceName());
        }
        final DecacCompiler decacCompiler = new DecacCompiler(new CompilerOptions(), file);
        parser.setDecacCompiler(decacCompiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        if (prog == null) {
            System.exit(1);
        } else {
            prog.prettyPrint(System.out);
        }
    }

}
