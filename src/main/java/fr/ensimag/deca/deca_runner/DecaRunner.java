package fr.ensimag.deca.deca_runner;

import fr.ensimag.deca.context.Type;

import java.io.*;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class DecaRunner {
    private String fileDirectory;
    private File fileName;
    private DecaResults decaResults;

    public DecaRunner(String fileDirectory, File programFile) {
        this.fileDirectory = fileDirectory;
        this.fileName = programFile;
        this.decaResults = null;
    }

    public DecaResults getResult() throws NoResultException {
        if (decaResults != null) {
            return decaResults;
        } else {
            throw new NoResultException("The result doesn't exist, do not forget to use the function run()");
        }
    }

    public void run() throws IOException {
        try {
            String command = "java -cp " + fileDirectory + " " + fileName;
            Process pro = Runtime.getRuntime().exec(command);

            decaResults = new DecaResults();

            String line = null;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(pro.getInputStream()));

            while ((line = in.readLine()) != null) {
                String[] splited = line.split("\\s+");
                if (splited.length == 3) {
                    String type = splited[0];
                    String name = splited[1];
                    String value = splited[2];

                    if (type.equals("int")) {
                        DecaResult<Integer> currentResult = new DecaResult<>(parseInt(value));
                        decaResults.addResult(name, currentResult);
                    } else if (type.equals("float")) {
                        DecaResult<Float> currentResult = new DecaResult<>(parseFloat(value));
                        decaResults.addResult(name, currentResult);
                    } else if (type.equals("boolean")) {
                        DecaResult<Boolean> currentResult = new DecaResult<>(parseBoolean(value));
                        decaResults.addResult(name, currentResult);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
