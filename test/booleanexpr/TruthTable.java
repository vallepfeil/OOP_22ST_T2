/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 * Aufgabe 7
 * Nachdem nun die notwendigen Grundlagen vorhanden sind, soll in dieser Aufgabe eine Wahrheitstabelle
 * realisiert werden. Implementieren Sie dazu die Klasse TruthTable im Package
 * booleanexpr. Der Wahrheitstabelle sollen per addExpr(Expr)-Methode neue Ausdrücke hinzugefügt
 * werden können. Während des Hinzufügens von Expr-Objekten sollten diese direkt nach
 * Variablen untersucht werden (denken Sie an den VarExtractVisitor).
 * Die Wahrheitstabelle bietet neben der addExpr(...)-Methode die Methoden toString() und
 * getVars() an. Letztere liefert die bekannten Variablen als Menge zurück.
 * Die toString()-Methode muss entsprechend der folgenden Beschreibung überschrieben werden:
 * a) Der Kopf der Tabelle besteht aus den Namen der Variablen (in lexikographisch aufsteigender
 * Reihenfolge) sowie einer Folge e1, e2, . . . , en von Bezeichnern für die hinzugefügten Expr-
 * Objekte.
 * b) Darunter folgt eine Trennlinie.
 * c) Darunter folgt der Inhalt der Tabelle mit jeweils einer Zeile pro Variablenbelegung. Die
 * Belegung beginnt beim Zustand „alle false“ und wechselt wie in Aufgabe 4 e) beschrieben
 * zeilenweise bis zum Zustand „alle true“.
 * d) Unter der Tabelle folgt eine Auflistung der dargestellten Ausdrücke. Dazu wird jeweils eine
 * der Bezeichner in e1, e2, . . . , en gefolgt von einem =-Symbol und der Infix-Repräsentation
 * des Ausdrucks angehängt. Jeder Ausdruck wird in einer eigenen Zeile dargestellt.
 * e) Zur Verringerung des Platzbedarfs werden statt den Worten false und true die Symbole F
 * und T verwendet. Innerhalb der Spalten werden die Symbole zentriert bzw. eine Stelle rechts
 * des Zentrums dargestellt.
 * f) Falls keine Expr-Objekte hinzugefügt wurden, wird die Zeichenfolge no expressions defined
 * ohne abschließenden Zeilenumbruch zurück gegeben.
 * Beispiel: Ergebnis der toString()-Methode der Wahrheitstabelle aus Tabelle 2:
 * | x | xyz | e1 | e2 | e3 | e4 | e5 |
 * +---+-----+----+----+----+----+----+
 * | F | F | F | T | T | F | F |
 * | F | T | F | F | T | T | T |
 * | T | F | F | F | F | T | T |
 * | T | T | T | T | F | T | F |
 * e1 = (x & xyz)
 * e2 = (x == xyz)
 * e3 = !x
 * e4 = (x | xyz)
 * e5 = (x ^ xyz)
 * Hinweise:
 * • Der Variablenname y wurde oben durch xyz ersetzt, um die Zentrierung bei längeren
 * Namen darzustellen.
 * • Außer der Formatierung wurden alle notwendigen Algorithmen schon implementiert
 * (VarAssignment, EvalVisitor, . . . )
 */

package booleanexpr;

public class TruthTable {
}