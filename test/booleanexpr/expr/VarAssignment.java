/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 * Aufgabe 4
 * Implementieren Sie im Package booleanexpr.expr die Klasse VarAssignment, welche es ermöglichen
 * soll, Variablen boolesche Werte zuzuordnen. Die Klasse VarAssignment soll über folgende
 * Methoden verfügen:
 * a) setVar(Var, boolean) – ordnet der übergebenen Variable den übergebenen booleschen
 * Wert zu.
 * b) isAssigned(Var) – liefert true zurück, falls der übergebenen Variable ein Wert zugewiesen
 * wurde, ansonsten false.
 * c) getAssignment(Var) – liefert den zugewiesenenWert der übergebenen Variable zurück. Falls
 * kein Wert zugewiesen wurde wird eine UnknownVarException ausgelöst. Die entsprechende
 * Klasse muss im selben Package implementiert werden.
 * d) getVars() – liefert eine Liste aller zugewiesenen Variablen zurück. Die Liste soll bzgl. der
 * Variablennamen lexikographisch aufsteigend sortiert sein.
 * e) iterator() – liefert ein Iterator<VarAssignment>-Objekt zurück. Dieser Iterator soll die
 * 2n möglichen Variablenbelegungen zurückliefern, wobei n für die Anzahl der im vorliegenden
 * VarAssignment-Objekt zugewiesenen Variablen steht. Bei jedem Aufruf von next()
 * wird ein neues VarAssignment-Objekt mit neuer Belegung geliefert. Nachdem alle möglichen
 * Variablenbelegungen geliefert wurden, löst ein weiterer Aufruf von next() eine
 * NoSuchElementException aus und ein Aufruf von hasNext() liefert false zurück, vorher
 * true.
 * Beim ersten Aufruf von next() sind alle Variablen innerhalb des VarAssignment-Objekts auf
 * den Wert false gesetzt. Bei weiteren Aufrufen unterscheidet sich die Variablenbelegung des
 * jeweiligen VarAssignment-Objekts vom vorherigen wie folgt: Der Wert der lexikographisch
 * größten Variable wechselt bei jedem Aufruf der Methode. Sollte diese Variable den Wert true
 * haben, wird auch der Wert der lexikographisch nächstkleineren Variable gewechselt. Sollte
 * diese den Wert true haben, wird wiederum auch der Wert der lexikographisch nächstkleineren
 * Variable gewechselt usw. Dieses System wird bis zur lexikographisch kleinsten Variable
 * fortgesetzt (siehe Tabelle 1).
 * Jeder Aufruf von next() liefert ein neues VarAssignment-Objekt zurück. Insbesondere wird
 * die Variablenbelegung desjenigen VarAssignment-Objekt, für das die iterator()-Methode
 * aufgerufen wird, dadurch nicht verändert!
 * f) equals(Object) – liefert true zurück, falls das andere Objekt vom Typ VarAssignment ist
 * und sich dieses auf die gleichen Variablen mit den jeweils gleichen Wertebelegungen bezieht.
 * g) hashCode() – liefert einen gültigen Hashcode passend zur equals(...)-Methode zurück.
 */

package booleanexpr.expr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VarAssignment implements Iterable {

    Map<Var, Boolean> map = new HashMap<>();
    List<Var> list = new ArrayList<>();

    /**
     * Trägt Belegung vom Ausdruck und Boolean in die Map ein
     *
     * @param var
     * @param bool
     */
    public void setVar(Var var, boolean bool) {
        map.put(var, bool); if (!(list.contains(var))) {
            list.add(var);
        }
    }

    public Boolean isAssigned(Var var) {
        if (map.containsKey(var)) return true;
        else return false;
    }

    /**
     * @param var
     * @return gibt Wert (Boolean) der Belegung zurück, get-Methode von Klasse Maps greift auf Werte zu, welche
     * per put-Methode eingetragen worden sind, siehe setVar-Methode
     * @throws UnknownVarException
     */
    public Boolean getAssignment(Var var) throws UnknownVarException {
        if (map.containsKey(var)) return map.get(var);
        else throw new UnknownVarException();
    }

    /**
     * Das Var, also die Klasse Var kennt keinen Vgl. zw. anderen Variablen, deswegen wird ein neues compareto
     * geschrieben, um die Variablen zu vgl. => weil es eigentlich AUSDRÜCKE sind! (Expr)
     *
     * @return
     */
    public List<Var> getVars() {
        Collections.sort(list); return list;
    }

    /**
     * Bei Verwendung des Interfaces, muss ein eigene Iterator-Klasse geschrieben werden
     * Die können lokal geschrieben werden
     *
     * @return
     */
    public Iterator<VarAssignment> iterator() {
        Iterator<VarAssignment> iterator = new VarIterator(getVars()); return iterator;
    }

    public class VarIterator implements Iterator<VarAssignment> {

        VarAssignment varAss;
        VarAssignment[] varAssArr;
        int c = -1;

        /**
         * varAssArr muss in der Größe definiert werden
         * Definition im Konstruktor anhand der Listengröße
         * Schleife rückwärts laufen (siehe j-For-Schleife)
         * i zählt next Aufrufe der Tabelle (spaltenweise)
         * j zählt Variablen rückwärts, also von unten (zeilenweise)
         *
         * @param list
         */
        public VarIterator(List<Var> list) {
            this.varAssArr = new VarAssignment[(int) Math.pow(2, list.size())];
            for (int i = 0; i < varAssArr.length; i++) {
                varAssArr[i] = this.varAss = new VarAssignment(); for (Var var : list) {
                    varAss.setVar(var, false);
                }
            } for (int i = 1; i < varAssArr.length; i++) {
                List<Var> varAssTList = new ArrayList<>();
                for (int j = varAssArr[i - 1].getVars().size() - 1; j >= 0; j--) {
                    Var v = varAssArr[i - 1].getVars().get(j); //in v steht das z
                    if (varAssArr[i - 1].getAssignment(v) == false) {
                        varAssArr[i].setVar(v, true); //verändere Wert
                        for (Var var : varAssTList) {
                            varAssArr[i].setVar(var, false);
                        } break;
                    }
                    else {
                        varAssTList.add(v);
                    }
                }
                if (i == varAssArr.length - 1) {
                    for (Var v : varAssArr[i].getVars()) {
                        varAssArr[i].setVar(v, true);
                    } break;
                }
            }
        }

        /**
         * in der for-Schleife geht er die Werte der Variablen durch und fragt ab, ob gleich false
         * off by 1 Error, Verrechnung um 1
         * Sagt ob man am Ende angekommen ist, Abbruchbedingung für die Schleife
         * @return false, wenn alle Variablen true sind
         */
        @Override
        public boolean hasNext() {
            if (c == -1) {
                for (Var var : varAssArr[c+1].list) {
                    if (varAssArr[c+1].getAssignment(var) == false) {
                        return true;
                    } ;
                } return false;
            } for (Var var : varAssArr[c].list) {
                if (varAssArr[c].getAssignment(var) == false) {
                    return true;
                } ;
            } return false;
        }

        /**
         * Man schreibt sich ein Array vom Typ varAss
         * Größe 2^n
         * Stelle 0: varAss alle Variablen false
         * Stelle 2^n-1: varAss alle Variablen true
         * An Stelle 0: Ausgabe
         * An Stelle n: Ausgabe
         *
         * @return
         */
        @Override
        public VarAssignment next() {
            return varAssArr[++c];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VarAssignment that = (VarAssignment) o;
        return Objects.equals(map, that.map) && Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map, list);
    }
}
