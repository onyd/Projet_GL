package fr.ensimag.deca;

import fr.ensimag.deca.codegen.ManageCodeGen;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.syntax.DecaLexer;
import fr.ensimag.deca.syntax.DecaParser;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tree.AbstractProgram;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.deca.tree.LocationException;
import fr.ensimag.deca.tree.Program;
import fr.ensimag.ima.pseudocode.AbstractLine;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Instruction;
import fr.ensimag.ima.pseudocode.Label;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.log4j.Logger;

/**
 * Decac compiler instance.
 *
 * This class is to be instantiated once per source file to be compiled. It
 * contains the meta-data used for compiling (source file name, compilation
 * options) and the necessary utilities for compilation (symbol tables, abstract
 * representation of target file, ...).
 *
 * It contains several objects specialized for different tasks. Delegate methods
 * are used to simplify the code of the caller (e.g. call
 * compiler.addInstruction() instead of compiler.getProgram().addInstruction()).
 *
 * @author gl28
 * @date 01/01/2022
 */
public class DecacCompiler {
    private static final Logger LOG = Logger.getLogger(DecacCompiler.class);
    
    /**
     * Portable newline character.
     */
    private static final String nl = System.getProperty("line.separator", "\n");

    public DecacCompiler(CompilerOptions compilerOptions, File source) {
        super();
        this.compilerOptions = compilerOptions;
        this.source = source;

        // Initialization of env_types
        SymbolTable.Symbol voidType = symbolTable.create("void");
        SymbolTable.Symbol booleanType = symbolTable.create("boolean");
        SymbolTable.Symbol floatType = symbolTable.create("float");
        SymbolTable.Symbol intType = symbolTable.create("int");
        try {
            env_types.declare(voidType, new TypeDefinition(new VoidType(voidType), Location.BUILTIN));
            env_types.declare(booleanType, new TypeDefinition(new BooleanType(booleanType), Location.BUILTIN));
            env_types.declare(floatType, new TypeDefinition(new FloatType(floatType), Location.BUILTIN));
            env_types.declare(intType, new TypeDefinition(new IntType(intType), Location.BUILTIN));
        } catch (EnvironmentType.DoubleDefException e) {
            // Never happen
        }
        this.manageCodeGen = new ManageCodeGen(this, this.compilerOptions.getRegisterNumber());
    }

    /**
     * Source file associated with this compiler instance.
     */
    public File getSource() {
        return source;
    }

    /**
     * Compilation options (e.g. when to stop compilation, number of registers
     * to use, ...).
     */
    public CompilerOptions getCompilerOptions() {
        return compilerOptions;
    }

    /**
     * @see
     * fr.ensimag.ima.pseudocode.IMAProgram#add(fr.ensimag.ima.pseudocode.AbstractLine)
     */
    public void add(AbstractLine line) {
        program.add(line);
    }

    /**
     * @see fr.ensimag.ima.pseudocode.IMAProgram#addComment(java.lang.String)
     */
    public void addComment(String comment) {
        program.addComment(comment);
    }

    /**
     * @see
     * fr.ensimag.ima.pseudocode.IMAProgram#addLabel(fr.ensimag.ima.pseudocode.Label)
     */
    public void addLabel(Label label) {
        program.addLabel(label);
    }

    /**
     * @see
     * fr.ensimag.ima.pseudocode.IMAProgram#addInstruction(fr.ensimag.ima.pseudocode.Instruction)
     */
    public void addInstruction(Instruction instruction) {
        program.addInstruction(instruction);
    }

    /**
     * @see
     * fr.ensimag.ima.pseudocode.IMAProgram#addInstruction(fr.ensimag.ima.pseudocode.Instruction,
     * java.lang.String)
     */
    public void addInstruction(Instruction instruction, String comment) {
        program.addInstruction(instruction, comment);
    }

    /**
     * @see
     * fr.ensimag.ima.pseudocode.IMAProgram#append(fr.ensimag.ima.pseudocode.IMAProgram)
     */
    public void append(IMAProgram p) {
        program.append(p);
    }
    
    /**
     * @see 
     * fr.ensimag.ima.pseudocode.IMAProgram#display()
     */
    public String displayIMAProgram() {
        return program.display();
    }
    
