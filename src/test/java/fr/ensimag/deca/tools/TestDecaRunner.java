package fr.ensimag.deca.tools;

import fr.ensimag.deca.deca_runner.DecaResults;
import fr.ensimag.deca.deca_runner.DecaRunner;
import fr.ensimag.deca.deca_runner.NoResultException;

import java.io.File;
import java.io.IOException;

public class TestDecaRunner {
    public static void main(String[] args) throws IOException, NoResultException {
        String path = "src/test/deca/codegen/valid/demo/clazz/";
        String fileName = "for_runner";
        DecaRunner decaProgram = new DecaRunner(path, fileName);
        decaProgram.run();

        DecaResults res = decaProgram.getResults();
        System.out.println(res.getValue("x"));

    }


}
