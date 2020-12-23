package com.example.lambda;

/**
 * Specialises an abstraction.
 * <p>
 * Also known as a "bound pair".
 * </p>
 * Format: {@code (function-expression argument-expression)}<br>
 * Example: {@code (\s.(s s) (\x.x \y.y))}
 */
class FunctionApplication implements Expression {
   private final Expression function;
   private final Expression argument;

   FunctionApplication(Expression function, Expression argument) {
      this.function = function;
      this.argument = argument;
   }

   @Override
   public Expression replace(Replacement replacement) {
      Optional o = replacement.getNewValue(this);
      if (o.isSet()) {
         return o.getValue();
      } else {
         Expression newFunction = function.replace(replacement);
         Expression newArgument = argument.replace(replacement);
         if (newFunction != function || newArgument != argument) {
            return new FunctionApplication(newFunction, newArgument);
         } else {
            return this;
         }
      }
   }

   public Expression getFunction() {
      return function;
   }

   public Expression getArgument() {
      return argument;
   }

   @Override
   public String toString() {
      return "(" + function + " " + argument + ")";
   }
}