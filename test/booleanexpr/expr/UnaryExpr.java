/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 * Aufgabe 1
 * c) Die abstrakte Klasse UnaryExpr bildet die Basis für unäre Operatoren. Über die Methode
 * getOperator() stellt sie die textuelle Darstellung des Operators als String zur Verfügung.
 * Die Methode getOperand() ermöglicht den Zugriff auf den Operanden der Operation.
 * <p>
 * h) Alle Expr-Objekte sollen beim Aufruf der toString()-Methode geeignete Zeichenketten zurückliefern.
 * So sollen Var-Objekte ihren Namen, Const-Objekte ihren Wert als entsprechendes
 * Java-Literal, unäre Operationen den Operator direkt vor dem Operanden, z.B. !x, und
 * binäre Operationen eine geklammerte Infix-Repräsentation zurückliefern, z.B. (a & b), wobei
 * a und b Variablennamen sind.
 * i) Alle Expr-Objekte sollen über naheliegende Implementierungen der equals(Object)- und
 * hashCode()-Methoden verfügen. Dabei soll nur die strukturelle Gleichheit, aber nicht Gleichheit
 * nach entsprechender Umformung geprüft werden. Beispielsweise sollen die beiden Terme
 * (a & b) und (b & a) nicht als gleich gelten.
 */

package booleanexpr.expr;

public abstract class UnaryExpr implements Expr {
    /**
     * stellt sie die textuelle Darstellung des Operators als String zur Verfügung
     */
    public abstract String getOperator();

    /**
     * ermöglicht den Zugriff auf den Operanden der Operation
     * Operand kann Expression/Ausdruck sein
     */
    public abstract Expr getOperand();
}
