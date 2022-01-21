package fr.ensimag.deca;

import fr.ensimag.deca.codegen.LabelManager;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.codegen.Stack;
import fr.ensimag.deca.codegen.VTable;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tree.AbstractProgram;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.AbstractLine;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Instruction;
import fr.ensimag.ima.pseudocode.Label;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class IMACompiler extends DecacCompiler {
    /**
     * Portable newline character.
     */
    private static final String nl = System.getProperty("line.separator", "\n");

    public IMACompiler(CompilerOptions compilerOptions, File source) {
        super(compilerOptions, source);

        this.stack = new Stack(this);
        this.registerManager = new RegisterManager(compilerOptions.getRegisterNumber());
        this.labelManager = new LabelManager();
        this.vTable = new VTable(this);

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

    public void setDeclareMethod(boolean declareMethod) {
        this.declareMethod = declareMethod;
    }

    @Override
    public void doCodeGen(AbstractProgram prog, String destName) throws DecacFatalError {
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
    }

    @Override
    public String getDestFileName(String sourceFileName) {
        sourceFileName = sourceFileName.substring(0, sourceFileName.length() - 4);
        sourceFileName = sourceFileName + "ass";
        return sourceFileName;
    }
}
