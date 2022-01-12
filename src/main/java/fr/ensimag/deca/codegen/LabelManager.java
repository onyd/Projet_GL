package fr.ensimag.deca.codegen;

import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.ima.pseudocode.Label;

import java.util.HashMap;
import java.util.Map;

public class LabelManager {
    private HashMap<String, Integer> labelsOccurrences = new HashMap<>();

    public Label getNextLabel(String labelName) {
        Integer occurrences = labelsOccurrences.get(labelName);
        if (occurrences != null) {
            labelsOccurrences.put(labelName, occurrences + 1);
            return new Label(labelName + "." + occurrences + 1);
        } else {
            labelsOccurrences.put(labelName, 0);
            return new Label(labelName + ".0");
        }
    }




}
