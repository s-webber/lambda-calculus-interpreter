package com.how2examples.lambda.util;

import static com.how2examples.lambda.util.LambdaCalculusEngineTestUtils.createApplicativeOrderEngine;
import static com.how2examples.lambda.util.LambdaCalculusEngineTestUtils.createLazyEvaluationEngine;
import static com.how2examples.lambda.util.LambdaCalculusEngineTestUtils.createNormalOrderEngine;
import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.how2examples.lambda.Expression;
import com.how2examples.lambda.LambdaCalculusEngine;
import com.how2examples.lambda.Listener;

public class EvaluationStepsTestUtils {
   public static void assertEvaluationSteps(String input, String... expected) {
      EvaluationStepsExpectation e = new EvaluationStepsExpectation(input);
      e.setNormalOrderSteps(expected);
      e.setApplicativeOrderSteps(expected);
      e.setLazySteps(expected);
      assertEvaluationSteps(e);
   }

   public static void assertEvaluationSteps(EvaluationStepsExpectation e) {
      assertEvaluationSteps(createNormalOrderEngine(), e.getInput(), e.getNormalOrderSteps());
      assertEvaluationSteps(createApplicativeOrderEngine(), e.getInput(), e.getApplicativeOrderSteps());
      assertEvaluationSteps(createLazyEvaluationEngine(), e.getInput(), e.getLazySteps());
   }

   public static void assertNormalOrderSteps(String input, String... expected) {
      assertEvaluationSteps(createNormalOrderEngine(), input, expected);
   }

   public static void assertEvaluationSteps(LambdaCalculusEngine e, String input, String... expected) {
      final List<Expression> actual = new ArrayList<>();
      final Expression result = e.evaluateExpression(input, new Listener() {
         @Override
         public void feedback(Expression expression) {
            actual.add(expression);
         }
      });
      // Note: expect "expected" to be one element shorter than "actual" -
      // as "actual" has the original input stored as the first element
      // but "expected" does not.
      assertEquals(expected.length + 1, actual.size());
      assertExpression(input, actual.get(0));
      for (int i = 0; i < expected.length; i++) {
         assertExpression(expected[i], actual.get(i + 1));
      }
      assertExpression(expected[expected.length - 1], result);
   }

   private static void assertExpression(String expected, Expression actual) {
      assertEquals(expected, actual.toString());
   }
}
