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
        DecacCompiler compiler;
        if (options.getJavaCompilation())
            compiler =new JavaCompiler(options, source);
        else
            compiler = new IMACompiler(options, source);

        boolean error = compiler.compile();
        if(error) {
            System.exit(1);
        }
    }
}
