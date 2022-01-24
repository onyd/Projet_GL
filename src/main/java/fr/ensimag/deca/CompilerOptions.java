package fr.ensimag.deca;

import java.io.File;
import java.util.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl28
 * @date 01/01/2022
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    public boolean getParseFiles() {
        return parseFiles;
    }

    public boolean getVerifyFiles() {
        return verifyFiles;
    }

    public boolean getNoCheck() {
        return noCheck;
    }

    public int getRegisterNumber() {
        return registerNumber;
    }

    public boolean getRegisterLimit() {
        return registerLimit;
    }

    public boolean getJavaCompilation(){ return javaCompilation;}

    private int debug = 0;
    private boolean parallel = false;
    private boolean printBanner = false;
    private List<File> sourceFiles = new ArrayList<File>();

    private boolean parseFiles = false;
    private boolean verifyFiles = false;
    private boolean noCheck = false;
    private int registerNumber = 16;
    private boolean registerLimit = false;

    private boolean javaCompilation = false;

    public void setJavaCompilation(boolean javaCompilation) {
        this.javaCompilation = javaCompilation;
    }

    public void parseArgs(String[] args) throws CLIException {
        // Parse options
        treatOption(args);

        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }
    }

    private void treatOption(String[] args) {
        if(args.length == 0) {
            return;
        }
        int index = 0;
        while(index < args.length) {
            String arg = args[index];
            if(Objects.equals(arg, "-b")) {
                if(args.length > 1) {
                    throw new UnsupportedOperationException("-b can just be use alone");
                }
                this.printBanner = true;
                break;
            }
            if(Objects.equals(arg,"-d")) {
                while(Objects.equals(arg, "-d")) {
                    if(this.debug < 3) {
                        this.debug++;
                    }
                    index++;
                    if(index == args.length) {
                        break;
                    }
                    arg = args[index];
                }
                continue;
            }
            switch (arg) {
                case "-p":
                    if (this.verifyFiles) {
                        throw new UnsupportedOperationException("You can't make the options -v and -p at the same time");
                    } else if (this.javaCompilation) {
                        throw new UnsupportedOperationException("You can't use -p with -java");
                    } else {
                        this.parseFiles = true;
                    }
                    break;
                case "-v":
                    if (this.parseFiles) {
                        throw new UnsupportedOperationException("You can't make the options -v and -p at the same time");
                    } else if (this.javaCompilation) {
                        throw new UnsupportedOperationException("You can't use -v with -java");
                    } else {
                        this.verifyFiles = true;
                    }
                    break;
                case "-n":
                    if (this.javaCompilation) {
                        throw new UnsupportedOperationException("You can't use -p with -java");
                    }
                    this.noCheck = true;
                    break;
                case "-java":
                    if (this.parseFiles || this.verifyFiles || this.noCheck || this.registerLimit)
                        throw new UnsupportedOperationException(usage());
                    this.javaCompilation = true;
                    break;
                case "-r":
                    if (this.registerLimit) {
                        throw new UnsupportedOperationException("option -r already typed");
                    } else if (this.javaCompilation) {
                        throw new UnsupportedOperationException("You can't use -r with -java");
                    }
                    this.registerLimit = true;
                    try {
                        index++;
                        arg = args[index];
                        int num = Integer.parseInt(arg);
                        if(num < 4 || num > 16) {
                            throw new UnsupportedOperationException("You have to use a number between 4 and 16 after -r");
                        } else {
                            this.registerNumber = num;
                        }
                    } catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        throw new UnsupportedOperationException("You have to use a number between 4 and 16 after -r");
                    }
                    break;
                case "-P":
                    this.parallel = true;
                    break;
                default:
                    File file = new File(arg);
                    if(!file.exists() || !arg.contains(".deca")) {
                        throw new UnsupportedOperationException("Unknown Flags or Incorrect File");
                    }
                    sourceFiles.add(file);
            }
            index++;
        }
    }

    protected String usage() {
        return "Usage: \n decac [[-p | -v] [-n] [-r X] [-d]* [-P] <fichier deca>...] | [-b] " +
                "\n decac -java [[-d]* [-P] <fichier deca>...] | [-b]";
    }

    protected void displayUsage() {
        System.out.println(usage());
    }

    @Override
    public String toString() {
        return "CompilerOptions{" +
                "debug=" + debug +
                ", parallel=" + parallel +
                ", printBanner=" + printBanner +
                ", sourceFiles=" + sourceFiles +
                ", parseFiles=" + parseFiles +
                ", verifyFiles=" + verifyFiles +
                ", noCheck=" + noCheck +
                ", registerNumber=" + registerNumber +
                ", registerLimit=" + registerLimit +
                '}';
    }
}
