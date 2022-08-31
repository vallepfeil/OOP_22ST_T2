/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 * Aufgabe 1
 * d) Die Klasse NotExpr erweitert die Klasse UnaryExpr so, dass bei der Erzeugung eines NotExpr-
 * Objektes der richtige Operator (als String-Objekt) und die übergebene Expr an die Oberklasse
 * weitergegeben werden.
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
 * <p>
 * Konstruktor, Variable die Speichert, Get-Methoden
 */
package booleanexpr.expr;

import java.util.Objects;

public class NotExpr extends UnaryExpr {
    Expr expr;

    /**
     * @param expr wenn Konstruktor aufgerufen wurde, Erzeugung des Objekts, dann Übergabe vom Ausdruck
     */
    public NotExpr(Expr expr) {
        this.expr = expr;
    }

    /**
     * @return gibt Operator Not/"!" aus
     */
    @Override
    public String getOperator() {return ("!");}

    @Override
    public Expr getOperand() {
        return expr;
    }

    /**
     * @return mit Hilfe Object-Klasse und seiner Methode toString, macht er aus der Übergabe von
     * Strings, welche mit + konkateniert werden
     */
    @Override
    public String toString() {return getOperator() + getOperand().toString();}

    /**
     * @param o
     * @return Unterscheidung von zwei Objekten, aber auch Bewertung, ob zwei Objekte dieselben sind
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; if (o == null || getClass() != o.getClass()) return false;
        NotExpr notExpr = (NotExpr) o; return Objects.equals(expr, notExpr.expr);
    }

    /**
     * @return Gibt bei Datenstruktur, die auf Hashs basieren, ob sie gleich sind auf Basis dieser errechneten
     * Werte
     */
    @Override
    public int hashCode() {
        return Objects.hash(expr);
    }

    /**
     * @param visitor Wenn visitor Strings als Ausgabe verwendet, dann wird auch diese Accept Methode Strings als
     *                Rückgabe verwenden, visitor.visit(this)
     * @param <T>     Generischer Typ, kann String, Int, whatever sein
     * @return
     */
    public <T> T accept(Visitor visitor) {return (T) visitor.visit(this);}
}
