package booleanexpr;

import booleanexpr.expr.AndExpr;
import booleanexpr.expr.EqualsExpr;
import booleanexpr.expr.EvalVisitor;
import booleanexpr.expr.Expr;
import booleanexpr.expr.InfixVisitor;
import booleanexpr.expr.NotExpr;
import booleanexpr.expr.OrExpr;
import booleanexpr.expr.PostfixVisitor;
import booleanexpr.expr.PrefixVisitor;
import booleanexpr.expr.UnknownVarException;
import booleanexpr.expr.Var;
import booleanexpr.expr.VarAssignment;
import booleanexpr.expr.VarExtractVisitor;
import booleanexpr.expr.XorExpr;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static booleanexpr.expr.Const.FALSE;
import static booleanexpr.expr.Const.TRUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BooleanExprTest {

    private static final Var A = new Var("a");
    private static final Var A2 = new Var("a");
    private static final Var B = new Var("b");
    private static final Var VALUE = new Var("value");
    public static final Var X = new Var("x");
    public static final Var Y = new Var("y");
    public static final Var Z = new Var("z");

    public static final AndExpr X_AND_Y = new AndExpr(X, Y);
    public static final AndExpr Y_AND_X = new AndExpr(Y, X);
    public static final AndExpr X_AND_Z = new AndExpr(X, new Var("z"));
    public static final EqualsExpr X_EQUALS_Y = new EqualsExpr(X, Y);
    public static final EqualsExpr Y_EQUALS_X = new EqualsExpr(Y, X);
    public static final EqualsExpr X_EQUALS_Z = new EqualsExpr(X, new Var("z"));
    public static final NotExpr NOT_X = new NotExpr(X);
    public static final NotExpr NOT_Y = new NotExpr(Y);
    public static final NotExpr NOT_Z = new NotExpr(new Var("z"));
    public static final OrExpr X_OR_Y = new OrExpr(X, Y);
    public static final OrExpr Y_OR_X = new OrExpr(Y, X);
    public static final OrExpr X_OR_Z = new OrExpr(X, new Var("z"));
    public static final XorExpr X_XOR_Y = new XorExpr(X, Y);
    public static final XorExpr Y_XOR_X = new XorExpr(Y, X);
    public static final XorExpr X_XOR_Z = new XorExpr(X, new Var("z"));
    public static final AndExpr X_AND_Y_AND_Z = new AndExpr(X_AND_Y, new Var("z"));
    public static final EqualsExpr X_EQUALS_Y_EQUALS_Z = new EqualsExpr(X_EQUALS_Y, new Var("z"));
    public static final NotExpr NOT_NOT_Z = new NotExpr(new NotExpr(new Var("z")));
    public static final OrExpr Z_OR_X_OR_Y = new OrExpr(new Var("z"), new OrExpr(X, Y));
    public static final XorExpr Z_XOR_X_XOR_Y = new XorExpr(new Var("z"), new XorExpr(X, Y));

    public static final InfixVisitor INFIX_VISITOR_JAVA = new InfixVisitor();
    public static final InfixVisitor INFIX_VISITOR_LOGIC = new InfixVisitor("^", "=", "~", "v", "⊕");
    public static final InfixVisitor INFIX_VISITOR_MATH = new InfixVisitor("*", "=", "~", "+", "⊕");
    public static final PrefixVisitor PREFIX_VISITOR_JAVA = new PrefixVisitor();
    public static final PrefixVisitor PREFIX_VISITOR_LOGIC = new PrefixVisitor("^", "=", "~", "v", "⊕");
    public static final PrefixVisitor PREFIX_VISITOR_MATH = new PrefixVisitor("*", "=", "~", "+", "⊕");
    public static final PostfixVisitor POSTFIX_VISITOR_JAVA = new PostfixVisitor();
    public static final PostfixVisitor POSTFIX_VISITOR_LOGIC = new PostfixVisitor("^", "=", "~", "v", "⊕");
    public static final PostfixVisitor POSTFIX_VISITOR_MATH = new PostfixVisitor("*", "=", "~", "+", "⊕");

    public static final String LS = System.lineSeparator();

    public static final String VAR_TABLE = "| x | e1 |" + LS + "+---+----+" + LS + "| F |  F |" + LS + "| T |  T |" + LS + "e1 = x" + LS;

    public static final String EXAMPLE_TABLE = "| x | y | e1 | e2 | e3 | e4 | e5 |" + LS + "+---+---+----+----+----+----+----+" + LS + "| F | F |  F |  T |  T |  F |  F |" + LS + "| F | T |  F |  F |  T |  T |  T |" + LS + "| T | F |  F |  F |  F |  T |  T |" + LS + "| T | T |  T |  T |  F |  T |  F |" + LS + "e1 = (x & y)" + LS + "e2 = (x == y)" + LS + "e3 = !x" + LS + "e4 = (x | y)" + LS + "e5 = (x ^ y)" + LS;

    public static final String MANY_VARS_TABLE = "| ab | cde | x | y | e1 | e2 | e3 | e4 | e5 | e6 | e7 | e8 | e9 | e10 |" + LS + "+----+-----+---+---+----+----+----+----+----+----+----+----+----+-----+" + LS + "|  F |  F  | F | F |  F |  T |  T |  F |  F |  F |  T |  T |  F |  F  |" + LS + "|  F |  F  | F | T |  F |  F |  T |  T |  T |  F |  T |  T |  F |  F  |" + LS + "|  F |  F  | T | F |  F |  F |  F |  T |  T |  F |  T |  T |  F |  F  |" + LS + "|  F |  F  | T | T |  T |  T |  F |  T |  F |  F |  T |  T |  F |  F  |" + LS + "|  F |  T  | F | F |  F |  T |  T |  F |  F |  F |  F |  T |  T |  T  |" + LS + "|  F |  T  | F | T |  F |  F |  T |  T |  T |  F |  F |  T |  T |  T  |" + LS + "|  F |  T  | T | F |  F |  F |  F |  T |  T |  F |  F |  T |  T |  T  |" + LS + "|  F |  T  | T | T |  T |  T |  F |  T |  F |  F |  F |  T |  T |  T  |" + LS + "|  T |  F  | F | F |  F |  T |  T |  F |  F |  F |  F |  F |  T |  T  |" + LS + "|  T |  F  | F | T |  F |  F |  T |  T |  T |  F |  F |  F |  T |  T  |" + LS + "|  T |  F  | T | F |  F |  F |  F |  T |  T |  F |  F |  F |  T |  T  |" + LS + "|  T |  F  | T | T |  T |  T |  F |  T |  F |  F |  F |  F |  T |  T  |" + LS + "|  T |  T  | F | F |  F |  T |  T |  F |  F |  T |  T |  F |  T |  F  |" + LS + "|  T |  T  | F | T |  F |  F |  T |  T |  T |  T |  T |  F |  T |  F  |" + LS + "|  T |  T  | T | F |  F |  F |  F |  T |  T |  T |  T |  F |  T |  F  |" + LS + "|  T |  T  | T | T |  T |  T |  F |  T |  F |  T |  T |  F |  T |  F  |" + LS + "e1 = (x & y)" + LS + "e2 = (x == y)" + LS + "e3 = !x" + LS + "e4 = (x | y)" + LS + "e5 = (x ^ y)" + LS + "e6 = (ab & cde)" + LS + "e7 = (ab == cde)" + LS + "e8 = !ab" + LS + "e9 = (ab | cde)" + LS + "e10 = (ab ^ cde)" + LS;
    private static final String LONG_NAME_TABLE = "| abcdefghijklmnopqrstuvwxyz | e1 |" + LS + "+----------------------------+----+" + LS + "|              F             |  T |" + LS + "|              T             |  F |" + LS + "e1 = !abcdefghijklmnopqrstuvwxyz" + LS;
    private static final String TRUE_AND_FALSE_TABLE = "| e1 |" + LS + "+----+" + LS + "|  F |" + LS + "e1 = (true & false)" + LS;

    // CONST
    @Test
    public void constFalseTest() {
        assertFalse(FALSE.isValue());
    }

    @Test
    public void constTrueTest() {
        assertTrue(TRUE.isValue());
    }

    @Test
    public void constEqualsTest() {
        assertEquals(TRUE, TRUE); assertEquals(FALSE, FALSE); assertNotEquals(TRUE, FALSE);
        assertNotEquals(FALSE, TRUE);
    }

    @Test
    public void constToStringTest() {
        assertEquals("true", TRUE.toString()); assertEquals("false", FALSE.toString());
    }

    // AND

    @Test
    public void andEqualsTest() {
        assertEquals(X_AND_Y, X_AND_Y); assertNotEquals(X_AND_Y, Y_AND_X);
        assertEquals(X_AND_Z, new AndExpr(X, new Var("z")));
    }

    @Test
    public void andToStringTest() {
        assertEquals("(x & y)", X_AND_Y.toString()); assertEquals("(y & x)", Y_AND_X.toString());
    }

    @Test
    public void andGetLeftOperandTest() {
        assertEquals(X, X_AND_Y.getLeftOperand()); assertEquals(Y, Y_AND_X.getLeftOperand());
    }

    @Test
    public void andGetRightOperandTest() {
        assertEquals(Y, X_AND_Y.getRightOperand()); assertEquals(X, Y_AND_X.getRightOperand());
        assertEquals(new Var("z"), X_AND_Z.getRightOperand());
    }

    @Test
    public void andGetOperatorTest() {
        assertEquals("&", X_AND_Y.getOperator()); assertEquals("&", X_AND_Z.getOperator());
    }

    // EQUALS

    @Test
    public void equalsEqualsTest() {
        assertEquals(X_EQUALS_Y, X_EQUALS_Y); assertNotEquals(X_EQUALS_Y, Y_EQUALS_X);
        assertEquals(X_EQUALS_Z, new EqualsExpr(X, new Var("z")));
    }

    @Test
    public void equalsToStringTest() {
        assertEquals("(x == y)", X_EQUALS_Y.toString()); assertEquals("(y == x)", Y_EQUALS_X.toString());
    }

    @Test
    public void equalsGetLeftOperandTest() {
        assertEquals(X, X_EQUALS_Y.getLeftOperand()); assertEquals(Y, Y_EQUALS_X.getLeftOperand());
    }

    @Test
    public void equalsGetRightOperandTest() {
        assertEquals(Y, X_EQUALS_Y.getRightOperand()); assertEquals(X, Y_EQUALS_X.getRightOperand());
        assertEquals(new Var("z"), X_EQUALS_Z.getRightOperand());
    }

    @Test
    public void equalsGetOperatorTest() {
        assertEquals("==", X_EQUALS_Y.getOperator()); assertEquals("==", X_EQUALS_Z.getOperator());
    }

    // NOT

    @Test
    public void notEqualsTest() {
        assertEquals(NOT_X, new NotExpr(X)); assertEquals(NOT_Y, new NotExpr(new Var("y")));
        assertNotEquals(NOT_Z, new NotExpr(new Var("Z")));
    }

    @Test
    public void notToStringTest() {
        assertEquals("!x", NOT_X.toString()); assertEquals("!y", NOT_Y.toString());
        assertEquals("!z", NOT_Z.toString());
    }

    @Test
    public void notGetOperand() {
        assertEquals(X, NOT_X.getOperand()); assertEquals(Y, NOT_Y.getOperand());
        assertEquals(NOT_Z.getOperand(), new Var("z"));
    }

    @Test
    public void notGetOperatorTest() {
        assertEquals("!", NOT_X.getOperator()); assertEquals("!", NOT_Y.getOperator());
    }

    // OR

    @Test
    public void orEqualsTest() {
        assertEquals(X_OR_Y, X_OR_Y); assertNotEquals(X_OR_Y, Y_OR_X);
        assertEquals(X_OR_Z, new OrExpr(X, new Var("z")));
    }

    @Test
    public void orToStringTest() {
        assertEquals("(x | y)", X_OR_Y.toString()); assertEquals("(y | x)", Y_OR_X.toString());
    }

    @Test
    public void orGetLeftOperandTest() {
        assertEquals(X, X_OR_Y.getLeftOperand()); assertEquals(Y, Y_OR_X.getLeftOperand());
    }

    @Test
    public void orGetRightOperandTest() {
        assertEquals(Y, X_OR_Y.getRightOperand()); assertEquals(X, Y_OR_X.getRightOperand());
        assertEquals(new Var("z"), X_OR_Z.getRightOperand());
    }

    @Test
    public void orGetOperatorTest() {
        assertEquals("|", X_OR_Y.getOperator()); assertEquals("|", X_OR_Z.getOperator());
    }

    // XOR

    @Test
    public void xorEqualsTest() {
        assertEquals(X_XOR_Y, X_XOR_Y); assertNotEquals(X_XOR_Y, Y_XOR_X);
        assertEquals(X_XOR_Z, new XorExpr(X, new Var("z")));
    }

    @Test
    public void xorToStringTest() {
        assertEquals("(x ^ y)", X_XOR_Y.toString()); assertEquals("(y ^ x)", Y_XOR_X.toString());
    }

    @Test
    public void xorGetLeftOperandTest() {
        assertEquals(X, X_XOR_Y.getLeftOperand()); assertEquals(Y, Y_XOR_X.getLeftOperand());
    }

    @Test
    public void xorGetRightOperandTest() {
        assertEquals(Y, X_XOR_Y.getRightOperand()); assertEquals(X, Y_XOR_X.getRightOperand());
        assertEquals(new Var("z"), X_XOR_Z.getRightOperand());
    }

    @Test
    public void xorGetOperatorTest() {
        assertEquals("^", X_XOR_Y.getOperator()); assertEquals("^", X_XOR_Z.getOperator());
    }

    // VAR

    @Test
    public void varEqualsTest() {
        assertEquals(A, A); assertNotEquals(A, B); assertEquals(A, A2);
    }

    @Test
    public void varGetNameTest() {
        assertEquals("a", A.getName()); assertEquals("value", VALUE.getName());
    }

    @Test
    public void varToStringTest() {
        assertEquals("a", A.toString()); assertEquals("a", A2.toString()); assertEquals("b", B.toString());
        assertEquals("value", VALUE.toString());
    }

    @Test
    public void varCompareToTest() {
//        assertTrue(new Var("x") instanceof Comparable<Var>); assertTrue(new Var("a").compareTo(new Var("y")) < 0);
        assertTrue(new Var("x").compareTo(new Var("y")) < 0); assertEquals(0, new Var("x").compareTo(new Var("x")));
        assertTrue(new Var("x").compareTo(new Var("a")) > 0); assertTrue(new Var("x").compareTo(new Var("w")) > 0);

        assertTrue(new Var("a").compareTo(new Var("aa")) < 0); assertTrue(new Var("a").compareTo(new Var("ab")) < 0);
        assertTrue(new Var("aA").compareTo(new Var("aa")) < 0); assertTrue(new Var("abc").compareTo(new Var("z")) < 0);
    }

//     INFIX VISITOR

    @Test
    public void infixVisitAndExprTest() {
        assertEquals("(x & y)", X_AND_Y.accept(INFIX_VISITOR_JAVA));
        assertEquals("((x & y) & z)", X_AND_Y_AND_Z.accept(INFIX_VISITOR_JAVA));
    }

    @Test
    public void infixVisitEqualsExprTest() {
        assertEquals("(x == y)", X_EQUALS_Y.accept(INFIX_VISITOR_JAVA));
        assertEquals("((x == y) == z)", X_EQUALS_Y_EQUALS_Z.accept(INFIX_VISITOR_JAVA));
    }

    @Test
    public void infixVisitNotExprTest() {
        assertEquals("!x", NOT_X.accept(INFIX_VISITOR_JAVA)); assertEquals("!!z", NOT_NOT_Z.accept(INFIX_VISITOR_JAVA));
    }

    @Test
    public void infixVisitOrExprTest() {
        assertEquals("(x | y)", X_OR_Y.accept(INFIX_VISITOR_JAVA));
        assertEquals("(z | (x | y))", Z_OR_X_OR_Y.accept(INFIX_VISITOR_JAVA));
    }

    @Test
    public void infixVisitXorExprTest() {
        assertEquals("(x ^ y)", X_XOR_Y.accept(INFIX_VISITOR_JAVA));
        assertEquals("(z ^ (x ^ y))", Z_XOR_X_XOR_Y.accept(INFIX_VISITOR_JAVA));
    }

    @Test
    public void infixVisitConstExprTest() {
        assertEquals("false", FALSE.accept(INFIX_VISITOR_JAVA)); assertEquals("true", TRUE.accept(INFIX_VISITOR_JAVA));
    }

    @Test
    public void infixVisitExprWithConstJavaTest() {
        assertEquals("(false & true)", new AndExpr(FALSE, TRUE).accept(INFIX_VISITOR_JAVA));
        assertEquals("(false == true)", new EqualsExpr(FALSE, TRUE).accept(INFIX_VISITOR_JAVA));
        assertEquals("(false | true)", new OrExpr(FALSE, TRUE).accept(INFIX_VISITOR_JAVA));
        assertEquals("(false ^ true)", new XorExpr(FALSE, TRUE).accept(INFIX_VISITOR_JAVA));
        assertEquals("!true", new NotExpr(TRUE).accept(INFIX_VISITOR_JAVA));
    }

    @Test
    public void infixVisitExprWithConstLogicTest() {
        assertEquals("(false ^ true)", new AndExpr(FALSE, TRUE).accept(INFIX_VISITOR_LOGIC));
        assertEquals("(false = true)", new EqualsExpr(FALSE, TRUE).accept(INFIX_VISITOR_LOGIC));
        assertEquals("(false v true)", new OrExpr(FALSE, TRUE).accept(INFIX_VISITOR_LOGIC));
        assertEquals("(false ⊕ true)", new XorExpr(FALSE, TRUE).accept(INFIX_VISITOR_LOGIC));
        assertEquals("~true", new NotExpr(TRUE).accept(INFIX_VISITOR_LOGIC));
    }

    @Test
    public void infixVisitExprWithConstMathTest() {
        assertEquals("(false * true)", new AndExpr(FALSE, TRUE).accept(INFIX_VISITOR_MATH));
        assertEquals("(false = true)", new EqualsExpr(FALSE, TRUE).accept(INFIX_VISITOR_MATH));
        assertEquals("(false + true)", new OrExpr(FALSE, TRUE).accept(INFIX_VISITOR_MATH));
        assertEquals("(false ⊕ true)", new XorExpr(FALSE, TRUE).accept(INFIX_VISITOR_MATH));
        assertEquals("~true", new NotExpr(TRUE).accept(INFIX_VISITOR_MATH));
    }

//    // PREFIX VISITOR
//
//    @Test
//    public void prefixVisitAndExprTest() {
//        assertEquals("& x y", X_AND_Y.accept(PREFIX_VISITOR_JAVA));
//        assertEquals("& & x y z", X_AND_Y_AND_Z.accept(PREFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void prefixVisitEqualsExprTest() {
//        assertEquals("== x y", X_EQUALS_Y.accept(PREFIX_VISITOR_JAVA));
//        assertEquals("== == x y z", X_EQUALS_Y_EQUALS_Z.accept(PREFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void prefixVisitNotExprTest() {
//        assertEquals("! x", NOT_X.accept(PREFIX_VISITOR_JAVA));
//        assertEquals("! ! z", NOT_NOT_Z.accept(PREFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void prefixVisitOrExprTest() {
//        assertEquals("| x y", X_OR_Y.accept(PREFIX_VISITOR_JAVA));
//        assertEquals("| z | x y", Z_OR_X_OR_Y.accept(PREFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void prefixVisitXorExprTest() {
//        assertEquals("^ x y", X_XOR_Y.accept(PREFIX_VISITOR_JAVA));
//        assertEquals("^ z ^ x y", Z_XOR_X_XOR_Y.accept(PREFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void prefixVisitConstExprTest() {
//        assertEquals("false", FALSE.accept(PREFIX_VISITOR_JAVA));
//        assertEquals("true", TRUE.accept(PREFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void prefixVisitExprWithConstJavaTest() {
//        assertEquals("& false true", new AndExpr(FALSE, TRUE).accept(PREFIX_VISITOR_JAVA));
//        assertEquals("== false true", new EqualsExpr(FALSE, TRUE).accept(PREFIX_VISITOR_JAVA));
//        assertEquals("| false true", new OrExpr(FALSE, TRUE).accept(PREFIX_VISITOR_JAVA));
//        assertEquals("^ false true", new XorExpr(FALSE, TRUE).accept(PREFIX_VISITOR_JAVA));
//        assertEquals("! true", new NotExpr(TRUE).accept(PREFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void prefixVisitExprWithConstLogicTest() {
//        assertEquals("^ false true", new AndExpr(FALSE, TRUE).accept(PREFIX_VISITOR_LOGIC));
//        assertEquals("= false true", new EqualsExpr(FALSE, TRUE).accept(PREFIX_VISITOR_LOGIC));
//        assertEquals("v false true", new OrExpr(FALSE, TRUE).accept(PREFIX_VISITOR_LOGIC));
//        assertEquals("⊕ false true", new XorExpr(FALSE, TRUE).accept(PREFIX_VISITOR_LOGIC));
//        assertEquals("~ true", new NotExpr(TRUE).accept(PREFIX_VISITOR_LOGIC));
//    }
//
//    @Test
//    public void prefixVisitExprWithConstMathTest() {
//        assertEquals("* false true", new AndExpr(FALSE, TRUE).accept(PREFIX_VISITOR_MATH));
//        assertEquals("= false true", new EqualsExpr(FALSE, TRUE).accept(PREFIX_VISITOR_MATH));
//        assertEquals("+ false true", new OrExpr(FALSE, TRUE).accept(PREFIX_VISITOR_MATH));
//        assertEquals("⊕ false true", new XorExpr(FALSE, TRUE).accept(PREFIX_VISITOR_MATH));
//        assertEquals("~ true", new NotExpr(TRUE).accept(PREFIX_VISITOR_MATH));
//    }
//
//    // POSTFIX VISITOR
//
//    @Test
//    public void postfixVisitAndExprTest() {
//        assertEquals("x y &", X_AND_Y.accept(POSTFIX_VISITOR_JAVA));
//        assertEquals("x y & z &", X_AND_Y_AND_Z.accept(POSTFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void postfixVisitEqualsExprTest() {
//        assertEquals("x y ==", X_EQUALS_Y.accept(POSTFIX_VISITOR_JAVA));
//        assertEquals("x y == z ==", X_EQUALS_Y_EQUALS_Z.accept(POSTFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void postfixVisitNotExprTest() {
//        assertEquals("x !", NOT_X.accept(POSTFIX_VISITOR_JAVA));
//        assertEquals("z ! !", NOT_NOT_Z.accept(POSTFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void postfixVisitOrExprTest() {
//        assertEquals("x y |", X_OR_Y.accept(POSTFIX_VISITOR_JAVA));
//        assertEquals("z x y | |", Z_OR_X_OR_Y.accept(POSTFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void postfixVisitXorExprTest() {
//        assertEquals("x y ^", X_XOR_Y.accept(POSTFIX_VISITOR_JAVA));
//        assertEquals("z x y ^ ^", Z_XOR_X_XOR_Y.accept(POSTFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void postfixVisitConstExprTest() {
//        assertEquals("false", FALSE.accept(POSTFIX_VISITOR_JAVA));
//        assertEquals("true", TRUE.accept(POSTFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void postfixVisitExprWithConstJavaTest() {
//        assertEquals("false true &", new AndExpr(FALSE, TRUE).accept(POSTFIX_VISITOR_JAVA));
//        assertEquals("false true ==", new EqualsExpr(FALSE, TRUE).accept(POSTFIX_VISITOR_JAVA));
//        assertEquals("false true |", new OrExpr(FALSE, TRUE).accept(POSTFIX_VISITOR_JAVA));
//        assertEquals("false true ^", new XorExpr(FALSE, TRUE).accept(POSTFIX_VISITOR_JAVA));
//        assertEquals("true !", new NotExpr(TRUE).accept(POSTFIX_VISITOR_JAVA));
//    }
//
//    @Test
//    public void postfixVisitExprWithConstLogicTest() {
//        assertEquals("false true ^", new AndExpr(FALSE, TRUE).accept(POSTFIX_VISITOR_LOGIC));
//        assertEquals("false true =", new EqualsExpr(FALSE, TRUE).accept(POSTFIX_VISITOR_LOGIC));
//        assertEquals("false true v", new OrExpr(FALSE, TRUE).accept(POSTFIX_VISITOR_LOGIC));
//        assertEquals("false true ⊕", new XorExpr(FALSE, TRUE).accept(POSTFIX_VISITOR_LOGIC));
//        assertEquals("true ~", new NotExpr(TRUE).accept(POSTFIX_VISITOR_LOGIC));
//    }
//
//    @Test
//    public void postfixVisitExprWithConstMathTest() {
//        assertEquals("false true *", new AndExpr(FALSE, TRUE).accept(POSTFIX_VISITOR_MATH));
//        assertEquals("false true =", new EqualsExpr(FALSE, TRUE).accept(POSTFIX_VISITOR_MATH));
//        assertEquals("false true +", new OrExpr(FALSE, TRUE).accept(POSTFIX_VISITOR_MATH));
//        assertEquals("false true ⊕", new XorExpr(FALSE, TRUE).accept(POSTFIX_VISITOR_MATH));
//        assertEquals("true ~", new NotExpr(TRUE).accept(POSTFIX_VISITOR_MATH));
//    }
//
//    // VAR ASSIGNMENT
//
//    @Test
//    public void varAssignmentInputTest() {
//        final VarAssignment varAssignment = new VarAssignment(); List<Var> vars = varAssignment.getVars();
//        assertEquals(0, vars.size()); assertTrue(vars.isEmpty());
//
//        assertFalse(varAssignment.isAssigned(X)); assertFalse(varAssignment.isAssigned(Y));
//        assertFalse(varAssignment.isAssigned(Z));
//
//        varAssignment.setVar(Y, true); vars = varAssignment.getVars(); assertEquals(1, vars.size());
//        assertEquals(List.of(Y), vars); assertTrue(varAssignment.getAssignment(Y));
//
//        assertFalse(varAssignment.isAssigned(X)); assertTrue(varAssignment.isAssigned(Y));
//        assertFalse(varAssignment.isAssigned(Z));
//
//        varAssignment.setVar(Z, false); vars = varAssignment.getVars(); assertEquals(2, vars.size());
//        assertEquals(List.of(Y, Z), vars); assertFalse(varAssignment.getAssignment(Z));
//
//        assertFalse(varAssignment.isAssigned(X)); assertTrue(varAssignment.isAssigned(Y));
//        assertTrue(varAssignment.isAssigned(Z));
//
//        varAssignment.setVar(X, true); vars = varAssignment.getVars(); assertEquals(3, vars.size());
//        assertEquals(List.of(X, Y, Z), vars); assertTrue(varAssignment.getAssignment(X));
//
//        assertTrue(varAssignment.isAssigned(X)); assertTrue(varAssignment.isAssigned(Y));
//        assertTrue(varAssignment.isAssigned(Z));
//
//        varAssignment.setVar(X, false); vars = varAssignment.getVars(); assertEquals(3, vars.size());
//        assertEquals(List.of(X, Y, Z), vars); assertFalse(varAssignment.getAssignment(X));
//
//        assertTrue(varAssignment.isAssigned(X)); assertTrue(varAssignment.isAssigned(Y));
//        assertTrue(varAssignment.isAssigned(Z));
//    }
//
//    @Test
//    public void varAssignmentIteratorTest() {
//        VarAssignment varAssignment = new VarAssignment(); varAssignment.setVar(X, true);
//        varAssignment.setVar(Y, false); Iterator<VarAssignment> it = varAssignment.iterator(); assertTrue(it.hasNext());
//        VarAssignment va1 = it.next(); assertNotNull(va1); assertFalse(va1.getAssignment(X));
//        assertFalse(va1.getAssignment(Y)); assertTrue(it.hasNext()); VarAssignment va2 = it.next(); assertNotNull(va2);
//        assertFalse(va2.getAssignment(X)); assertTrue(va2.getAssignment(Y)); assertTrue(it.hasNext());
//        VarAssignment va3 = it.next(); assertNotNull(va3); assertTrue(va3.getAssignment(X));
//        assertFalse(va3.getAssignment(Y)); assertTrue(it.hasNext()); VarAssignment va4 = it.next(); assertNotNull(va4);
//        assertTrue(va4.getAssignment(X)); assertTrue(va4.getAssignment(Y)); assertFalse(it.hasNext());
//    }
//
//    // EVAL VISITOR
//
//    @Test
//    public void evalConstExprTest() {
//        EvalVisitor evalVisitor = new EvalVisitor(); assertTrue(TRUE.accept(evalVisitor));
//        assertFalse(FALSE.accept(evalVisitor));
//    }
//
//    @Test
//    public void evalVarTest() {
//        EvalVisitor evalVisitor = new EvalVisitor(); evalVisitor.setVar(new Var("f"), false);
//        evalVisitor.setVar(new Var("t"), true); evalVisitor.setVar(X, true); evalVisitor.setVar(Y, false);
//        assertFalse(evalVisitor.getVar(new Var("f"))); assertTrue(evalVisitor.getVar(new Var("t")));
//
//        assertFalse(new Var("f").accept(evalVisitor)); assertTrue(new Var("t").accept(evalVisitor));
//        assertTrue(X.accept(evalVisitor)); assertFalse(Y.accept(evalVisitor));
//    }
//
//    @Test(expected = UnknownVarException.class)
//    public void evalUnknownVarTest() {
//        EvalVisitor evalVisitor = new EvalVisitor(); X.accept(evalVisitor);
//    }
//
//    @Test
//    public void evalAndTest() {
//        EvalVisitor evalVisitor = new EvalVisitor(); assertFalse(new AndExpr(FALSE, FALSE).accept(evalVisitor));
//        assertFalse(new AndExpr(FALSE, TRUE).accept(evalVisitor));
//        assertFalse(new AndExpr(TRUE, FALSE).accept(evalVisitor));
//        assertTrue(new AndExpr(TRUE, TRUE).accept(evalVisitor));
//    }
//
//    @Test
//    public void evalEqualsTest() {
//        EvalVisitor evalVisitor = new EvalVisitor(); assertTrue(new EqualsExpr(FALSE, FALSE).accept(evalVisitor));
//        assertFalse(new EqualsExpr(FALSE, TRUE).accept(evalVisitor));
//        assertFalse(new EqualsExpr(TRUE, FALSE).accept(evalVisitor));
//        assertTrue(new EqualsExpr(TRUE, TRUE).accept(evalVisitor));
//    }
//
//    @Test
//    public void evalOrTest() {
//        EvalVisitor evalVisitor = new EvalVisitor(); assertFalse(new OrExpr(FALSE, FALSE).accept(evalVisitor));
//        assertTrue(new OrExpr(FALSE, TRUE).accept(evalVisitor));
//        assertTrue(new OrExpr(TRUE, FALSE).accept(evalVisitor)); assertTrue(new OrExpr(TRUE, TRUE).accept(evalVisitor));
//    }
//
//    @Test
//    public void evalXorTest() {
//        EvalVisitor evalVisitor = new EvalVisitor(); assertFalse(new XorExpr(FALSE, FALSE).accept(evalVisitor));
//        assertTrue(new XorExpr(FALSE, TRUE).accept(evalVisitor));
//        assertTrue(new XorExpr(TRUE, FALSE).accept(evalVisitor));
//        assertFalse(new XorExpr(TRUE, TRUE).accept(evalVisitor));
//    }
//
//    @Test
//    public void evalNotTest() {
//        EvalVisitor evalVisitor = new EvalVisitor(); assertFalse(new NotExpr(TRUE).accept(evalVisitor));
//        assertTrue(new NotExpr(FALSE).accept(evalVisitor));
//    }
//
//    @Test
//    public void evalExprVarTest() {
//        EvalVisitor evalVisitor = new EvalVisitor();
//
//        evalVisitor.setVar(X, false); evalVisitor.setVar(Y, false); assertTrue(new NotExpr(X).accept(evalVisitor));
//        assertFalse(new AndExpr(X, Y).accept(evalVisitor)); assertTrue(new EqualsExpr(X, Y).accept(evalVisitor));
//        assertFalse(new OrExpr(X, Y).accept(evalVisitor)); assertFalse(new XorExpr(X, Y).accept(evalVisitor));
//
//        evalVisitor.setVar(Y, true); assertFalse(new AndExpr(X, Y).accept(evalVisitor));
//        assertFalse(new EqualsExpr(X, Y).accept(evalVisitor)); assertTrue(new OrExpr(X, Y).accept(evalVisitor));
//        assertTrue(new XorExpr(X, Y).accept(evalVisitor));
//
//        evalVisitor.setVar(X, true); evalVisitor.setVar(Y, false); assertFalse(new NotExpr(X).accept(evalVisitor));
//        assertFalse(new AndExpr(X, Y).accept(evalVisitor)); assertFalse(new EqualsExpr(X, Y).accept(evalVisitor));
//        assertTrue(new OrExpr(X, Y).accept(evalVisitor)); assertTrue(new XorExpr(X, Y).accept(evalVisitor));
//
//        evalVisitor.setVar(Y, true); assertTrue(new AndExpr(X, Y).accept(evalVisitor));
//        assertTrue(new EqualsExpr(X, Y).accept(evalVisitor)); assertTrue(new OrExpr(X, Y).accept(evalVisitor));
//        assertFalse(new XorExpr(X, Y).accept(evalVisitor));
//    }
//
//    // VAREXTRACT VISITOR
//
//    @Test
//    public void nothingToExtractTest() {
//        VarExtractVisitor varExtractVisitor = new VarExtractVisitor();
//        assertTrue(varExtractVisitor.getVars().isEmpty()); TRUE.accept(varExtractVisitor);
//        assertTrue(varExtractVisitor.getVars().isEmpty()); FALSE.accept(varExtractVisitor);
//        assertTrue(varExtractVisitor.getVars().isEmpty()); new AndExpr(TRUE, TRUE).accept(varExtractVisitor);
//        new EqualsExpr(TRUE, TRUE).accept(varExtractVisitor); new OrExpr(TRUE, TRUE).accept(varExtractVisitor);
//        new XorExpr(TRUE, TRUE).accept(varExtractVisitor); new NotExpr(TRUE).accept(varExtractVisitor);
//        assertTrue(varExtractVisitor.getVars().isEmpty());
//    }
//
//    @Test
//    public void simpleVarExtractTest() {
//        final Var x = new Var("x"); final Var y = new Var("y");
//        VarExtractVisitor varExtractVisitor = new VarExtractVisitor();
//        assertTrue(varExtractVisitor.getVars().isEmpty()); x.accept(varExtractVisitor);
//        assertEquals(1, varExtractVisitor.getVars().size()); assertEquals(Set.of(x), varExtractVisitor.getVars());
//        x.accept(varExtractVisitor); assertEquals(1, varExtractVisitor.getVars().size());
//        assertEquals(Set.of(x), varExtractVisitor.getVars()); y.accept(varExtractVisitor);
//        assertEquals(2, varExtractVisitor.getVars().size()); assertEquals(Set.of(x, y), varExtractVisitor.getVars());
//        y.accept(varExtractVisitor); assertEquals(2, varExtractVisitor.getVars().size());
//        assertEquals(Set.of(x, y), varExtractVisitor.getVars());
//    }
//
//    @Test
//    public void complexVarExtractTest() {
//        final Var a = new Var("a"); final Var b = new Var("b"); final Var c = new Var("c"); final Var d = new Var("d");
//        Expr e = new NotExpr(new AndExpr(new OrExpr(new EqualsExpr(a, b), c), new XorExpr(d, TRUE)));
//        VarExtractVisitor varExtractVisitor = new VarExtractVisitor();
//        assertTrue(varExtractVisitor.getVars().isEmpty()); e.accept(varExtractVisitor);
//        assertEquals(4, varExtractVisitor.getVars().size());
//        assertEquals(Set.of(a, b, c, d), varExtractVisitor.getVars());
//    }
//
//    // TRUTHTABLE
//
//    @Test
//    public void noVarTableTest() {
//        final TruthTable truthTable = new TruthTable(); truthTable.addExpr(new AndExpr(TRUE, FALSE));
//        assertEquals(TRUE_AND_FALSE_TABLE, truthTable.toString());
//    }
//
//    @Test
//    public void varTableTest() {
//        final TruthTable truthTable = new TruthTable(); truthTable.addExpr(new Var("x"));
//        assertEquals(VAR_TABLE, truthTable.toString());
//    }
//
//    @Test
//    public void exampleTest() {
//        final TruthTable truthTable = new TruthTable(); final Var x = new Var("x"); final Var y = new Var("y");
//        truthTable.addExpr(new AndExpr(x, y)); truthTable.addExpr(new EqualsExpr(x, y));
//        truthTable.addExpr(new NotExpr(x)); truthTable.addExpr(new OrExpr(x, y)); truthTable.addExpr(new XorExpr(x, y));
//        assertEquals(EXAMPLE_TABLE, truthTable.toString());
//    }
//
//    @Test
//    public void manyExprTest() {
//        final TruthTable truthTable = new TruthTable(); final Var x = new Var("x"); final Var y = new Var("y");
//        truthTable.addExpr(new AndExpr(x, y)); assertEquals(Set.of(x, y), truthTable.getVars());
//        truthTable.addExpr(new EqualsExpr(x, y)); truthTable.addExpr(new NotExpr(x));
//        truthTable.addExpr(new OrExpr(x, y)); truthTable.addExpr(new XorExpr(x, y));
//
//        final Var ab = new Var("ab"); final Var cde = new Var("cde"); truthTable.addExpr(new AndExpr(ab, cde));
//        assertEquals(Set.of(x, y, ab, cde), truthTable.getVars()); truthTable.addExpr(new EqualsExpr(ab, cde));
//        truthTable.addExpr(new NotExpr(ab)); truthTable.addExpr(new OrExpr(ab, cde));
//        truthTable.addExpr(new XorExpr(ab, cde)); assertEquals(MANY_VARS_TABLE, truthTable.toString());
//
//        assertEquals(Set.of(x, y, ab, cde), truthTable.getVars());
//    }
//
//    @Test
//    public void longNameTest() {
//        final TruthTable truthTable = new TruthTable(); final Var longNameVar = new Var("abcdefghijklmnopqrstuvwxyz");
//        truthTable.addExpr(new NotExpr(longNameVar)); assertEquals(LONG_NAME_TABLE, truthTable.toString());
//    }
//
//    // NORMAL FORM
//
//    @Test
//    public void normalFormAndAssignmentsTest() {
//        NormalForm nf1 = new DummyNormalForm(X_AND_Y); VarAssignment a1 = new VarAssignment(); a1.setVar(X, true);
//        a1.setVar(Y, true); assertEquals(List.of(a1), nf1.getTrueAssignments()); VarAssignment a2 = new VarAssignment();
//        a2.setVar(X, false); a2.setVar(Y, false); VarAssignment a3 = new VarAssignment(); a3.setVar(X, false);
//        a3.setVar(Y, true); VarAssignment a4 = new VarAssignment(); a4.setVar(X, true); a4.setVar(Y, false);
//        assertEquals(List.of(a2, a3, a4), nf1.getFalseAssignments());
//    }
//
//    @Test
//    public void normalFormOrAssignmentsTest() {
//        NormalForm nf1 = new DummyNormalForm(X_OR_Y); VarAssignment a1 = new VarAssignment(); a1.setVar(X, false);
//        a1.setVar(Y, false); assertEquals(List.of(a1), nf1.getFalseAssignments());
//        VarAssignment a2 = new VarAssignment(); a2.setVar(X, false); a2.setVar(Y, true);
//        VarAssignment a3 = new VarAssignment(); a3.setVar(X, true); a3.setVar(Y, false);
//        VarAssignment a4 = new VarAssignment(); a4.setVar(X, true); a4.setVar(Y, true);
//        assertEquals(List.of(a2, a3, a4), nf1.getTrueAssignments());
//    }
//
//    @Test
//    public void disjunctiveNormalFormTest() {
//        List<Expr> exprList = List.of(new AndExpr(new Var("x"), new Var("y")), new AndExpr(new AndExpr(new Var("x"), new Var("y")), new Var("z")), new AndExpr(new Var("x"), new AndExpr(new Var("y"), new Var("z"))), new OrExpr(new Var("x"), new Var("y")), new OrExpr(new OrExpr(new Var("x"), new Var("y")), new Var("z")), new OrExpr(new Var("x"), new OrExpr(new Var("y"), new Var("z"))));
//
//        List<Expr> dnfList = List.of(new AndExpr(new Var("x"), new Var("y")), new AndExpr(new AndExpr(new Var("x"), new Var("y")), new Var("z")), new AndExpr(new AndExpr(new Var("x"), new Var("y")), new Var("z")), new OrExpr(new OrExpr(new AndExpr(new NotExpr(new Var("x")), new Var("y")), new AndExpr(new Var("x"), new NotExpr(new Var("y")))), new AndExpr(new Var("x"), new Var("y"))), new OrExpr(new OrExpr(new OrExpr(new OrExpr(new OrExpr(new OrExpr(new AndExpr(new AndExpr(new NotExpr(new Var("x")), new NotExpr(new Var("y"))), new Var("z")), new AndExpr(new AndExpr(new NotExpr(new Var("x")), new Var("y")), new NotExpr(new Var("z")))), new AndExpr(new AndExpr(new NotExpr(new Var("x")), new Var("y")), new Var("z"))), new AndExpr(new AndExpr(new Var("x"), new NotExpr(new Var("y"))), new NotExpr(new Var("z")))), new AndExpr(new AndExpr(new Var("x"), new NotExpr(new Var("y"))), new Var("z"))), new AndExpr(new AndExpr(new Var("x"), new Var("y")), new NotExpr(new Var("z")))), new AndExpr(new AndExpr(new Var("x"), new Var("y")), new Var("z"))), new OrExpr(new OrExpr(new OrExpr(new OrExpr(new OrExpr(new OrExpr(new AndExpr(new AndExpr(new NotExpr(new Var("x")), new NotExpr(new Var("y"))), new Var("z")), new AndExpr(new AndExpr(new NotExpr(new Var("x")), new Var("y")), new NotExpr(new Var("z")))), new AndExpr(new AndExpr(new NotExpr(new Var("x")), new Var("y")), new Var("z"))), new AndExpr(new AndExpr(new Var("x"), new NotExpr(new Var("y"))), new NotExpr(new Var("z")))), new AndExpr(new AndExpr(new Var("x"), new NotExpr(new Var("y"))), new Var("z"))), new AndExpr(new AndExpr(new Var("x"), new Var("y")), new NotExpr(new Var("z")))), new AndExpr(new AndExpr(new Var("x"), new Var("y")), new Var("z"))));
//
//        for (int i = 0; i < exprList.size(); ++i)
//            assertEquals(dnfList.get(i), new DisjunctiveNormalForm(exprList.get(i)).normalize());
//    }
}
