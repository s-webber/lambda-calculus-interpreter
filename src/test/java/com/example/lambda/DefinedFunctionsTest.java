package com.example.lambda;

import junit.framework.TestCase;

public class DefinedFunctionsTest extends TestCase {
   public void test() {
      DefinedFunctions df = new DefinedFunctions();

      String definitionName = "x";
      Expression input = new Name(definitionName);
      Expression output1 = new Name("y");
      Expression output2 = new Name("z");

      // test with no functions defined
      assertNotDefined(df, input);

      // add definition
      df.addDefinition(definitionName, output1);
      assertDefined(df, input, output1);
      assertNotDefined(df, new Name("w"));

      // replace existing definition
      df.addDefinition(definitionName, output2);
      assertDefined(df, input, output2);
   }

   private void assertDefined(DefinedFunctions df, Expression input, Expression expectedOutput) {
      assertTrue(df.isDefined(input));
      assertSame(expectedOutput, df.getDefinition(input));
   }

   private void assertNotDefined(DefinedFunctions df, Expression input) {
      assertFalse(df.isDefined(input));
      assertNull(df.getDefinition(input));
   }
}
