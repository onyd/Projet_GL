package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

public class AbstractListTree<T extends Tree> extends TreeList<T> {
    @Override
    public void decompile(IndentPrintStream s) {
        for (Tree t : getList()) {
            t.decompile(s);
        }
    }
}
