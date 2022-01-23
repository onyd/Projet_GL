package fr.ensimag.deca.tools;

import fr.ensimag.deca.deca_runner.DecaResults;
import fr.ensimag.deca.deca_runner.DecaRunner;
import fr.ensimag.deca.deca_runner.NoResultException;

import java.io.File;
import java.io.IOException;

public class TestDecaRunner {
    String path;
    String fileName;

    public TestDecaRunner(String path, String fileName) {
        this.fileName = fileName;
        this.path = path;
    }

    public static void main(String[] args) throws IOException, NoResultException {
        TestDecaRunner test = new TestDecaRunner("src/test/deca/codegen/valid/demo/class/", "for_deca_runner");
        File file = new File(test.fileName);
        DecaRunner decaProgram = new DecaRunner(test.path, file);
        System.out.println(file);
        decaProgram.run();

        DecaResults res = decaProgram.getResult();
        System.out.println(res.getResults());
    }


}
