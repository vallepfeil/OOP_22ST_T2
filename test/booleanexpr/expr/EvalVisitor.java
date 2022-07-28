/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 *
 * Aufgabe 5
 * Eine weitere Implementierung des Visitors hat die Auswertung von booleschen Ausdrücken zum
 * Ziel. Implementieren Sie den EvalVisitor im Package booleanexpr.expr so, dass ein Expr-
 * Objekt ein EvalVisitor-Objekt akzeptiert und als Ergebnis true oder false zurückgeliefert
 * wird. Zur Erinnerung steht Ihnen die Wahrheitstabelle der logischen Operatoren in Tabelle 2
 * zur Verfügung.
 * Um Variablen Werte zuzuordnen, soll der EvalVisitor die beiden Methoden setVar(Var var,
 * boolean value) und getVar(Var var) zur Verfügung stellen. Die erste setzt eine übergebene
 * Variable auf den ebenfalls übergebenen Wert und die zweite Methode liefert den gesetzten Wert
 * zurück.
 * Falls getVar(...) mit einer unbekannten Variable aufgerufen wird oder eine benötigte Variable
 * bei der Auswertung eines Ausdrucks fehlt, soll eine UnknownVarException geworfen werden.
 * Hinweis: Denken Sie daran, das Sie bereits eine Klasse zur Zuweisung von Variablenbelegungen
 * implementiert haben.
 */

package booleanexpr.expr;

public class EvalVisitor implements Visitor{
}
