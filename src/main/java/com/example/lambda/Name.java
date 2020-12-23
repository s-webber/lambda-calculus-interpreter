package com.example.lambda;

/**
 * Identifies an abstraction point.
 * <p>
 * Example: {@code x}
 */
class Name implements Expression {
   /** A sequence of non-blank characters. */
   private final String name;

   Name(String name) {
      this.name = name;
   }

   @Override
   public Expression replace(Replacement replacement) {
      Optional o = replacement.getNewValue(this);
      if (o.isSet()) {
         return o.getValue();
      } else {
         return this;
      }
   }

   public String getName() {
      return name;
   }

   @Override
   public String toString() {
      return name;
   }
}