/**
 * A2-BooleanExpr
 * 2022-07-28
 * VP
 * Aufgabe 2
 * Um mit der in Aufgabe 1 erstellten Datenstruktur arbeiten zu können, implementieren Sie nun
 * das Visitor Pattern im Package booleanexpr.expr.
 * a) Erstellen Sie das Interface Visitor. Dieses Interface soll über jeweils eine abstrakte Methode
 * für jede instanziierbare Klasse der Expr-Struktur verfügen. Diese Methoden folgen dem Muster:
 * T visit(<Klasse> expr). Dabei sollte Ihnen aufgefallen sein, dass das Interface über
 * den generischen Typparameter T verfügt.
 * b) Erweitern Sie nun alle Klassen und Interfaces der Expr-Struktur um eine geeignete Implementierung
 * der bekannten accept-Methode des Visitor-Patterns. Beachten Sie, dass diese
 * Methode (genau wie die visit-Methode) einen generischen Rückgabetyp aufweisen muss.
 * An dieser Stelle kann erneut T verwendet werden.
 * <p>
 * Aufgabe 3
 * Für eine praktische Anwendung des Visitor-Patterns beginnen wir mit der Implementierung von
 * drei Varianten der Generierung von Strings im Package booleanexpr.expr:
 * a) Der InfixVisitor ahmt das Verhalten der toString()-Methoden der Expr-Klassen nach.
 * Dabei soll ein zusätzlicher Konstruktor fünf Zeichenketten als Parameter akzeptieren, die
 * der Visitor zur Darstellung der Operatoren and, equals, not, or und xor (in genau dieser
 * Reihenfolge) verwendet. Der Standardkonstruktor definiert hierfür die in Java üblichen
 * Repräsentationen. Nicht unäre Operatoren müssen geklammert werden.
 * b) Der PrefixVisitor stellt Operatoren jeweils voran. Der Operator und die beiden Operanden
 * werden jeweils durch eine Leerstelle getrennt. Bei dieser Notation werden keine Klammern benötigt.
 * Die Konstruktoren des PrefixVisitors sollen sich genauso wie die des InfixVisitors
 * verhalten.
 * 2
 * Aufruf von next()
 * 1 2 3 4 5 6 7 8
 * Variablen Variablenbelegung
 * x false false false false true true true true
 * y false false true true false false true true
 * z false true false true false true false true
 * Tabelle 1: Darstellung der Variablenbelegungen der VarAssignment-Objekte, welche durch einen
 * Iterator mit den drei bekannten Variablen x, y und z geliefert werden.
 * c) Der PostfixVisitor stellt Operatoren jeweils hintenan. Die beiden Operanden und der
 * Operator werden jeweils durch eine Leerstelle getrennt. Bei dieser Notation werden ebenfalls
 * keine Klammern benötigt, und die Konstruktoren des PostfixVisitors sollen sich ebenfalls
 * genauso wie die des Prefix- und des InfixVisitors verhalten.
 * <p>
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
 * <p>
 * Aufgabe 6
 * Für eine spätere Aufgabe implementieren Sie im Package booleanexpr.expr den VarExtract-
 * Visitor, der ein Expr-Objekt besucht und dabei alle vorkommenden Variablen speichert. Die
 * Menge der gespeicherten Variablen soll im Anschluss von der getVars()-Methode zurückgeliefert
 * werden.
 */

package booleanexpr.expr;

/**
 * @param <T> Erstellt einen String-Visitor o. Anderes, T steht auch für den Rückgabetyp der Methode
 */
public interface Visitor<T> {
    /**
     * visit gibt ein Objekt vom Typ T, damit es abstrakt/generisch bleibt
     * läuft über Datenstruktur
     * jede Art von Objekten einer Datenstruktur erhält eine Visit-Methode
     * das Ganze ist abstrakt, wichtig für Aufgabe 3
     */
    public T visit(AndExpr visit);

    public T visit(Const visit);

    public T visit(EqualsExpr visit);

    public T visit(NotExpr visit);

    public T visit(OrExpr visit);

    public T visit(Var visit);

    public T visit(XorExpr visit);
}
