/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 * Aufgabe 8
 * Als letztes wollen wir die Normalisierung von booleschen Ausdrücken betrachten. Genau genommen
 * wollen wir uns mit der Erstellung der kanonischen Variante der disjunktiven Normalform
 * (DNF) beschäftigen.
 * Die DNF lässt sich im allgemeinen durch Ablesen der Wahrheitstabelle erstellen. Dabei betrachtet
 * man die Zeilen der Wahrheitstabelle, welche zu „wahr“ ausgewertet wurden. Jede dieser
 * Zeilen wird in eine UND-Verknüpfung aller Variablen überführt, wobei mit false belegte Variablen
 * negiert werden müssen. Anschließend werden alle Zeilen ODER-verknüpft.
 * Kanonisch ist die DNF dann, wenn jede der UND-Verknüpfungen alle Variablen enthält.
 * Aufgrund der Tatsache, dass die hier verwendeten logischen Operatoren AND und OR binär
 * sind und damit nur zwei Operanden verknüpfen können, müssen folgende Regeln implementiert
 * werden:
 * • Die Variablen in einer Zeile werden immer lexikographisch aufsteigend betrachtet.
 * • Bei mehr als zwei Variablen werden die AND-Operationen von links beginnend geklammert.
 * • Die Zeilen werden entsprechend der Reihenfolge des Iterators aus Aufgabe 4 e) betrachtet.
 * • Bei mehr als zwei Zeilen werden die OR-Operationen von links beginnend geklammert.
 * Beispiele: siehe Tabelle 3.
 * Erstellen Sie folgende Klassen (im Package booleanexpr):
 * a) NormalForm – Abstrakte Klasse mit einem Konstruktor, der genau einen booleschen Ausdruck
 * als Expr-Objekt entgegennimmt. Die Methode getTrueAssignments() soll eine Liste
 * aller Variablenbelegungen (als VarAssignment-Objekte) zurückgeben, für die dieser Ausdruck
 * den Wert „wahr“ ergibt. Analog soll getFalseAssignments() die Variablenbelegungen
 * zurückgeben, für die der Ausdruck „falsch“ ergibt. Die Listen sollen wie üblich sortiert sein.
 * Des Weiteren wird die abstrakte Methode normalize() für implementierende Unterklassen
 * vorgegeben.
 * b) DisjunctiveNormalForm – Erweitert die Klasse NormalForm und implementiert die Methode
 * normalize() so, dass sie ein Expr-Objekt in kanonischer DNF zurückliefert, welches das
 * im Konstruktor übergebene Expr-Objekt repräsentiert und entsprechend den obigen Regeln
 * geklammert ist.
 */

package booleanexpr;

import booleanexpr.expr.EvalVisitor;
import booleanexpr.expr.Expr;
import booleanexpr.expr.Var;
import booleanexpr.expr.VarAssignment;
import booleanexpr.expr.VarExtractVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class NormalForm {
    private Expr expr;
    private Set<Var> vars;

    /**
     * Man hat eine abstrakte Klasse mit einem Konstruktor, nicht um ein Objekt zu erstellen
     * sondern weil bei der b) eine konkrete Unterklasse geschrieben wird, welche den Konstruktor nutzt,
     * um Code auszulagern
     * Sonst müssten in den Unterklassen Konstruktoren implementiert werden
     *
     * @param expr boolescher Ausdruck als Expr Objekt
     * @var vars Variable, aber kein Parameter vom Konstruktur: Speichert in die Variable, Konstruktor erstellt
     * bereits Visitor, um die Variablen zu extrahieren als Speicherreferenz in das Set (keine Liste)
     */
    public NormalForm(Expr expr) {
        this.expr = expr;

        VarExtractVisitor varExtractVisit = new VarExtractVisitor(); expr.accept(varExtractVisit);
        this.vars = varExtractVisit.getVars();
    }

    /**
     * Rückgabetyp muss Liste sein, also mach neue Liste, die ist erstmal leer
     * 1. Erstelle neue Liste List<VarAssignment> list = new ArrayList<>();
     * Die ist leer
     * Standardkonstruktur: wenn kein Konstruktor geschrieben, ohne Parameterübergabe, aber stellt Methoden
     * und Attribute zur Verfügung
     * Ansonsten werden beim Erstellen eines Konstruktors, Parameter mitübergeben
     * Was hat es also? Es hat eine Map als Attribut und List als Attribut
     * Aber beide Leer und haben noch nichts
     * 2. Es muss ein neues VarAssignment Objekt erstellt werden, weil man damit alle Belegungen
     * iterieren kann für alle Variablen
     * Man schreibt eine Wahrheitstabelle zum Iterieren
     * 3. Es muss ein neues Visitor Objekt erstellt werden, weil wir alle Variablen aus dem Ausdruck filtern
     * und in die Liste befüllen
     * siehe Konstruktor
     * 4. Übergabe von einzelnen Variablen mit dem Wert False
     * 5. EvalVisitor gibt True/False für das Assignment zurück, kennt aber das VarAssignment noch nicht
     * 6. Deshalb Variable mit Belegung aus VarAssignment befüllen
     * 7. Durchiterieren mit einem Iterator der Klasse VarAssignment unt der Methode next()
     * 8. expr.accept: Expression accept den Visitor, der den Ausdruck auswertet
     * In Abhängigkeit des Ausdrucks nutzt er seine visits und die Methoden, um die Expression auszuwerten
     * Er schaut aber immer, ob true oder false
     * Wenn true, soll es in die liste übergeben werden
     *
     * @return befüllte Liste
     */
    public List<VarAssignment> getTrueAssignments() {
        List<VarAssignment> list = new ArrayList<>(); VarAssignment varAss = new VarAssignment(); for (Var var : vars) {
            varAss.setVar(var, false); // False nur für den Anfang gesetzt
        } for (VarAssignment it : (Iterable<VarAssignment>) varAss) {
            EvalVisitor evalVisit = new EvalVisitor(); for (Var var : it.getVars()) {
                evalVisit.setVar(var, it.getAssignment(var));
            } if (expr.accept(evalVisit)) list.add(it);
        } return list;
    }

    public List<VarAssignment> getFalseAssignments() {
        List<VarAssignment> list = new ArrayList<>(); VarAssignment varAss = new VarAssignment(); for (Var var : vars) {
            varAss.setVar(var, false); // False nur für den Anfang gesetzt
        } for (VarAssignment it : (Iterable<VarAssignment>) varAss) {
            EvalVisitor evalVisit = new EvalVisitor(); for (Var var : it.getVars()) {
                evalVisit.setVar(var, it.getAssignment(var));
            } if (!((boolean) expr.accept(evalVisit))) list.add(it);
        } return list;
    }

    public abstract Expr normalize();
}
