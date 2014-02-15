package com.how2examples.lambda;

/** A core building-block from which lambda calculus is constructed. */
public interface Expression {
   Expression replace(Replacement replacement);
}