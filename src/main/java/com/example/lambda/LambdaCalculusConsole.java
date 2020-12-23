package com.example.lambda;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A read-evaluate-print loop (REPL) console application for lambda calculus expressions.
 * <p>
 * To start the application you can do the following (this will default to normal order evaluation):
 * </p>
 * <code>java com.example.lambda.LambdaCalculusConsole</code>
 * <p>
 * To use applicative evaluation order do:
 * </p>
 * <code>java com.example.lambda.LambdaCalculusConsole APPLICATIVE</code>
 * <p>
 * To use lazy evaluation order do:
 * </p>
 * <code>java com.example.lambda.LambdaCalculusConsole LAZY</code>
 * 
 * @see EvaluationOrder
 */
public class LambdaCalculusConsole implements Listener {
   private static final Pattern DEFINITION_SYNTAX = Pattern.compile("def (\\S+) = (\\S+)");

   private final Scanner scanner;
   private final PrintStream out;
   private final LambdaCalculusEngine engine;

   LambdaCalculusConsole(InputStream source, PrintStream out, EvaluationOrder order) {
      this.scanner = new Scanner(source);
      this.out = out;
      this.engine = new LambdaCalculusEngine(order);
   }

   void readEvaluatePrintLoop() {
      do {
         printPrompt();
      } while (readEvaluateAndPrint());
   }

   /** @return {@code true} if should continue read-evaluate-print loop, {@code false} if should exit */
   private boolean readEvaluateAndPrint() {
      try {
         String input = scanner.nextLine();
         return evaluate(input);
      } catch (Exception e) {
         out.print("ERROR: " + e);
         return true;
      }
   }

   private boolean evaluate(String input) {
      if (shouldExit(input)) {
         return false;
      } else if (isDefinitionCommand(input)) {
         addDefinition(input);
         return true;
      } else {
         evaluateExpression(input);
         return true;
      }
   }

   private boolean shouldExit(String input) {
      return input.length() == 0;
   }

   private boolean isDefinitionCommand(String input) {
      return DEFINITION_SYNTAX.matcher(input).matches();
   }

   private void addDefinition(String input) {
      Matcher m = DEFINITION_SYNTAX.matcher(input);
      m.matches();
      engine.addDef(m.group(1), m.group(2));
   }

   private void evaluateExpression(String input) {
      printInput(input);
      Expression result = engine.evaluateExpression(input, this);
      printResult(result);
   }

   private void printPrompt() {
      out.println();
      out.print("> ");
   }

   private void printInput(String input) {
      out.println("Input: " + input);
   }

   private void printResult(Expression result) {
      out.println("Result: " + result);
   }

   @Override
   public void feedback(Expression expression) {
      out.println(expression);
   }

   public static void main(String[] args) {
      EvaluationOrder order = getEvaluationOrder(args);
      LambdaCalculusConsole console = new LambdaCalculusConsole(System.in, System.out, order);
      console.readEvaluatePrintLoop();
   }

   private static EvaluationOrder getEvaluationOrder(String[] args) {
      switch (args.length) {
         case 0:
            return EvaluationOrder.NORMAL;
         case 1:
            return EvaluationOrder.valueOf(args[0]);
         default:
            throw new IllegalArgumentException();
      }
   }
}
