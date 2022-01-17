package fr.ensimag.deca;

import org.objectweb.asm.*;

public class JavaCompiler implements Opcodes
{
    // A voir si il faut les déclarer statique ou cela pose problème pour le parallisme.
    private ClassWriter classWriter = new ClassWriter(0);
    private FieldVisitor fieldVisitor;
    private MethodVisitor methodVisitor;
    private AnnotationVisitor annotationVisitor;

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
}
