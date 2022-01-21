package fr.ensimag.deca.context;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.Plus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import java.io.File;

import static org.mockito.Mockito.*;

/**
 * Test for the Plus node using mockito, without using advanced features.
 * @see TestPlusAdvanced for more advanced examples.
 * @see TestPlusWithoutMock too see what would need to be written if the test
 * was done without using Mockito.
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class TestPlusPlain {
    final Type INT = new IntType(null);
    final Type FLOAT = new FloatType(null);

    @Test
    public void testType() throws ContextualError {
        File source = new File("src/test/deca/codegen/valid/custom/declaration/bool_declare.deca");
        CompilerOptions compilerOptions = new CompilerOptions();
        DecacCompiler compiler = new DecacCompiler(compilerOptions, source);
        AbstractExpr left = Mockito.mock(AbstractExpr.class);
        when(left.verifyExpr(compiler, null, null)).thenReturn(INT);
        AbstractExpr right = Mockito.mock(AbstractExpr.class);
        when(right.verifyExpr(compiler, null, null)).thenReturn(INT);
        Plus t = new Plus(left, right);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isInt());
        // check that the mocks have been called properly.
        verify(left).verifyExpr(compiler, null, null);
        verify(right).verifyExpr(compiler, null, null);
    }
}