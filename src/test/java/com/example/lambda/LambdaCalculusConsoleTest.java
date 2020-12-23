package com.example.lambda;

import static java.lang.System.lineSeparator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import junit.framework.TestCase;

public class LambdaCalculusConsoleTest extends TestCase {
   public void testFunctionDefinition() throws IOException {
      final String input = "def identity = \\x.x" // create definition
                           + lineSeparator()
                           + "(identity x)" // use definition in expression
                           + lineSeparator()
                           + lineSeparator(); // quit
      final String expected = lineSeparator()
                              + "> "
                              + lineSeparator()
                              + "> Input: (identity x)"
                              + lineSeparator()
                              + "(identity x)"
                              + lineSeparator()
                              + "(\\x.x x)"
                              + lineSeparator()
                              + "x"
                              + lineSeparator()
                              + "Result: x"
                              + lineSeparator()
                              + ""
                              + lineSeparator()
                              + "> ";
      final String actual = getOutput(input);
      assertEquals(expected, actual);
   }

   public void testInvalidInput() throws IOException {
      final String input = "x.x" // invalid input
                           + lineSeparator()
                           + "\\x x" // invalid input
                           + lineSeparator()
                           + "((s s) \\x.x)" // cannot evaluate
                           + lineSeparator()
                           + lineSeparator(); // quit
      final String expected = lineSeparator()
                              + "> Input: x.x"
                              + lineSeparator()
                              + "ERROR: java.lang.IllegalArgumentException"
                              + lineSeparator()
                              + "> Input: \\x x"
                              + lineSeparator()
                              + "ERROR: java.lang.IllegalArgumentException"
                              + lineSeparator()
                              + "> Input: ((s s) \\x.x)"
                              + lineSeparator()
                              + "((s s) \\x.x)"
                              + lineSeparator()
                              + "ERROR: java.lang.IllegalStateException: Expected function application but got: s"
                              + lineSeparator()
                              + "> ";
      final String actual = getOutput(input);
      assertEquals(expected, actual);
   }

   private String getOutput(String input) throws IOException {
      try (ByteArrayInputStream is = new ByteArrayInputStream(input.getBytes()); ByteArrayOutputStream os = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(os)) {
         final LambdaCalculusConsole c = new LambdaCalculusConsole(is, ps, EvaluationOrder.NORMAL);
         c.readEvaluatePrintLoop();
         return os.toString();
      }
   }
}
