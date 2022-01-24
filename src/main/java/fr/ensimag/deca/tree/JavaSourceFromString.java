package fr.ensimag.deca.tree;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

class JavaSourceFromString extends SimpleJavaFileObject {
    final String code;

    JavaSourceFromString(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
        //System.out.println("|||" + super.toString());
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}