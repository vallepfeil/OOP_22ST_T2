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

import java.util.HashSet;
import java.util.Set;

public class VarExtractVisitor implements Visitor {
    Set<Var> var = new HashSet<>();

    @Override
    public Object visit(AndExpr expr) {
        expr.getLeftOperand().accept(this); expr.getRightOperand().accept(this); return null;
    }

    /**
     * @param expr unterschiedlichen Typs, wenn unterschiedlichen Typen erkannt,
     *             springt diese in die jeweiligen visits rein und führt den Code aus
     * @return null, weil in Const keine Variablen vorkommen
     */
    @Override
    public Object visit(Const expr) {
        return null;
    }

    @Override
    public Object visit(EqualsExpr expr) {
        expr.getLeftOperand().accept(this); expr.getRightOperand().accept(this); return null;
    }

    @Override
    public Object visit(NotExpr expr) {
        expr.getOperand().accept(this); return null;
    }

    @Override
    public Object visit(OrExpr expr) {
        expr.getLeftOperand().accept(this); expr.getRightOperand().accept(this); return null;
    }

    @Override
    public Object visit(Var expr) {
        var.add(expr); return null;
    }

    @Override
    public Object visit(XorExpr expr) {
        expr.getLeftOperand().accept(this); expr.getRightOperand().accept(this); return null;
    }

    /**
     * @return var als Set<Var> muss zurückgegeben werden, Sets sind bestimmte Listen, nachlesen!
     */
    public Set<Var> getVars() {
        return var;
    }
}
