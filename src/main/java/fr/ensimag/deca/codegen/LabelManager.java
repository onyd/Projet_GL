package fr.ensimag.deca.codegen;

import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.ima.pseudocode.Label;

import java.util.HashMap;
import java.util.Map;

public class LabelManager {
    private HashMap<String, Integer> labelsOccurrences = new HashMap<>();
    private boolean inIfElse = false;

    public static final Label STACK_OVERFLOW_ERROR = new Label("stack_overflow_error"),
            OVERFLOW_ERROR = new Label("overflow_error"),
            IO_ERROR = new Label("io_error");

    /**
     * tells if we are already in an if else condition (used by an else)
     * @return
     */
    public boolean isInIfElse() {
        return inIfElse;
    }

    public void setInIfElse(boolean inIfElse) {
        this.inIfElse = inIfElse;
    }

    /**
     * return the label with the labelName
     * @param labelName
     * @param newLabel if true, return a new label (occurrences + 1), else return the current label (occurrences)
     * @return
     */
    public Label getNextLabel(String labelName, boolean newLabel) {
        Integer occurrences = labelsOccurrences.get(labelName);
        if (occurrences != null) {
            if(newLabel) {
                labelsOccurrences.put(labelName, (occurrences + 1));
                return new Label(labelName + "." + (occurrences + 1));
            } else {
                return new Label(labelName + "." + occurrences);
            }
        } else {
            labelsOccurrences.put(labelName, 0);
            return new Label(labelName + ".0");
        }
    }

    public Label getNextLabel(String labelName) {
        return getNextLabel(labelName, true);
    }

    public Label getNextLabel(String labelName, String prefix) {
        return getNextLabel(prefix + "_" + labelName);
    }

    public Label getNextLabel(String labelName, String prefix, String className) {
        return getNextLabel(prefix + "_" + labelName + "." + className);
    }
}
