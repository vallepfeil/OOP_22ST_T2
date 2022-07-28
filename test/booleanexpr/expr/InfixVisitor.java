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

public class InfixVisitor implements Visitor<String> {
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
    public InfixVisitor(String and, String equals, String not, String or, String xor) {
        this.and = and; this.equals = equals; this.not = not; this.or = or; this.xor = xor;
    }

    public InfixVisitor() {
        this.and = "&"; this.equals = "=="; this.not = "!"; this.or = "|"; this.xor = "^";
    }

    /**
     * Unbedingt nochmal angucken!
     * @param visit
     * @return
     */
    @Override
    public String visit(AndExpr visit) {
        return "(" + visit.getLeftOperand().accept(this) + " " + and +
               " " + visit.getRightOperand().accept(this) + ")";
    }

    public String visit(EqualsExpr visit) {
        return "(" + visit.getLeftOperand().accept(this) + " " + equals +
               " " + visit.getRightOperand().accept(this) + ")";
    }

    public String visit(NotExpr visit) {
        return "(" + not +
               " " + visit.getOperand().accept(this) + ")";
    }

    public String visit(OrExpr visit) {
        return "(" + visit.getLeftOperand().accept(this) + " " + or +
               " " + visit.getRightOperand().accept(this) + ")";
    }

    public String visit(XorExpr visit) {
        return "(" + visit.getLeftOperand().accept(this) + " " + xor +
               " " + visit.getRightOperand().accept(this) + ")";
    }

    /**
     * @param visit In dem Moment, in dem dieses Objekt mit einem Const Parameter aufgerufen wird, wird ein Const-Objekt
     *              draus
     * @return
     */
    @Override
    public String visit(Const visit) {
        return visit.toString();
    }


    /**
     * @param visit
     * @return Gibt String aus, einfacher Fall, weil keine Ausdrücke/Composite drin
     */
    @Override
    public String visit(Var visit) {
        return visit.toString();
    }

}
