package fr.ensimag.deca;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl28
 * @date 01/01/2022
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    public static void main(String[] args) {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {
            System.out.println("G6 - GL28");
            System.out.println("Valentin Laclautre");
            System.out.println("Anthony Dard");
            System.out.println("Damien Trouche");
            System.out.println("Martin Gangand");
            System.out.println("Basel Darwish Jzaerly");
            System.exit(0);
        }
        if (options.getSourceFiles().isEmpty()) {
            showUsage();
        }
        if (options.getParallel()) {
            ArrayList<Thread> threads = new ArrayList<>();
            for(File source : options.getSourceFiles()) {
                Thread thread = new Thread(new ParallelCompile(source, options));
                thread.start();
                threads.add(thread);
            }
            for(Thread thread : threads) {
                try{
                    thread.join();
                } catch (InterruptedException e) {
                    System.exit(1);
                }
            }
        } else {
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }

    private static void showUsage() {
        System.out.println("Usage: decac [[-p | -v] [-n] [-r X] [-d]* [-P] [-w] <fichier deca>...] | [-b]");
    }
}
