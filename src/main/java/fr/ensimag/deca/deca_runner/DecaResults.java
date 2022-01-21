package fr.ensimag.deca.deca_runner;

import java.util.HashMap;
import java.util.Map;

public class DecaResults {
    Map<String, DecaResult> results;

    public DecaResults() {
        this.results = new HashMap<String, DecaResult>();
    }

    public Map<String, DecaResult> getResults() {
        return results;
    }

    public void addResult(String name, DecaResult decaResult) {
        this.results.put(name, decaResult);
    }

    public DecaResult getValue(String name) throws NoResultException {
        DecaResult currentResult = this.results.get(name);
        if (currentResult != null) {
            return currentResult;
        } else {
            throw new NoResultException("The variable doesn't exist in the main program");
        }
    }

    @Override
    public String toString() {
        String ans = "";
        for (Map.Entry<String, DecaResult> entry : results.entrySet()) {
            ans += entry.getValue().getValue().getClass() + " " + entry.getKey() + " = " + entry.getValue().getValue() + "\n";
        }
        return ans;
    }
}