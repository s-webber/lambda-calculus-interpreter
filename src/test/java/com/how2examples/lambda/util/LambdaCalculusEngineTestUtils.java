package com.how2examples.lambda.util;

import java.util.HashMap;
import java.util.Map;

import com.how2examples.lambda.EvaluationOrder;
import com.how2examples.lambda.LambdaCalculusEngine;

public class LambdaCalculusEngineTestUtils {
   private static final Map<String, String> DEFINITIONS = new HashMap<String, String>();
   static {
      // change to &&, || and !
      DEFINITIONS.put("identity", "\\x.x");

      // boolean logic
      DEFINITIONS.put("true", "\\x.\\y.x");
      DEFINITIONS.put("false", "\\x.\\y.y");
      DEFINITIONS.put("and", "\\p.\\q.((p q) p)");
      DEFINITIONS.put("or", "\\p.\\q.((p p) q)");
      DEFINITIONS.put("cond", "\\e1.\\e2.\\c.((c e1) e2)");
      DEFINITIONS.put("not", "\\x.((x false) true)");

      // numbers
      DEFINITIONS.put("zero", "\\x.x");
      DEFINITIONS.put("iszero", "\\n.(n true)");
      DEFINITIONS.put("pred", "\\n.(((iszero n) zero) (n false))");
      DEFINITIONS.put("succ", "\\n.\\s.((s false) n)");
      final String[] numbers = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
      for (int i = 1; i < numbers.length; i++) {
         DEFINITIONS.put(numbers[i], "(succ " + numbers[i - 1] + ")");
      }

      // arithmetic operators
      DEFINITIONS.put("recursive", "\\f.(\\s.(f (s s)) \\s.(f (s s)))");
      DEFINITIONS.put("add", "\\f.\\x.\\y." + createIfThenElse("(iszero y)", "x", "((f (succ x)) (pred y))"));
      DEFINITIONS.put("+", "(recursive add)");
      DEFINITIONS.put("multiply", "\\f.\\x.\\y." + createIfThenElse("(iszero y)", "zero", "((+ x) ((f x) (pred y)))"));
      DEFINITIONS.put("*", "(recursive multiply)");
      DEFINITIONS.put("subtract", "\\f.\\x.\\y." + createIfThenElse("(iszero y)", "x", "((f (pred x)) (pred y))"));
      DEFINITIONS.put("-", "(recursive subtract)");
      DEFINITIONS.put("divide", "\\f.\\x.\\y." + createIfThenElse("((> y) x)", "zero", "(succ ((f ((- x) y)) y))"));
      DEFINITIONS.put("/", "(recursive divide)");

      // numeric comparison
      DEFINITIONS.put(">", "\\x.\\y.(not(iszero ((- x) y)))");
      DEFINITIONS.put("diff", "\\x.\\y.((+ ((- x) y)) ((- y) x))");
      DEFINITIONS.put("=", "\\x.\\y.(iszero ((diff x) y))");
   }

   public static LambdaCalculusEngine createNormalOrderEngine() {
      return createLambdaCalculusEngine(EvaluationOrder.NORMAL);
   }

   public static LambdaCalculusEngine createApplicativeOrderEngine() {
      return createLambdaCalculusEngine(EvaluationOrder.APPLICATIVE);
   }

   public static LambdaCalculusEngine createLazyEvaluationEngine() {
      return createLambdaCalculusEngine(EvaluationOrder.LAZY);
   }

   private static LambdaCalculusEngine createLambdaCalculusEngine(EvaluationOrder order) {
      LambdaCalculusEngine e = new LambdaCalculusEngine(order);
      addDefinitions(e);
      return e;
   }

   private static void addDefinitions(LambdaCalculusEngine e) {
      for (Map.Entry<String, String> entry : DEFINITIONS.entrySet()) {
         e.addDef(entry.getKey(), entry.getValue());
      }
   }

   private static String createIfThenElse(String condition, String trueAction, String falseAction) {
      return "(((cond " + trueAction + ") " + falseAction + ") " + condition + ")";
   }
}
