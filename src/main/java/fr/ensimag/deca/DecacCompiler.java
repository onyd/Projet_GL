package fr.ensimag.deca;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.syntax.DecaLexer;
import fr.ensimag.deca.syntax.DecaParser;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tree.AbstractProgram;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.deca.tree.LocationException;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

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
public abstract class DecacCompiler {
    protected static final Logger LOG = Logger.getLogger(DecacCompiler.class);
    
    public DecacCompiler(CompilerOptions compilerOptions, File source) {
        this.compilerOptions = compilerOptions;
        this.source = source;

        // Initialization of env_types
        try {
            envTypes.declare(VOID_SYMBOL, new TypeDefinition(new VoidType(VOID_SYMBOL), Location.BUILTIN));
            envTypes.declare(BOOLEAN_SYMBOL, new TypeDefinition(new BooleanType(BOOLEAN_SYMBOL), Location.BUILTIN));
            envTypes.declare(FLOAT_SYMBOL, new TypeDefinition(new FloatType(FLOAT_SYMBOL), Location.BUILTIN));
            envTypes.declare(INT_SYMBOL, new TypeDefinition(new IntType(INT_SYMBOL), Location.BUILTIN));

            ClassType objectType = new ClassType(OBJECT_SYMBOL, Location.BUILTIN, null);
            envTypes.declare(OBJECT_SYMBOL, objectType.getDefinition());

            Signature signature = new Signature();
            signature.add(getEnvironmentType().get(OBJECT_SYMBOL).getType());
            EQUALS_DEF = new MethodDefinition(getEnvironmentType().get(BOOLEAN_SYMBOL).getType(), Location.BUILTIN, signature, 1);
            objectType.getDefinition().getMembers().declare(EQUALS_SYMBOL, EQUALS_DEF);
            objectType.getDefinition().incNumberOfMethods();

        } catch (EnvironmentType.DoubleDefException e) {
            // Never happen
        }
    }

    protected final CompilerOptions compilerOptions;
    protected final File source;
    protected SymbolTable symbolTable = new SymbolTable();
    protected EnvironmentType envTypes = new EnvironmentType(null);


    public final SymbolTable.Symbol VOID_SYMBOL = symbolTable.create("void"),
            BOOLEAN_SYMBOL = symbolTable.create("boolean"),
            FLOAT_SYMBOL = symbolTable.create("float"),
            INT_SYMBOL = symbolTable.create("int"),
            OBJECT_SYMBOL = symbolTable.create("Object"),
            EQUALS_SYMBOL = symbolTable.create("equals"),
            NULL_SYMBOL = symbolTable.create("null");
    public MethodDefinition EQUALS_DEF;

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public EnvironmentType getEnvironmentType() {
        return envTypes;
    }

    /**
     * Source file associated with this compiler instance.
     */
    public File getSource() {
        return source;
    }

    /**
     * Run the compiler (parse source file, generate code)
     *
     * @return true on error
     */
    public boolean compile() {
        String sourceFile = source.getAbsolutePath();
        String destFile = getDestFileName(sourceFile);

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
            throws DecacFatalError, LocationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

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
        doCodeGen(prog, destName);

        LOG.info("Compilation of " + sourceName + " successful.");
        return false;
    }

    /**
     * Compilation options (e.g. when to stop compilation, number of registers
     * to use, ...).
     */
    public CompilerOptions getCompilerOptions() {
        return compilerOptions;
    }

    public abstract void doCodeGen(AbstractProgram prog, String destName) throws DecacFatalError, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    public abstract String getDestFileName(String sourceFileName);

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

        return parser.parseProgramAndManageErrors(err);
    }

}
