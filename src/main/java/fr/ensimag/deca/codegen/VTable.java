package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VTable {
    private HashMap<String, List<Label>> VTables;
    private List<Label> currentLabelList;
    private DecacCompiler compiler;

    public VTable(DecacCompiler compiler) {
        this.compiler = compiler;
        this.VTables = new HashMap<>();
    }

    /**
     * create the method table for Object
     */
    public void VTableForObject() {
        compiler.addComment("For Oject:");
        this.currentLabelList = new ArrayList<>();
        compiler.getStack().declareImmediateOnStack(new NullOperand());
        Label equals = compiler.getLabelManager().getMethodLabel("Object", "equals");
        compiler.getStack().declareLabelOnStack(equals);
        currentLabelList.add(equals);
        this.VTables.put("Object", currentLabelList);
    }
}
