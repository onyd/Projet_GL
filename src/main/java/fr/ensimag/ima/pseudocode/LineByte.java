package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;

/**
 * Line of code in an IMA program.
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class LineByte extends AbstractLine {
    public LineByte(Label label, InstructionByte instructionByte, String comment) {
        super();
        checkComment(comment);
        this.label = label;
        this.instructionByte = instructionByte;
        this.comment = comment;
    }

    public LineByte(InstructionByte instructionByte) {
        super();
        this.instructionByte = instructionByte;
    }

    public LineByte(String comment) {
        super();
        checkComment(comment);
        this.comment = comment;
    }

    public LineByte(Label label) {
        super();
        this.label = label;
    }

    private void checkComment(final String s) {
        if (s == null) {
            return;
        }
        if (s.contains("\n")) {
            throw new IMAInternalError("Comment '" + s + "'contains newline character");
        }
        if (s.contains("\r")) {
            throw new IMAInternalError("Comment '" + s + "'contains carriage return character");
        }
    }
    private InstructionByte instructionByte;
    private String comment;
    private Label label;

    @Override
    void display(PrintStream s) {
        boolean tab = false;
        if (label != null) {
            s.print(label);
                        s.print(":");
            tab = true;
        }
        if (instructionByte != null) {
            s.print("\t");
            instructionByte.display(s);
            tab = true;
        }
        if (comment != null) {
            if (tab) {
                            s.print("\t");
                        }
            s.print("; " + comment);
        }
        s.println();
    }

    public void setInstructionByte(InstructionByte instruction) {
        this.instructionByte = instructionByte;
    }

    public InstructionByte getInstructionByte() {
        return instructionByte;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Label getLabel() {
        return label;
    }
}
