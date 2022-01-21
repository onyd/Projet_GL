package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.IMACompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class DeclParam extends AbstractDeclParam {
    private final AbstractIdentifier typeName;
    private final  AbstractIdentifier paramIdent;

    public DeclParam(AbstractIdentifier typeName, AbstractIdentifier paramIdent) {
        Validate.notNull(typeName);
        Validate.notNull(paramIdent);
        this.typeName = typeName;
        this.paramIdent = paramIdent;
    }

    @Override
    protected Type verifyParamType(DecacCompiler compiler) throws ContextualError {
        Type type = typeName.verifyType(compiler);
        if (type.isVoid()) {
            throw new ContextualError("(2.9) Param cannot have void type", getLocation());
        }
        return type;
    }

    @Override
    protected void verifyParam(DecacCompiler compiler, EnvironmentExp envExpParams) throws ContextualError {
        ExpDefinition def = new ParamDefinition(typeName.getType(), getLocation());
        try {
            envExpParams.declare(paramIdent.getName(), def);
            paramIdent.setDefinition(def);
            paramIdent.setType(def.getType());
        } catch (Environment.DoubleDefException e) {
            throw new ContextualError("Param has already been declared", getLocation());
        }
    }

    @Override
    protected void codeGenDeclParam(IMACompiler compiler, int offset) {
        paramIdent.getExpDefinition().setOperand(new RegisterOffset(offset, Register.LB));
    }

    @Override
    public void decompile(IndentPrintStream s) {
        typeName.decompile(s);
        s.print(" ");
        paramIdent.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        typeName.prettyPrint(s, prefix, false);
        paramIdent.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        typeName.iter(f);
        paramIdent.iter(f);
    }
}
