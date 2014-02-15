package com.how2examples.lambda;

import static com.how2examples.lambda.util.EvaluationStepsTestUtils.assertEvaluationSteps;
import static com.how2examples.lambda.util.LambdaCalculusEngineTestUtils.createNormalOrderEngine;
import junit.framework.TestCase;

import com.how2examples.lambda.util.EvaluationStepsExpectation;

public class LambdaCalculusEngineTest extends TestCase {
   /** \x.x - identity, returns argument */
   public void testIdentity() {
      assertEvaluationSteps("(\\x.x y)", "y");
      assertEvaluationSteps("((\\x.x \\x.x) y)", "(\\x.x y)", "y");
      assertEvaluationSteps("(\\x.x \\x.x)", "\\x.x");
      assertEvaluationSteps("((\\x.x \\x.x) \\x.x)", "(\\x.x \\x.x)", "\\x.x");
      assertEvaluationSteps("(\\x.x \\s.(s s))", "\\s.(s s)");
   }

   /** \s.(s s) - self application, applies its argument to its argument */
   public void testSelfApplication() {
      assertEvaluationSteps("(\\s.(s s) \\x.x)", "(\\x.x \\x.x)", "\\x.x");
   }

   /** \f.\a.(f a) - function application, applies first argument to second */
   public void testFunctionApplication() {
      assertEvaluationSteps("((\\f.\\a.(f a) \\x.x) \\s.(s s))", "(\\a.(\\x.x a) \\s.(s s))", "(\\x.x \\s.(s s))", "\\s.(s s)");
   }

   public void testBoundVariable() {
      assertEvaluationSteps("(\\f.(f \\f.f) \\s.(s s))", "(\\s.(s s) \\f.f)", "(\\f.f \\f.f)", "\\f.f");
   }

   public void testSubstitution() {
      EvaluationStepsExpectation e = new EvaluationStepsExpectation("((identity (identity identity)) ((identity identity) identity))");
      e.setNormalOrderSteps("((\\x.x (identity identity)) ((identity identity) identity))", "((identity identity) ((identity identity) identity))", "((\\x.x identity) ((identity identity) identity))", "(identity ((identity identity) identity))",
               "(\\x.x ((identity identity) identity))", "((identity identity) identity)", "((\\x.x identity) identity)", "(identity identity)", "(\\x.x identity)", "identity");
      e.setApplicativeOrderSteps("((identity (identity identity)) ((identity identity) \\x.x))", "((identity (identity identity)) ((identity \\x.x) \\x.x))", "((identity (identity identity)) ((\\x.x \\x.x) \\x.x))",
               "((identity (identity identity)) (\\x.x \\x.x))", "((identity (identity identity)) \\x.x)", "((identity (identity \\x.x)) \\x.x)", "((identity (\\x.x \\x.x)) \\x.x)", "((identity \\x.x) \\x.x)", "((\\x.x \\x.x) \\x.x)",
               "(\\x.x \\x.x)", "\\x.x");
      e.setLazySteps(e.getNormalOrderSteps());
      assertEvaluationSteps(e);
   }

   public void testEtaReduction() {
      // eta reduction: \\x.(a x) == a
      EvaluationStepsExpectation e = new EvaluationStepsExpectation("(\\y.(identity y) true)");
      e.setNormalOrderSteps("(identity true)", "(\\x.x true)", "true");
      e.setApplicativeOrderSteps("(\\y.(identity y) \\x.\\y.x)", "(identity \\x.\\y.x)", "(\\x.x \\x.\\y.x)", "\\x.\\y.x");
      e.setLazySteps(e.getNormalOrderSteps());
      assertEvaluationSteps(e);
   }

   public void testAlphaConversion() {
      LambdaCalculusEngine e = createNormalOrderEngine();
      e.addDef("test", "\\a.\\b.(b a)");
      // example of where alpha conversion not required:
      assertEvaluationSteps(e, "((test c) \\x.x)", "((\\a.\\b.(b a) c) \\x.x)", "(\\b.(b c) \\x.x)", "(\\x.x c)", "c");
      // example of where alpha conversion is required:
      assertEvaluationSteps(e, "((test b) \\x.x)", "((\\a.\\b.(b a) b) \\x.x)", "(\\b.(b b) \\x.x)", "(\\x.x b)", "b");
   }

   public void testApplyApplicationToSelfApplication() {
      EvaluationStepsExpectation e = new EvaluationStepsExpectation("(\\s.(s s) (\\x.x \\y.y))");
      e.setNormalOrderSteps("((\\x.x \\y.y) (\\x.x \\y.y))", "(\\y.y (\\x.x \\y.y))", "(\\x.x \\y.y)", "\\y.y");
      e.setApplicativeOrderSteps("(\\s.(s s) \\y.y)", "(\\y.y \\y.y)", "\\y.y");
      e.setLazySteps("((\\x.x \\y.y) (\\x.x \\y.y))", "(\\y.y \\y.y)", "\\y.y");
      assertEvaluationSteps(e);
   }
}
