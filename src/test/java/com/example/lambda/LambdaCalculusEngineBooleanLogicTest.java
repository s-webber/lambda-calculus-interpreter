package com.example.lambda;

import static com.example.lambda.util.EvaluationOutcomeTestUtils.assertNormalOrderOutcome;
import static com.example.lambda.util.EvaluationStepsTestUtils.assertNormalOrderSteps;
import junit.framework.TestCase;

public class LambdaCalculusEngineBooleanLogicTest extends TestCase {
   public void testAnd() {
      assertNormalOrderSteps("((and true) true)", "((\\p.\\q.((p q) p) true) true)", "(\\q.((true q) true) true)", "((true true) true)", "((\\x.\\y.x true) true)", "(\\y.true true)", "true");
      assertNormalOrderSteps("((and true) false)", "((\\p.\\q.((p q) p) true) false)", "(\\q.((true q) true) false)", "((true false) true)", "((\\x.\\y.x false) true)", "(\\y.false true)", "false");
      assertNormalOrderSteps("((and false) true)", "((\\p.\\q.((p q) p) false) true)", "(\\q.((false q) false) true)", "((false true) false)", "((\\x.\\y.y true) false)", "(\\y.y false)", "false");
      assertNormalOrderSteps("((and false) false)", "((\\p.\\q.((p q) p) false) false)", "(\\q.((false q) false) false)", "((false false) false)", "((\\x.\\y.y false) false)", "(\\y.y false)", "false");
   }

   public void testOr() {
      assertNormalOrderSteps("((or true) true)", "((\\p.\\q.((p p) q) true) true)", "(\\q.((true true) q) true)", "((true true) true)", "((\\x.\\y.x true) true)", "(\\y.true true)", "true");
      assertNormalOrderSteps("((or true) false)", "((\\p.\\q.((p p) q) true) false)", "(\\q.((true true) q) false)", "((true true) false)", "((\\x.\\y.x true) false)", "(\\y.true false)", "true");
      assertNormalOrderSteps("((or false) true)", "((\\p.\\q.((p p) q) false) true)", "(\\q.((false false) q) true)", "((false false) true)", "((\\x.\\y.y false) true)", "(\\y.y true)", "true");
      assertNormalOrderSteps("((or false) false)", "((\\p.\\q.((p p) q) false) false)", "(\\q.((false false) q) false)", "((false false) false)", "((\\x.\\y.y false) false)", "(\\y.y false)", "false");
   }

   public void testNot() {
      assertNormalOrderSteps("(not false)", "(\\x.((x false) true) false)", "((false false) true)", "((\\x.\\y.y false) true)", "(\\y.y true)", "true");
      assertNormalOrderSteps("(not true)", "(\\x.((x false) true) true)", "((true false) true)", "((\\x.\\y.x false) true)", "(\\y.false true)", "false");
   }

   public void testIfThenElse() {
      assertNormalOrderSteps("(((cond a) b) true)", "(((\\e1.\\e2.\\c.((c e1) e2) a) b) true)", "((\\e2.\\c.((c a) e2) b) true)", "(\\c.((c a) b) true)", "((true a) b)", "((\\x.\\y.x a) b)", "(\\y.a b)", "a");
      assertNormalOrderSteps("(((cond a) b) false)", "(((\\e1.\\e2.\\c.((c e1) e2) a) b) false)", "((\\e2.\\c.((c a) e2) b) false)", "(\\c.((c a) b) false)", "((false a) b)", "((\\x.\\y.y a) b)", "(\\y.y b)", "b");
   }

   public void testCombinedBooleanLogic() {
      assertOutcomeTrue("(not (not true))");
      assertOutcomeFalse("(not (not false))");
      assertOutcomeTrue("(not (not ((or true) false)))");
      assertOutcomeFalse("(not (not ((and true) false)))");
      assertOutcomeTrue("(not ((and (not true)) ((and ((or true) false)) ((or false) true))))");
      assertOutcomeFalse("(not ((and (not false)) ((or ((and true) true)) ((and false) false))))");
   }

   private void assertOutcomeTrue(String input) {
      assertNormalOrderOutcome(input, "true");
   }

   private void assertOutcomeFalse(String input) {
      assertNormalOrderOutcome(input, "false");
   }
}
