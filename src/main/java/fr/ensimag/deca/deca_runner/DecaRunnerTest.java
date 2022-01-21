package fr.ensimag.deca.deca_runner;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DecaRunnerTest {
    public static void main(String[] args) throws IOException, NoResultException {
        DecaRunner decaRunner = new DecaRunner("src/main/java/fr/ensimag/deca/deca_runner", new File("TestJava"));
        decaRunner.run();
        DecaResults decaResults = decaRunner.getResult();
        System.out.println(decaResults.getValue("x").toString());
        //System.out.println(decaResults.toString());
        //System.out.println(decaResults.getValue("x").toString());
    }
}
