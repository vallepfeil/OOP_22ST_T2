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
import java.util.Arrays;
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
        return map.containsKey(var);
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
        Var[] varArr = new Var[map.size()]; int i = 0; for (Var v : map.keySet()) {
            varArr[i] = v; i++;
        } Arrays.sort(varArr); return Arrays.stream(varArr).toList();
    }

    /**
     * Bei Verwendung des Interfaces, muss ein eigene Iterator-Klasse geschrieben werden
     * Die können lokal geschrieben werden
     *
     * @return
     */

    public Iterator<VarAssignment> iterator() {
        int size = 1; for (int i = 0; i < map.size(); i++) {
            size *= 2;
        } int finalSize = size; VarAssignment[] varAssArr = new VarAssignment[finalSize];

        VarAssignment newVarAss = null; for (int i = 0; i < finalSize; i++) {
            if (newVarAss == null) {
                newVarAss = new VarAssignment(); for (Var v : map.keySet()) {
                    newVarAss.setVar(v, false);
                }
            }
            else {
                VarAssignment copyVarAss = new VarAssignment(); for (Var v : newVarAss.getVars()) {
                    copyVarAss.map.put(v, newVarAss.map.get(v));
                } newVarAss = copyVarAss; List<Integer> storeTrues = new ArrayList<>();
                for (int j = newVarAss.getVars().size() - 1; j >= 0; j--) {
                    if (!newVarAss.getAssignment(newVarAss.getVars().get(j))) {
                        newVarAss.setVar(newVarAss.getVars().get(j), true); for (int k : storeTrues) {
                            newVarAss.setVar(newVarAss.getVars().get(k), false);
                        } break;
                    }
                    else {
                        storeTrues.add(j);
                    }
                }
            }

            varAssArr[i] = newVarAss;
        }

        return new Iterator<>() {

            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < finalSize;
            }

            @Override
            public VarAssignment next() {
                return varAssArr[currentIndex++];
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; if (o == null || getClass() != o.getClass()) return false;
        VarAssignment that = (VarAssignment) o; return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
