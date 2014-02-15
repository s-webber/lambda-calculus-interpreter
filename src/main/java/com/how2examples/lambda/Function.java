package com.how2examples.lambda;

import static com.how2examples.lambda.ExpressionParser.ReservedCharacters.LAMBDA_TOKEN;

/**
 * An abstraction.
 * <p>
 * Format: {@code \name.body} <br>
 * Example: {@code \a.\b.(b a)}
 */
class Function implements Expression {
   private final Expression boundVariable;
   private final Expression body;

   Function(Expression boundVariable, Expression body) {
      this.boundVariable = boundVariable;
      this.body = body;
   }

   @Override
   public Function replace(Replacement replacement) {
      Expression newBoundVariable = boundVariable.replace(replacement);
      Expression newBody = body.replace(replacement);
      if (newBoundVariable != boundVariable || newBody != body) {
         return new Function(newBoundVariable, newBody);
      } else {
         return this;
      }
   }

   public Expression getBoundVariable() {
      return boundVariable;
   }

   public Expression getBody() {
      return body;
   }

   @Override
   public String toString() {
      return LAMBDA_TOKEN.value + boundVariable + "." + body;
   }
}