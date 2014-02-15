package com.how2examples.lambda;

/**
 * The order in which a function application should be evaluated.
 * <p>
 * Also known as: reduction strategy / evaluation strategy.
 */
public enum EvaluationOrder {
   /**
    * Substitute the argument expression of a function application into the body expression <i>before</i> it being
    * reduced.
    */
   NORMAL,
   /**
    * Reduce the argument expression of a function application <i>before</i> it being substituted into the body
    * expression.
    */
   APPLICATIVE,
   /**
    * Delay expression evaluation until if is the body expression of a function application.
    * <p>
    * When evaluated, all copies of the expression are updated with the new value.
    */
   LAZY
}
