package fr.ensimag.deca;

import java.io.File;

public class ParallelCompile implements Runnable {
    private File source;
    private CompilerOptions options;

    public ParallelCompile(File file, CompilerOptions options) {
        this.source = file;
        this.options = options;
    }

    @Override
    public void run() {
        DecacCompiler compiler = new DecacCompiler(options, source);
        boolean error = compiler.compile();
        if(error) {
            System.exit(1);
        }
    }
}
