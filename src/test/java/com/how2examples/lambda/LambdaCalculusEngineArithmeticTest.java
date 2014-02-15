package com.how2examples.lambda;

import static com.how2examples.lambda.util.EvaluationOutcomeTestUtils.assertNormalOrderOutcome;
import static com.how2examples.lambda.util.EvaluationStepsTestUtils.assertNormalOrderSteps;
import junit.framework.TestCase;

public class LambdaCalculusEngineArithmeticTest extends TestCase {
   public void testIsZero() {
      final String input = "(iszero zero)";
      assertNormalOrderSteps(input, "(\\n.(n true) zero)", "(zero true)", "(\\x.x true)", "true");
   }

   public void testIsNotZero() {
      final String input = "(iszero three)";
      assertNormalOrderSteps(input, "(\\n.(n true) three)", "(three true)", "((succ two) true)", "((\\n.\\s.((s false) n) two) true)", "(\\s.((s false) two) true)", "((true false) two)", "((\\x.\\y.x false) two)", "(\\y.false two)", "false");
   }

   public void testSucc() {
      assertOutcome("(succ zero)", "\\s.((s false) zero)");
   }

   public void testPred() {
      assertOutcome("(pred one)", "zero");
      assertOutcome("(pred three)", "two");
      assertOutcome("(pred zero)", "zero");
      assertOutcome("(pred (pred two))", "zero");
   }

   public void testAdd() {
      assertOutcome("((+ zero) zero)", "zero");
      assertOutcome("((+ one) zero)", "one");
      assertOutcome("((+ one) one)", "\\s.((s false) one)");
      assertOutcome("((+ one) two)", "\\s.((s false) (succ one))");
   }

   public void testMultiply() {
      assertOutcome("((* zero) zero)", "zero");
      assertOutcome("((* one) zero)", "zero");
      assertOutcome("((* one) one)", "one");
      assertOutcome("((* two) two)", "\\s.((s false) (succ two))");
   }

   public void testSubtraction() {
      assertOutcome("((- four) two)", "two");
      assertOutcome("((- five) two)", "three");
      assertOutcome("((- four) four)", "zero");
      assertOutcome("((- one) zero)", "one");
      assertOutcome("((- one) two)", "zero");
   }

   public void testEqual() {
      assertOutcome("((= zero) zero)", "true");
      assertOutcome("((= five) five)", "true");
      assertOutcome("((= four) five)", "false");
   }

   public void testDiff() {
      assertOutcome("((diff two) one)", "one");
      assertOutcome("((diff one) two)", "\\s.((s false) ((- one) two))");
      assertOutcome("((diff two) three)", "\\s.((s false) ((- two) three))");
      assertOutcome("((diff one) four)", "\\s.((s false) (succ (succ ((- one) four))))");
   }

   public void testGreater() {
      assertOutcome("((> one) zero)", "true");
      assertOutcome("((> one) one)", "false");
      assertOutcome("((> one) two)", "false");
      assertOutcome("((> one) five)", "false");
      assertOutcome("((> five) one)", "true");
   }

   public void testDivide() {
      assertOutcome("((/ two) four)", "zero");
      assertOutcome("((/ four) two)", "\\s.((s false) (((\\s.(divide (s s)) \\s.(divide (s s))) ((- four) two)) two))");
      assertOutcome("((/ five) one)", "\\s.((s false) (((\\s.(divide (s s)) \\s.(divide (s s))) ((- five) one)) one))");
      assertOutcome("((/ five) five)", "\\s.((s false) (((\\s.(divide (s s)) \\s.(divide (s s))) ((- five) five)) five))");
   }

   private void assertOutcome(String input, String expected) {
      assertNormalOrderOutcome(input, expected);
   }
}