    private final CompilerOptions compilerOptions;
    private final File source;
    private SymbolTable symbolTable = new SymbolTable();
    private EnvironmentType env_types = new EnvironmentType();
    private ManageCodeGen manageCodeGen;

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public EnvironmentType getEnvironmentType() {
        return env_types;
    }

    public ManageCodeGen getManageCodeGen() {
        return manageCodeGen;
    }

    /**
     * The main program. Every instruction generated will eventually end up here.
     */
    private final IMAProgram program = new IMAProgram();
 

    /**
     * Run the compiler (parse source file, generate code)
     *
     * @return true on error
     */
    public boolean compile() {
        String sourceFile = source.getAbsolutePath();
        String destFile = sourceFile;
        destFile = destFile.substring(0, destFile.length() - 4);
        destFile = destFile + "ass";
        PrintStream err = System.err;
        PrintStream out = System.out;
        LOG.debug("Compiling file " + sourceFile + " to assembly file " + destFile);
        try {
            return doCompile(sourceFile, destFile, out, err);
        } catch (LocationException e) {
            e.display(err);
            return true;
        } catch (DecacFatalError e) {
            err.println(e.getMessage());
            return true;
        } catch (StackOverflowError e) {
            LOG.debug("stack overflow", e);
            err.println("Stack overflow while compiling file " + sourceFile + ".");
            return true;
        } catch (Exception e) {
            LOG.fatal("Exception raised while compiling file " + sourceFile
                    + ":", e);
            err.println("Internal compiler error while compiling file " + sourceFile + ", sorry.");
            return true;
        } catch (AssertionError e) {
            LOG.fatal("Assertion failed while compiling file " + sourceFile
                    + ":", e);
            err.println("Internal compiler error while compiling file " + sourceFile + ", sorry.");
            return true;
        }
    }

    /**
     * Internal function that does the job of compiling (i.e. calling lexer,
     * verification and code generation).
     *
     * @param sourceName name of the source (deca) file
     * @param destName name of the destination (assembly) file
     * @param out stream to use for standard output (output of decac -p)
     * @param err stream to use to display compilation errors
     *
     * @return true on error
     */
    private boolean doCompile(String sourceName, String destName,
            PrintStream out, PrintStream err)
            throws DecacFatalError, LocationException {

        // STEP A
        AbstractProgram prog = doLexingAndParsing(sourceName, err);

        if (prog == null) {
            LOG.info("Parsing failed");
            return true;
        }
        assert(prog.checkAllLocations());

        if(this.compilerOptions.getParseFiles()) {
             System.out.println(prog.decompile());
             System.exit(0);
        }

        // STEP B
        prog.verifyProgram(this);
        if(this.compilerOptions.getVerifyFiles()) {
            System.exit(0);
        }
        assert(prog.checkAllDecorations());

        // STEP C
        addComment("start main program");
        prog.codeGenProgram(this);
        addComment("end main program");
        LOG.debug("Generated assembly code:" + nl + program.display());
        LOG.info("Output file assembly file is: " + destName);

        FileOutputStream fstream = null;
        try {
            fstream = new FileOutputStream(destName);
        } catch (FileNotFoundException e) {
            throw new DecacFatalError("Failed to open output file: " + e.getLocalizedMessage());
        }

        LOG.info("Writing assembler file ...");

        program.display(new PrintStream(fstream));
        LOG.info("Compilation of " + sourceName + " successful.");
        return false;
    }

    /**
     * Build and call the lexer and parser to build the primitive abstract
     * syntax tree.
     *
     * @param sourceName Name of the file to parse
     * @param err Stream to send error messages to
     * @return the abstract syntax tree
     * @throws DecacFatalError When an error prevented opening the source file
     * @throws DecacInternalError When an inconsistency was detected in the
     * compiler.
     * @throws LocationException When a compilation error (incorrect program)
     * occurs.
     */
    protected AbstractProgram doLexingAndParsing(String sourceName, PrintStream err)
            throws DecacFatalError, DecacInternalError {
        DecaLexer lex;
        try {
            lex = new DecaLexer(CharStreams.fromFileName(sourceName));
        } catch (IOException ex) {
            throw new DecacFatalError("Failed to open input file: " + ex.getLocalizedMessage());
        }
        lex.setDecacCompiler(this);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        parser.setDecacCompiler(this);

        // TODO get SymbolTable from parser
        return parser.parseProgramAndManageErrors(err);
    }
}
