package fr.ensimag.deca.syntax;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.tree.AbstractProgram;
import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class IncludeFileNotFoundTestSynt {
    public static void main(String[] args) throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;

        System.out.println("Programme where the file to include doesn't exist");
        String arg = "src/test/deca/syntax/invalid/custom/invalid_include.deca/";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));

        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        File file = null;
        if (lex.getSourceName() != null) {
            file = new File(lex.getSourceName());
        }
        final IMACompiler decacCompiler = new IMACompiler(new CompilerOptions(), file);
        parser.setDecacCompiler(decacCompiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        if (prog == null) {
            System.exit(1);
        } else {
            prog.prettyPrint(System.out);
        }
    }
}
