package myLang.abstractSyntax;

import java.util.List;

public class FunAppExt extends AbstractSyntax {
    private final String functionId;
    private final List<AbstractSyntax> params;

    public FunAppExt(String functionId, List<AbstractSyntax> params) {
        this.functionId = functionId;
        this.params = params;
    }

    public String getFunctionId() {
        return functionId;
    }

    public List<AbstractSyntax> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "FunAppExt(" + functionId + ": " + params.toString() + ")";
    }
}
