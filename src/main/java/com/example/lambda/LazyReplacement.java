package com.example.lambda;

class LazyReplacement implements Replacement {
   private final Expression from;
   private final Optional to;

   LazyReplacement(Expression from, Expression to) {
      this.from = from;
      this.to = new Optional(to);
   }

   /**
    * Returns the expression that the specified expression should be replaced with.
    * <p>
    * Multiple calls to the same instance of {@code LazyReplacement} which specify the same expression argument will
    * result in the same object being returned. The same object is returned from multiple calls (rather than returning a
    * new copy each time) so that when transforming from:
    * </p>
    * <code>(\s.(s s) (\a.\b.b \x.\y.x))</code>
    * <p>
    * to:
    * </p>
    * <code>((\a.\b.b \x.\y.x) (\a.\b.b \x.\y.x))</code>
    * <p>
    * The function and argument expressions of the resulting function application refer to the the same instance.
    * </p>
    * 
    * @see FunctionApplicationReplacement#getNewValue(Expression)
    */
   @Override
   public Optional getNewValue(Expression expression) {
      if (shouldBeReplaced(expression)) {
         return to;
      } else {
         return Optional.NULL;
      }
   }

   private boolean shouldBeReplaced(Expression e) {
      return from == e;
   }
}
