/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 * Aufgabe 1
 * d) Die Klasse NotExpr erweitert die Klasse UnaryExpr so, dass bei der Erzeugung eines NotExpr-
 * Objektes der richtige Operator
 *
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

public class NotExpr {
}
