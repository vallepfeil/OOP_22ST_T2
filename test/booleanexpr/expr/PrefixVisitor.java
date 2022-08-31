/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 * Aufgabe 3
 * Für eine praktische Anwendung des Visitor-Patterns beginnen wir mit der Implementierung von
 * drei Varianten der Generierung von Strings im Package booleanexpr.expr:
 * a) Der InfixVisitor ahmt das Verhalten der toString()-Methoden der Expr-Klassen nach.
 * Dabei soll ein zusätzlicher Konstruktor fünf Zeichenketten als Parameter akzeptieren, die
 * der Visitor zur Darstellung der Operatoren and, equals, not, or und xor (in genau dieser
 * Reihenfolge) verwendet. Der Standardkonstruktor definiert hierfür die in Java üblichen
 * Repräsentationen. Nicht unäre Operatoren müssen geklammert werden.
 * b) Der PrefixVisitor stellt Operatoren jeweils voran. Der Operator und die beiden Operanden
 * werden jeweils durch eine Leerstelle getrennt. Bei dieser Notation werden keine Klammern benötigt.
 * Die Konstruktoren des PrefixVisitors sollen sich genauso wie die des InfixVisitors
 * verhalten.
 * 2
 * Aufruf von next()
 * 1 2 3 4 5 6 7 8
 * Variablen Variablenbelegung
 * x false false false false true true true true
 * y false false true true false false true true
 * z false true false true false true false true
 * Tabelle 1: Darstellung der Variablenbelegungen der VarAssignment-Objekte, welche durch einen
 * Iterator mit den drei bekannten Variablen x, y und z geliefert werden.
 * c) Der PostfixVisitor stellt Operatoren jeweils hintenan. Die beiden Operanden und der
 * Operator werden jeweils durch eine Leerstelle getrennt. Bei dieser Notation werden ebenfalls
 * keine Klammern benötigt, und die Konstruktoren des PostfixVisitors sollen sich ebenfalls
 * genauso wie die des Prefix- und des InfixVisitors verhalten.
 */

package booleanexpr.expr;

public class PrefixVisitor implements Visitor<String> {
    String and;
    String equals;
    String not;
    String or;
    String xor;

    /**
     * Speichert die Konstruktor-Parameter als Attribute
     *
     * @param and
     * @param equals
     * @param not
     * @param or
     * @param xor
     */
    public PrefixVisitor(String and, String equals, String not, String or, String xor) {
        this.and = and; this.equals = equals; this.not = not; this.or = or; this.xor = xor;
    }

    public PrefixVisitor() {
        this.and = "&"; this.equals = "=="; this.not = "!"; this.or = "|"; this.xor = "^";
    }

    /**
     * Unbedingt nochmal angucken!
     * Composite Pattern kombiniert mit Visitor-Pattern
     * Composite liefert modellierte Datenstruktur (liefert verschachtelte Ausdrücke)
     * Visitor erlaubt an einer zentralen Stelle einer neuen Visitor Klasse in dieser Datenstruktur Funktionalitäten
     * zu schreiben
     * Man muss nicht die toString Methode in jedes der Expr schreiben, sondern man macht einen Visitor, der darauf
     * zugreifen kann
     *
     * @param expr
     * @return
     */
    @Override
    public String visit(AndExpr expr) {
        return and + " " + expr.getLeftOperand().accept(this) + " " + expr.getRightOperand().accept(this);
    }

    public String visit(EqualsExpr expr) {
        return equals + " " + expr.getLeftOperand().accept(this) + " " + expr.getRightOperand().accept(this);
    }

    public String visit(NotExpr expr) {
        return not + " " + expr.getOperand().accept(this);
    }

    public String visit(OrExpr expr) {
        return or + " " + expr.getLeftOperand().accept(this) + " " + expr.getRightOperand().accept(this);
    }

    public String visit(XorExpr expr) {
        return xor + " " + expr.getLeftOperand().accept(this) + " " + expr.getRightOperand().accept(this);
    }

    /**
     * @param expr In dem Moment, in dem dieses Objekt mit einem Const Parameter aufgerufen wird, wird ein Const-Objekt
     *             draus
     * @return
     */
    @Override
    public String visit(Const expr) {
        return expr.toString();
    }

    /**
     * @param expr
     * @return Gibt String aus, einfacher Fall, weil keine Ausdrücke/Composite drin
     */
    @Override
    public String visit(Var expr) {
        return expr.toString();
    }
}
