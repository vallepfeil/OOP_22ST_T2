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

import booleanexpr.expr.AndExpr;
import booleanexpr.expr.Expr;
import booleanexpr.expr.NotExpr;
import booleanexpr.expr.OrExpr;
import booleanexpr.expr.Var;
import booleanexpr.expr.VarAssignment;

import java.util.ArrayList;
import java.util.List;

public class DisjunctiveNormalForm extends NormalForm {
    /**
     * NormalForm übergibt Attribute und Methoden an DNF
     *
     * @param expr super übernimmt Konstruktor der Oberklasse
     */
    public DisjunctiveNormalForm(Expr expr) {
        super(expr);
    }

    public Expr normalize() {
        List<Expr> orList = new ArrayList<>(); for (VarAssignment it : getTrueAssignments()) {
            List<Expr> andList = new ArrayList<>(); for (Var var : it.getVars()) {
                if (it.getAssignment(var)) {
                    andList.add(var);
                }
                else {
                    andList.add(new NotExpr(var));
                }
            }

            if (andList.size() >= 2) {
                AndExpr andRes = new AndExpr(andList.remove(0), andList.remove(0)); while (!andList.isEmpty()) {
                    andRes = new AndExpr(andRes, andList.remove(0));
                } orList.add(andRes);
            }
            else if (andList.size() == 1) {
                orList.add(andList.remove(0));
            }
        }

        if (orList.size() >= 2) {
            OrExpr orRes = new OrExpr(orList.remove(0), orList.remove(0)); while (!orList.isEmpty()) {
                orRes = new OrExpr(orRes, orList.remove(0));
            } return orRes;
        }
        else if (orList.size() == 1) {
            return orList.remove(0);
        } return null;
    }
}
