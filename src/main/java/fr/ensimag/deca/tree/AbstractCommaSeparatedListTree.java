package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

import java.util.Iterator;
import java.util.ListIterator;

public class AbstractCommaSeparatedListTree<T extends Tree> extends AbstractListTree<T> {
    @Override
    public void decompile(IndentPrintStream s) {
        Iterator<T> it = getList().iterator();
        if (it.hasNext()) {
            it.next().decompile(s);
        }
        while (it.hasNext()) {
            s.print(", ");
            it.next().decompile(s);
        }
    }
}
