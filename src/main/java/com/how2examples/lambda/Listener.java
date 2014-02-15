package com.how2examples.lambda;

/** Provides a callback mechanism to provide feedback on the evaluation of an expression. */
public interface Listener {
   void feedback(Expression expression);
}
