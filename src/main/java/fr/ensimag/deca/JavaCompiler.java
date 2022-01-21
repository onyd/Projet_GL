package fr.ensimag.deca;

import org.objectweb.asm.*;

import java.util.HashMap;

public class JavaCompiler implements Opcodes
{
    //for the main class
    private ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    private FieldVisitor fieldVisitor;
    private MethodVisitor methodVisitor;
    private AnnotationVisitor annotationVisitor;
    private String className;

    //for all the declared class
    private HashMap<String, JavaCompiler> declClass = new HashMap<>();

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

    public HashMap<String, JavaCompiler> getDeclClass() {
        return declClass;
    }
}
