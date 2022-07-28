/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 * Aufgabe 6
 * Für eine spätere Aufgabe implementieren Sie im Package booleanexpr.expr den VarExtract-
 * Visitor, der ein Expr-Objekt besucht und dabei alle vorkommenden Variablen speichert. Die
 * Menge der gespeicherten Variablen soll im Anschluss von der getVars()-Methode zurückgeliefert
 * werden.
 */

package booleanexpr.expr;

public class VarExtractVisitor implements Visitor{
    @Override
    public Object visit(AndExpr visit) {
        return null;
    }

    @Override
    public Object visit(Const visit) {
        return null;
    }

    @Override
    public Object visit(EqualsExpr visit) {
        return null;
    }

    @Override
    public Object visit(NotExpr visit) {
        return null;
    }

    @Override
    public Object visit(OrExpr visit) {
        return null;
    }

    @Override
    public Object visit(Var visit) {
        return null;
    }

    @Override
    public Object visit(XorExpr visit) {
        return null;
    }
}
