/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 * Aufgabe 1
 * g) Die Klasse Var repräsentiert eine Variable in einem booleschen Ausdruck. Ein Var-Objekt
 * bekommt im Konstruktor einen Bezeichner als String übergeben und liefert diesen mit der
 * Methode getName() zurück. Der Versuch, true oder false als Bezeichner zu wählen, soll
 * mit einer IllegalArgumentException beantwortet werden.
 */

package booleanexpr.expr;

import java.util.Comparator;
import java.util.Objects;

public class Var implements Expr, Comparable {
    String name;

    /**
     * @param name wenn Konstruktor aufgerufen wurde, Erzeugung des linken Objekts, dann Übergabe vom Ausdruck
     */
    public Var(String name) throws IllegalArgumentException {
        if (name == "true" | name == "false") {
            throw new IllegalArgumentException();
        }
        else {
            this.name = name;
        }
    }

    /**
     * @return gibt Var, also ihren Namen aus
     */
    public String getName() {return this.name;}

    /**
     * @return Hätte Speicheradressierung und Nummerierung zur Laufzeit des Objekts übergeben, i. d. F.
     * Übergabe vom Namen von Var
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * @param o
     * @return Unterscheidung von zwei Objekten, aber auch Bewertung, ob zwei Objekte dieselben sind
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Var var = (Var) o;
        return Objects.equals(name, var.name);
    }

    /**
     * @return Gibt bei Datenstruktur, die auf Hashs basieren, ob sie gleich sind auf Basis dieser errechneten
     * Werte
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    /**
     * @param visitor Wenn visitor Strings als Ausgabe verwendet, dann wird auch diese Accept Methode Strings als
     *                Rückgabe verwenden, visitor.visit(this)
     * @param <T> Generischer Typ, kann String, Int, whatever sein
     * @return
     */
    public <T> T accept(Visitor visitor) {return (T) visitor.visit(this);}
}
