package booleanexpr;

import booleanexpr.expr.Expr;

public class DummyNormalForm extends NormalForm {
    public DummyNormalForm(Expr xOrY) {super(xOrY);}

    @Override
    public Expr normalize() {
        return null;
    }
}
