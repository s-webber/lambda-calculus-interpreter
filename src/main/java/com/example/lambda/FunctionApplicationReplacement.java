package com.example.lambda;

class FunctionApplicationReplacement implements Replacement {
   private final Expression from;
   private final FunctionApplication to;

   FunctionApplicationReplacement(Expression from, FunctionApplication to) {
      this.from = from;
      this.to = to;
   }

   /**
    * Returns a new instance of a {@link FunctionApplication} that the specified expression should be replaced with.
    * <p>
    * When using a 'normal order' or 'applicative' (i.e. not 'lazy') evaluation order there is a requirement to make a
    * new copy of the function application (rather than just returning the instance specified in the constructor) so
    * that when transforming from:
    * </p>
    * <code>(\s.(s s) (\a.\b.b \x.\y.x))</code>
    * <p>
    * to:
    * </p>
    * <code>((\a.\b.b \x.\y.x) (\a.\b.b \x.\y.x))</code>
    * <p>
    * The function and argument expressions of the resulting function application are separate (i.e. different) objects.
    * </p>
    * 
    * @see LazyReplacement#getNewValue(Expression)
    */
   @Override
   public Optional getNewValue(Expression expression) {
      if (shouldBeReplaced(expression)) {
         return new Optional(createNewValue());
      } else {
         return Optional.NULL;
      }
   }

   private boolean shouldBeReplaced(Expression e) {
      return from == e;
   }

   private FunctionApplication createNewValue() {
      return new FunctionApplication(to.getFunction(), to.getArgument());
   }
}
