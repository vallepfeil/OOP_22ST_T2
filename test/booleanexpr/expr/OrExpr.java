/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 * Aufgabe 1
 * f) Die Klassen AndExpr, EqualsExpr, OrExpr und XorExpr erweitern die Klasse BinaryExpr so,
 * dass bei der Erzeugung eines Objektes der Unterklassen der richtige Operator (als String-
 * Objekt) und die übergebenen Ausdrücke vom Typ Expr an die Oberklasse weitergegeben
 * werden.
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

import java.util.Objects;

public class OrExpr extends BinaryExpr{
    Expr exprL;
    Expr exprR;

    /**
     * @param exprL wenn Konstruktor aufgerufen wurde, Erzeugung des linken Objekts, dann Übergabe vom Ausdruck
     * @param exprR wenn Konstruktor aufgerufen wurde, Erzeugung des rechten Objekts, dann Übergabe vom Ausdruck
     */
    public OrExpr(Expr exprL, Expr exprR) {
        this.exprL = exprL;
        this.exprR = exprR;
    }

    /**
     * @return gibt Operator OR aus
     */
    @Override
    public String getOperator() {return ("|");}

    @Override
    public Expr getLeftOperand() {
        return exprL;
    }

    @Override
    public Expr getRightOperand() {
        return exprR;
    }
    /**
     * @return mit Hilfe Object-Klasse und seiner Methode toString, macht er aus der Übergabe von
     * Strings, welche mit + konkateniert werden
     */
    @Override
    public String toString() {return "(" + getLeftOperand().toString() + " " + getOperator() + " " + getRightOperand().toString() + ")";}

    /**
     * @param o
     * @return Unterscheidung von zwei Objekten, aber auch Bewertung, ob zwei Objekte dieselben sind
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrExpr orExpr = (OrExpr) o;
        return Objects.equals(exprL, orExpr.exprL) && Objects.equals(exprR, orExpr.exprR);
    }

    /**
     * @return Gibt bei Datenstruktur, die auf Hashs basieren, ob sie gleich sind auf Basis dieser errechneten
     * Werte
     */
    @Override
    public int hashCode() {
        return Objects.hash(exprL, exprR);
    }

    /**
     * @param visitor Wenn visitor Strings als Ausgabe verwendet, dann wird auch diese Accept Methode Strings als
     *                Rückgabe verwenden, visitor.visit(this)
     * @param <T> Generischer Typ, kann String, Int, whatever sein
     * @return
     */
    public <T> T accept(Visitor visitor) {return (T) visitor.visit(this);}
}
