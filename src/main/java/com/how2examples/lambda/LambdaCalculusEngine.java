package com.how2examples.lambda;

/**
 * Provides a concise API for evaluating lambda calculus.
 * <p>
 * Acts as a facade to provide a simplified interface to other parts of the code-base.
 */
public class LambdaCalculusEngine {
   private static final Listener EMPTY_LISTENER = new Listener() {
      @Override
      public void feedback(Expression expression) {
      }
   };

   private final DefinedFunctions definitions = new DefinedFunctions();
   private final ExpressionEvaluator evaluator;

   public LambdaCalculusEngine(EvaluationOrder order) {
      this.evaluator = new ExpressionEvaluator(order);
   }

   public Expression evaluateExpression(String input) {
      return evaluateExpression(input, EMPTY_LISTENER);
   }

   public Expression evaluateExpression(String input, Listener listener) {
      Expression expression = ExpressionParser.createExpression(input);
      return evaluator.evaluateExpression(expression, definitions, listener);
   }

   public void addDef(String name, String expression) {
      Expression e = ExpressionParser.createExpression(expression);
      definitions.addDefinition(name, e);
   }
}
