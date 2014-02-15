package com.how2examples.lambda;

import java.util.HashMap;
import java.util.Map;

/**
 * A collection of name/function associations.
 * <p>
 * To avoid repeatedly writing out commonly used functions, functions can be associated with concise names. When an
 * expression containing the name is evaluated the name is replaced with the actual expression.
 */
class DefinedFunctions {
   private final Map<String, Expression> definitions = new HashMap<>();

   void addDefinition(String name, Expression expression) {
      definitions.put(name, expression);
   }

   boolean isDefined(Expression e) {
      return definitions.containsKey(e.toString());
   }

   Expression getDefinition(Expression e) {
      return definitions.get(e.toString());
   }
}
