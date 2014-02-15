package com.how2examples.lambda;

/**
 * An object that can optionally contain another object.
 * <p>
 * Used to avoid having to return {@code null} from methods.
 */
class Optional {
   public static final Optional NULL = new Optional(null);

   private final Expression value;

   public Optional(Expression e) {
      this.value = e;
   }

   public boolean isSet() {
      return value != null;
   }

   public Expression getValue() {
      return value;
   }
}
