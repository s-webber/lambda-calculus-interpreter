package com.how2examples.lambda;

/**
 * Provides a mechanism to replace one expression with another.
 * <p>
 * e.g. As required when reducing a function application (e.g. moving from <code>(\x.x y)</code> to <code>y</code> or
 * replacing the name of a defined function with it's definition (e.g. replacing <code>identity</code> with
 * <code>(\x.x y)</code> - where <code>identity</code> has already been defined using <code>def identity = \x.x</code>).
 * </p>
 */
interface Replacement {
   /**
    * Returns the new value the specified expression should be replaced with.
    * 
    * @see FunctionApplicationReplacement#getNewValue(Expression)
    * @see LazyReplacement#getNewValue(Expression)
    */
   Optional getNewValue(Expression expression);
}
