package fr.ensimag.deca;

import fr.ensimag.ima.pseudocode.Instruction;
import org.objectweb.asm.*;

public class JavaCompiler implements Opcodes
{
    // A voir si il faut les déclarer statique ou cela pose problème pour le parallisme.
    private ClassWriter classWriter = new ClassWriter(0);
    private FieldVisitor fieldVisitor;
    private MethodVisitor methodVisitor;
    private AnnotationVisitor annotationVisitor;
    private String className;

    public void setMethodVisitor(MethodVisitor methodVisitor)
    {
        this.methodVisitor = methodVisitor;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public ClassWriter getClassWriter()
    {
        return classWriter;
    }

    public FieldVisitor getFieldVisitor()
    {
        return fieldVisitor;
    }

    public MethodVisitor getMethodVisitor()
    {
        return methodVisitor;
    }

    public AnnotationVisitor getAnnotationVisitor()
    {
        return annotationVisitor;
    }

    /**
     * Il faut faire une structure similaire à Ima pour suivre sa logique.
     * Cette méthode a pour role de faire WNL et non pas toutes les instructions ima.
     */
    public void addInstruction(int opCode)
    {

    }
}
