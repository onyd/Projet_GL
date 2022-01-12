package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;

public class ManageCodeGen {
    private Stack stack;
    private RegisterManager registerManager;
    private LabelManager labelManager;
    private DecacCompiler decacCompiler;

    public ManageCodeGen(DecacCompiler decacCompiler) {
        this.decacCompiler = decacCompiler;
        this.stack = new Stack(decacCompiler);
        this.registerManager = new RegisterManager(16);
        this.labelManager = new LabelManager();
    }

    public Stack getStack() {
        return stack;
    }

    public RegisterManager getRegisterManager() {
        return registerManager;
    }
        public LabelManager getLabelManager() {
        return labelManager;
    }
}
