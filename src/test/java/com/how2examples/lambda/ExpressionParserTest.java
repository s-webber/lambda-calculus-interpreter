package com.how2examples.lambda;

import junit.framework.TestCase;

public class ExpressionParserTest extends TestCase {
   public void testName() {
      String input = "x";
      assertValid(input, Name.class);
   }

   public void testFunction() {
      String input = "\\x.x";
      assertValid(input, Function.class);
   }

   public void testFunctionApplication() {
      String input = "(x x)";
      assertValid(input, FunctionApplication.class);
   }

   public void testComplexExpression() {
      String input = "\\x.\\y.\\z.((\\x.x x) (y z))";
      assertValid(input, Function.class);
   }

   public void testInvalid() {
      assertInvalid("x.x");
      assertInvalid("x.(x x)");
      assertInvalid("\\x(x x)");
      assertInvalid("\\x.(x x))");
      assertInvalid("\\x.((x x)");
      assertInvalid("\\x.(x x x)");
      assertInvalid("\\\\x.x");
      assertInvalid("\\x..x");
   }

   private void assertValid(String input, Class<? extends Expression> c) {
      Expression e = ExpressionParser.createExpression(input);
      assertSame(c, e.getClass());
      assertEquals(input, e.toString());
   }

   private void assertInvalid(String input) {
      try {
         Expression e = ExpressionParser.createExpression(input);
         fail(input + " " + e.toString());
      } catch (IllegalArgumentException e) {
         //expected
      }
   }
}
