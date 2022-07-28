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
 * die Varaiblenbelegung desjenigen VarAssignment-Objekt, für das die iterator()-Methode
 * aufgerufen wird, dadurch nicht verändert!
 * f) equals(Object) – liefert true zurück, falls das andere Objekt vom Typ VarAssignment ist
 * und sich dieses auf die gleichen Variablen mit den jeweils gleichen Wertebelegungen bezieht.
 * g) hashCode() – liefert einen gültigen Hashcode passend zur equals(...)-Methode zurück.
 */

package booleanexpr.expr;

public class VarAssignment {
}
