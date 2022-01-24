package fr.ensimag.deca;

import fr.ensimag.deca.tree.AbstractProgram;
import org.objectweb.asm.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * The Bytecode manager.
 */
public class JavaCompiler extends DecacCompiler implements Opcodes
{
    //for the main class
    private ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    private FieldVisitor fieldVisitor;
    private MethodVisitor methodVisitor;
    private AnnotationVisitor annotationVisitor;
    private String className;
    private String javaMethodBodies = "%s";

    /**
     * returns java method body.
     * @return returns java method body.
     */
    public String getMethods() {
        return javaMethodBodies;
    }

    /**
     * Add java method body name.
     * @param newJavaMethod new java method.
     */
    public void addJavaMethod(String newJavaMethod) {
        this.javaMethodBodies = String.format(javaMethodBodies, newJavaMethod);
    }

    /**
     * adds Java class method
     * @param className class name.
     */
    public void addJavaJavaMethodClass(String className) {
        this.javaMethodBodies = String.format(javaMethodBodies, ""); // Close the last inserting point
        this.javaMethodBodies += "public static class " + className + "{ %s }"; // Begin new java method insertion
    }

    //for all the declared class
    private HashMap<String, ClassWriter> declClass = new HashMap<>();

    /**
     * Initialize the JavaCompiler.
     * @param compilerOptions the options list
     * @param source the deca source file.
     */
    public JavaCompiler(CompilerOptions compilerOptions, File source) {
        super(compilerOptions, source);
    }

    @Override
    public void doCodeGen(AbstractProgram prog, String destName) throws DecacFatalError, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String path = source.getAbsolutePath().substring(0, source.getAbsolutePath().length() - source.getName().length());
        FileOutputStream fstreamByteCode = null;
        try {
            fstreamByteCode = new FileOutputStream(destName);
        } catch (FileNotFoundException e) {
            throw new DecacFatalError("Failed to open output bytecode file.class: " + e.getLocalizedMessage());
        }
        String className = source.getName().substring(0, source.getName().length() - 5);
        prog.codeGenProgramByte(this, path, className);
        LOG.info("Writing .class file ...");
        byte[] b = getClassWriter().toByteArray();
        try {
            fstreamByteCode.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //for all the declared classes
        for(String declClassName : getDeclClass().keySet()) {
            String destDeclClassName = path + declClassName + ".class";
            try {
                fstreamByteCode = new FileOutputStream(destDeclClassName);
            } catch (FileNotFoundException e) {
                throw new DecacFatalError("Failed to open output bytecode file.class: " + e.getLocalizedMessage());
            }
            b = getDeclClass().get(declClassName).toByteArray();
            try {
                fstreamByteCode.write(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getDestFileName(String sourceFileName) {
        sourceFileName = sourceFileName.substring(0, sourceFileName.length() - 4);
        sourceFileName = sourceFileName + "class";
        return sourceFileName;
    }

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

    public void setClassWriter(ClassWriter classWriter) {
        this.classWriter = classWriter;
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

    public HashMap<String, ClassWriter> getDeclClass() {
        return declClass;
    }
}
