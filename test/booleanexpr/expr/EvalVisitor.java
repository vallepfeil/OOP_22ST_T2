/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 * <p>
 * Aufgabe 5
 * Eine weitere Implementierung des Visitors hat die Auswertung von booleschen Ausdrücken zum
 * Ziel. Implementieren Sie den EvalVisitor im Package booleanexpr.expr so, dass ein Expr-
 * Objekt ein EvalVisitor-Objekt akzeptiert und als Ergebnis true oder false zurückgeliefert
 * wird. Zur Erinnerung steht Ihnen die Wahrheitstabelle der logischen Operatoren in Tabelle 2
 * zur Verfügung.
 * Um Variablen Werte zuzuordnen, soll der EvalVisitor die beiden Methoden setVar(Var var,
 * boolean value) und getVar(Var var) zur Verfügung stellen. Die erste setzt eine übergebene
 * Variable auf den ebenfalls übergebenen Wert und die zweite Methode liefert den gesetzten Wert
 * zurück.
 * Falls getVar(...) mit einer unbekannten Variable aufgerufen wird oder eine benötigte Variable
 * bei der Auswertung eines Ausdrucks fehlt, soll eine UnknownVarException geworfen werden.
 * Hinweis: Denken Sie daran, das Sie bereits eine Klasse zur Zuweisung von Variablenbelegungen
 * implementiert haben.
 * expr.accept(evalvisit)
 */

package booleanexpr.expr;

public class EvalVisitor implements Visitor<Boolean> {
    VarAssignment visitVarAss = new VarAssignment();

    @Override
    public Boolean visit(AndExpr expr) {
        return (Boolean) expr.getLeftOperand().accept(this) & (Boolean) expr.getRightOperand().accept(this);
    }

    @Override
    public Boolean visit(Const expr) {
        return expr.isValue();
    }

    @Override
    public Boolean visit(EqualsExpr expr) {
        return expr.getLeftOperand().accept(this) == expr.getRightOperand().accept(this);
    }

    @Override
    public Boolean visit(NotExpr expr) {
        return !(Boolean) expr.getOperand().accept(this);
    }

    @Override
    public Boolean visit(OrExpr expr) {
        return (Boolean) expr.getLeftOperand().accept(this) | (Boolean) expr.getRightOperand().accept(this);
    }

    @Override
    public Boolean visit(Var expr) {
        return getVar(expr);
    }

    @Override
    public Boolean visit(XorExpr expr) {
        return (Boolean) expr.getLeftOperand().accept(this) ^ (Boolean) expr.getRightOperand().accept(this);
    }

    public void setVar(Var var, boolean value) {
        visitVarAss.setVar(var, value);
    }

    public Boolean getVar(Var var) throws UnknownVarException {
        if (!(visitVarAss.isAssigned(var))) throw new UnknownVarException(); return visitVarAss.getAssignment(var);
    }
}
