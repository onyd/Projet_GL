package fr.ensimag.deca.syntax;

import fr.ensimag.deca.context.ContextualError;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

/**
 * Unit tests file for the lexer.
 *
 */


public class TestLex {

    /**
     * Description: test if the tokens are good for the file empty.deca
     *
     * Results : Ok
     */
    @Test
    public void testTokenEmpty() throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;
        System.out.println("Beginning of unit tests");
        System.out.println("Empty Program:");
        String arg = "src/test/deca/syntax/valid/custom/empty.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.nextToken();
        assertEquals(lex.OBRACE, lex.getType());
        lex.nextToken();
        assertEquals(lex.CBRACE,lex.getType());
    }

    /**
     * Description: test if the tokens are good for the file hello_world.deca
     *
     * Results : Ok
     */

    @Test
    public void testTokenHelloWorld() throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;

        System.out.println("\n Hello World Program:");
        String arg = "src/test/deca/syntax/valid/custom/hello_world.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.nextToken();
        assertEquals(lex.OBRACE, lex.getType());
        lex.nextToken();
        assertEquals(lex.PRINTLN,lex.getType());
        lex.nextToken();
        assertEquals(lex.OPARENT, lex.getType());
        lex.nextToken();
        assertEquals(lex.STRING, lex.getType());
        lex.nextToken();
        assertEquals(lex.CPARENT, lex.getType());
        lex.nextToken();
        assertEquals(lex.SEMI,lex.getType());
        lex.nextToken();
        assertEquals(lex.CBRACE, lex.getType());

    }

    /**
     * Description: test if an incomplete string can be considered as a string token
     *
     * Results : NOk
     * token recognition error
     *
     */

    @Test
    public void testIncompleteString() throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;

        System.out.println("Programme with a string");
        String arg = "src/test/deca/syntax/invalid/provided/chaine_incomplete.deca";
        try{
            lex = new DecaLexer(CharStreams.fromFileName(arg));
            lex.setSource(new File(arg));
            lex.nextToken();
            assertEquals(lex.STRING, lex.getType());
        } catch(Exception e){
            System.err.println("an expected error about the incomplete string has been raised");
        }
    }

    /**
     * Description: test if the text associated to each token is the right one in incr.deca
     *
     * Results : Ok
     */
    @Test
    public void testIncr() throws IOException {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;

        System.out.println("Programme with a class that increment an integer");
        String arg = "src/test/deca/syntax/valid/custom/incr.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.nextToken();
        assertEquals("class", lex.getText());
        lex.nextToken();
        assertEquals("Incr", lex.getText());
        lex.nextToken();
        assertEquals("{", lex.getText());
        lex.nextToken();
        assertEquals("int",lex.getText());
        lex.nextToken();
        assertEquals("i",lex.getText());
        lex.nextToken();
        assertEquals("=",lex.getText());
        lex.nextToken();
        assertEquals("0",lex.getText());
        lex.nextToken();
        assertEquals(";",lex.getText());
        lex.nextToken();
        assertEquals("void",lex.getText());
        lex.nextToken();
        assertEquals("incr",lex.getText());
        lex.nextToken();
        assertEquals("(",lex.getText());
        lex.nextToken();
        assertEquals(")",lex.getText());
        lex.nextToken();
        assertEquals("{",lex.getText());
        lex.nextToken();
        assertEquals("this",lex.getText());
        lex.nextToken();
        assertEquals(".",lex.getText());
        lex.nextToken();
        assertEquals("i",lex.getText());
        lex.nextToken();
        assertEquals("=",lex.getText());
        lex.nextToken();
        assertEquals("i",lex.getText());
        lex.nextToken();
        assertEquals("+",lex.getText());
        lex.nextToken();
        assertEquals("1",lex.getText());
        lex.nextToken();
        assertEquals(";",lex.getText());
        lex.nextToken();
        assertEquals("}",lex.getText());
        lex.nextToken();
        assertEquals("}",lex.getText());
        lex.nextToken();
        assertEquals("{",lex.getText());
        lex.nextToken();
        assertEquals("Incr",lex.getText());
        lex.nextToken();
        assertEquals("incr",lex.getText());
        lex.nextToken();
        assertEquals("=",lex.getText());
        lex.nextToken();
        assertEquals("new",lex.getText());
        lex.nextToken();
        assertEquals("Incr",lex.getText());
        lex.nextToken();
        assertEquals("(",lex.getText());
        lex.nextToken();
        assertEquals(")",lex.getText());
        lex.nextToken();
        assertEquals(";",lex.getText());
        lex.nextToken();
        assertEquals("incr",lex.getText());
        lex.nextToken();
        assertEquals(".",lex.getText());
        lex.nextToken();
        assertEquals("incr",lex.getText());
        lex.nextToken();
        assertEquals("(",lex.getText());
        lex.nextToken();
        assertEquals(")",lex.getText());
        lex.nextToken();
        assertEquals(";",lex.getText());
        lex.nextToken();
        assertEquals("}",lex.getText());
    }


    /**
     * Description: test if the text associated to each token is the right one in incr.deca
     *
     * Results : NOk
     *           Bad String in line 236
     */
    @Test
    public void testSum() throws IOException{
        Logger.getRootLogger().setLevel(Level.DEBUG);
        DecaLexer lex;

        System.out.println("Programme with a class that increment an integer");
        String arg = "src/test/deca/syntax/valid/custom/sum.deca";
        lex = new DecaLexer(CharStreams.fromFileName(arg));
        lex.setSource(new File(arg));
        lex.nextToken();
        assertEquals("{", lex.getText());
        lex.nextToken();
        assertEquals("int", lex.getText());
        lex.nextToken();
        assertEquals("i", lex.getText());
        lex.nextToken();
        assertEquals("=", lex.getText());
        lex.nextToken();
        assertEquals("0", lex.getText());
        lex.nextToken();
        assertEquals(";",lex.getText());
        lex.nextToken();
        assertEquals("i",lex.getText());
        lex.nextToken();
        assertEquals("=",lex.getText());
        lex.nextToken();
        assertEquals("2",lex.getText());
        lex.nextToken();
        assertEquals("+",lex.getText());
        lex.nextToken();
        assertEquals("343",lex.getText());
        lex.nextToken();
        assertEquals(";",lex.getText());
        lex.nextToken();
        assertEquals("println",lex.getText());
        lex.nextToken();
        assertEquals("(",lex.getText());
        lex.nextToken();
        assertEquals("i",lex.getText());
        lex.nextToken();
        assertEquals(")",lex.getText());
        lex.nextToken();
        assertEquals(";",lex.getText());
        lex.nextToken();
        assertEquals("}",lex.getText());
    }

}
