package com.example.lambda;

import static com.example.lambda.EvaluationOrder.APPLICATIVE;
import static com.example.lambda.EvaluationOrder.LAZY;

/**
 * Evaluates lambda calculus expressions.
 * <p>
 * e.g. Evaluates from: {@code (\s.(s s) (\x.x \y.y))} to: {@code \y.y}
 */
class ExpressionEvaluator {
   private final EvaluationOrder order;

   ExpressionEvaluator(EvaluationOrder order) {
      this.order = order;
   }

   Expression evaluateExpression(Expression expression, DefinedFunctions definitions, Listener listener) {
      while (expression instanceof FunctionApplication) {
         listener.feedback(expression);
         expression = evaluate(expression, definitions);
      }
      listener.feedback(expression);
      return expression;
   }

   private Expression evaluate(Expression expression, DefinedFunctions definitions) {
      if (expression instanceof FunctionApplication) {
         return evaluate((FunctionApplication) expression, definitions);
      } else {
         throw new IllegalStateException("Expected function application but got: " + expression);
      }
   }

   private Expression evaluate(FunctionApplication a, DefinedFunctions definitions) {
      Expression function = a.getFunction();
      Expression argument = a.getArgument();
      Expression result;

      if (doReplaceArgumentWithDefinition(definitions, argument)) {
         // e.g. where (not true) and "true" is a defined function
         result = replaceArgumentWithDefinition(definitions, function, argument);
      } else if (doEvaluateArgument(argument)) {
         // e.g. where (\s.(s s) (\x.x \x.x))
         result = evaluateArgument(definitions, function, argument);
      } else if (doReplaceFunctionWithDefiniton(definitions, function)) {
         // e.g. where (not true) and "not" is a defined function
         result = replaceFunctionWithDefinition(definitions, function, argument);
      } else if (function instanceof Function) {
         // e.g. where expression is: (\x.x y)
         result = applyFunctionToArgument(function, argument);
      } else {
         // e.g. where expression is: ((\x.x \x.x) y)
         result = evaluateFunction(a, definitions);
      }

      return result;
   }

   private boolean doReplaceArgumentWithDefinition(DefinedFunctions definitions, Expression argument) {
      return order == APPLICATIVE && definitions.isDefined(argument);
   }

   private Expression replaceArgumentWithDefinition(DefinedFunctions definitions, Expression function, Expression argument) {
      return new FunctionApplication(function, definitions.getDefinition(argument));
   }

   private boolean doReplaceFunctionWithDefiniton(DefinedFunctions definitions, Expression function) {
      return definitions.isDefined(function);
   }

   private Expression replaceFunctionWithDefinition(DefinedFunctions definitions, Expression function, Expression argument) {
      return new FunctionApplication(definitions.getDefinition(function), argument);
   }

   private boolean doEvaluateArgument(Expression argument) {
      return order == APPLICATIVE && argument instanceof FunctionApplication;
   }

   private Expression evaluateArgument(DefinedFunctions definitions, Expression function, Expression argument) {
      return new FunctionApplication(function, evaluate(argument, definitions));
   }

   private Expression applyFunctionToArgument(Expression function, Expression argument) {
      Function f = (Function) function;
      Replacement r = createReplacement(f.getBoundVariable(), argument);
      return f.getBody().replace(r);
   }

   private Expression evaluateFunction(FunctionApplication a, DefinedFunctions definitions) {
      Expression function = a.getFunction();
      Replacement r = createReplacement(function, evaluate(function, definitions));
      return a.replace(r);
   }

   private Replacement createReplacement(Expression from, Expression to) {
      if (to instanceof FunctionApplication && order != LAZY) {
         return new FunctionApplicationReplacement(from, (FunctionApplication) to);
      } else {
         return new LazyReplacement(from, to);
      }
   }
}
