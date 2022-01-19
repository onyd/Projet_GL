package fr.ensimag.deca;

import fr.ensimag.deca.codegen.LabelManager;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.codegen.Stack;
import fr.ensimag.deca.codegen.VTable;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.syntax.DecaLexer;
import fr.ensimag.deca.syntax.DecaParser;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tree.AbstractProgram;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.deca.tree.LocationException;
import fr.ensimag.ima.pseudocode.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang.StringUtils;
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
        this.stack = new Stack(this);
        this.registerManager = new RegisterManager(compilerOptions.getRegisterNumber());
        this.labelManager = new LabelManager();
        this.vTable = new VTable(this);

        // Initialization of env_types
        try {
            envTypes.declare(VOID_SYMBOL, new TypeDefinition(new VoidType(VOID_SYMBOL), Location.BUILTIN));
            envTypes.declare(BOOLEAN_SYMBOL, new TypeDefinition(new BooleanType(BOOLEAN_SYMBOL), Location.BUILTIN));
            envTypes.declare(FLOAT_SYMBOL, new TypeDefinition(new FloatType(FLOAT_SYMBOL), Location.BUILTIN));
            envTypes.declare(INT_SYMBOL, new TypeDefinition(new IntType(INT_SYMBOL), Location.BUILTIN));

            ClassType objectType = new ClassType(OBJECT_SYMBOL, Location.BUILTIN, null);
            envTypes.declare(OBJECT_SYMBOL, objectType.getDefinition());

        } catch (EnvironmentType.DoubleDefException e) {
            // Never happen
        }
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
        if(declareMethod) {
            declMethodProg.add(line);
        } else {
            program.add(line);
        }
    }

    /**
     * @see fr.ensimag.ima.pseudocode.IMAProgram#addComment(java.lang.String)
     */
    public void addComment(String comment) {
        if(declareMethod) {
            declMethodProg.addComment(comment);
        } else {
            program.addComment(comment);
        }
    }

    /**
     * @see
     * fr.ensimag.ima.pseudocode.IMAProgram#addLabel(fr.ensimag.ima.pseudocode.Label)
     */
    public void addLabel(Label label) {
        if(declareMethod) {
            declMethodProg.addLabel(label);
        } else {
            program.addLabel(label);
        }
    }

    /**
     * @see
     * fr.ensimag.ima.pseudocode.IMAProgram#addInstruction(fr.ensimag.ima.pseudocode.Instruction)
     */
    public void addInstruction(Instruction instruction) {
        if(declareMethod) {
            declMethodProg.addInstruction(instruction);
        } else {
            program.addInstruction(instruction);
        }
    }
    public void addInstructionByte(InstructionByte instructionByte) {
        program.addInstruction(instructionByte);
    }

    /**
     * @see
     * fr.ensimag.ima.pseudocode.IMAProgram#addInstruction(fr.ensimag.ima.pseudocode.Instruction,
     * java.lang.String)
     */
    public void addInstruction(Instruction instruction, String comment) {
        if(declareMethod) {
            declMethodProg.addInstruction(instruction, comment);
        } else {
            program.addInstruction(instruction, comment);
        }
    }

    /**
     * @see
     * fr.ensimag.ima.pseudocode.IMAProgram#append(fr.ensimag.ima.pseudocode.IMAProgram)
     */
    public void append(IMAProgram p) {
        program.append(p);
    }

    public void appendMethodProg() {
        program.append(declMethodProg);
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
    private EnvironmentType envTypes = new EnvironmentType(null);

    private Stack stack;
    private RegisterManager registerManager;
    private LabelManager labelManager;
    private VTable vTable;

    public Stack getStack() {
        return stack;
    }

    public RegisterManager getRegisterManager() {
        return registerManager;
    }

    public LabelManager getLabelManager() {
        return labelManager;
    }

    public VTable getvTable() {
        return vTable;
    }

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
     * The main program. Every instruction generated will eventually end up here.
     */
    private final IMAProgram program = new IMAProgram();

    /**
     * The program that create the constructor and the method for all the classes
     */
    private final IMAProgram declMethodProg = new IMAProgram();
    private boolean declareMethod = false;

    /**
     * Contient les classes pour générer les fichiers .class
     */
    private JavaCompiler javaCompiler = new JavaCompiler();

    public void setDeclareMethod(boolean declareMethod) {
        this.declareMethod = declareMethod;
    }

    /**
     * Run the compiler (parse source file, generate code)
     *
     * @return true on error
     */
    public boolean compile() {
        String sourceFile = source.getAbsolutePath();
        String destFile = sourceFile;
        String destByteFile = sourceFile;
        destFile = destFile.substring(0, destFile.length() - 4);
        destByteFile = destByteFile.substring(0, destByteFile.length() - 4);
        destFile = destFile + "ass";
        destByteFile = destByteFile + "class";
        PrintStream err = System.err;
        PrintStream out = System.out;
        LOG.debug("Compiling file " + sourceFile + " to assembly file " + destFile);
        try {
            return doCompile(sourceFile, destFile,destByteFile,out, err);
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
     * @param destByteName name of the bytecode destination (assembly) file
     * @param out stream to use for standard output (output of decac -p)
     * @param err stream to use to display compilation errors
     *
     * @return true on error
     */
    private boolean doCompile(String sourceName, String destName, String destByteName,
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

        //String destNameBytecode = removeLastCharactersAndGetClassName(destName,4);
        if(this.compilerOptions.getByteFiles())
        {
            int index = 0;
            int futurIndex = destByteName.indexOf("/");
            while (futurIndex !=  -1) {
                index = futurIndex;
                futurIndex = destByteName.indexOf("/", index +  1) ;
            }
            String className = destByteName.substring(index+1, destByteName.length() - 6);
            int fileIndex = destByteName.indexOf(className);
            className = className.substring(0, 1).toUpperCase() + className.substring(1);
            destByteName = (destByteName.substring(0,fileIndex )) + destByteName.substring(fileIndex,fileIndex+1).toUpperCase()
                    + (destByteName.substring(fileIndex+1,destByteName.length()));
            int beginIndex = destByteName.indexOf("src");
            destByteName = destByteName.substring(beginIndex, destByteName.length());
            prog.codeGenProgramByte(this,javaCompiler, destByteName, className);
        }
        addComment("end main program");
        LOG.debug("Generated assembly code:" + nl + program.display());
        LOG.info("Output file assembly file is: " + destName);

        FileOutputStream fstream = null;
        FileOutputStream fstreamByteCode = null;
        try {
            fstream = new FileOutputStream(destName);
        } catch (FileNotFoundException e) {
            throw new DecacFatalError("Failed to open output file: " + e.getLocalizedMessage());
        }

        if(this.compilerOptions.getByteFiles())
        {
            try {
                fstreamByteCode = new FileOutputStream(destByteName);
                System.out.println("Création du " + destByteName);
            } catch (FileNotFoundException e) {
                throw new DecacFatalError("Failed to open output bytecode file.class: " + e.getLocalizedMessage());
            }
        }
        LOG.info("Writing assembler file ...");

        program.display(new PrintStream(fstream));

        if(this.compilerOptions.getByteFiles())
        {
            LOG.info("Writing .class file ...");
            byte[] b = javaCompiler.getClassWriter().toByteArray();
            try
            {
                assert fstreamByteCode != null;
                fstreamByteCode.write(b);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
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

        return parser.parseProgramAndManageErrors(err);
    }

    // peut être supprimer plus tard.
    private String removeLastCharactersAndGetClassName(String inputString, int extensionLength)
    {
        String res = inputString;
        for(int i=0;i<extensionLength;i++)
        {
            res = StringUtils.chop(res);// suppression du dernier s de ass, puis s, puis a.
        }
        String[] s = res.split("/");
        s[s.length-1] = StringUtils.chop(s[s.length-1]);
        return res;
    }

}
