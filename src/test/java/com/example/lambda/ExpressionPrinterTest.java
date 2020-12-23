package com.example.lambda;

import java.io.IOException;

import junit.framework.TestCase;

public class ExpressionPrinterTest extends TestCase {
   public void testName() throws IOException {
      assertPrinterOutput("x", "[name] - x");
      assertPrinterOutput("qwertyuiop", "[name] - qwertyuiop");
      assertPrinterOutput("1", "[name] - 1");
      assertPrinterOutput("98265743", "[name] - 98265743");
      assertPrinterOutput("!$%&*-_+='1", "[name] - !$%&*-_+='1");
   }

   public void testApplication() throws IOException {
      assertPrinterOutput("(x y)", "[application]", "  [function exp] - [name] - x", "  [argument exp] - [name] - y");
      assertPrinterOutput("(\\x.x \\y.y)", "[application]", "  [function exp] - \\x.x", "    [function]", "      [bound variable] - x", "      [body] - [name] - x", "  [argument exp] - \\y.y", "    [function]", "      [bound variable] - y",
               "      [body] - [name] - y");
      assertPrinterOutput("((\\x.x x) (\\y.y y))", "[application]", "  [function exp] - (\\x.x x)", "    [application]", "      [function exp] - \\x.x", "        [function]", "          [bound variable] - x", "          [body] - [name] - x",
               "      [argument exp] - [name] - x", "  [argument exp] - (\\y.y y)", "    [application]", "      [function exp] - \\y.y", "        [function]", "          [bound variable] - y", "          [body] - [name] - y",
               "      [argument exp] - [name] - y");
   }

   public void testFunction() throws IOException {
      assertPrinterOutput("\\x.x", "[function]", "  [bound variable] - x", "  [body] - [name] - x");
      assertPrinterOutput("\\x.\\y.(x y)", "[function]", "  [bound variable] - x", "  [body] - \\y.(x y)", "    [function]", "      [bound variable] - y", "      [body] - (x y)", "        [application]", "          [function exp] - [name] - x",
               "          [argument exp] - [name] - y");
      assertPrinterOutput("\\x.\\y.\\z.(\\z.z (x y))", "[function]", "  [bound variable] - x", "  [body] - \\y.\\z.(\\z.z (x y))", "    [function]", "      [bound variable] - y", "      [body] - \\z.(\\z.z (x y))", "        [function]",
               "          [bound variable] - z", "          [body] - (\\z.z (x y))", "            [application]", "              [function exp] - \\z.z", "                [function]", "                  [bound variable] - z",
               "                  [body] - [name] - z", "              [argument exp] - (x y)", "                [application]", "                  [function exp] - [name] - x", "                  [argument exp] - [name] - y");
   }

   public void testUnknownExpression() throws IOException {
      try {
         ExpressionPrinter.toString(new Expression() {
            @Override
            public Expression replace(Replacement replacement) {
               return null;
            }
         });
         fail();
      } catch (IllegalArgumentException e) {
         // expected
      }
   }

   private void assertPrinterOutput(String input, String... expectedOutputLines) throws IOException {
      String expectedOutput = toString(expectedOutputLines);
      Expression e = ExpressionParser.createExpression(input);
      assertEquals(input, e.toString());
      String actualOutput = ExpressionPrinter.toString(e);
      assertEquals(expectedOutput, actualOutput);
   }

   private String toString(String... expectedOutputLines) {
      StringBuilder expectedOutput = new StringBuilder();
      for (String line : expectedOutputLines) {
         expectedOutput.append(line);
         expectedOutput.append(System.lineSeparator());
      }
      return expectedOutput.toString();
   }
}
