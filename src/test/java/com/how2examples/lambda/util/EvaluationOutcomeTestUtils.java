package com.how2examples.lambda.util;

import static com.how2examples.lambda.util.LambdaCalculusEngineTestUtils.createNormalOrderEngine;
import static junit.framework.Assert.assertEquals;

import com.how2examples.lambda.LambdaCalculusEngine;

public class EvaluationOutcomeTestUtils {
   public static void assertNormalOrderOutcome(String input, String expected) {
      assertOutcome(createNormalOrderEngine(), input, expected);
   }

   private static void assertOutcome(LambdaCalculusEngine e, String input, String expected) {
      assertEquals(expected, calculateOutcome(e, input));
   }

   private static String calculateOutcome(LambdaCalculusEngine e, String input) {
      return e.evaluateExpression(input).toString();
   }
}
