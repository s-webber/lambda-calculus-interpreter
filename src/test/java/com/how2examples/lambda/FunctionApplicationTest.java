package com.how2examples.lambda;

import static com.how2examples.lambda.Optional.NULL;
import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

public class FunctionApplicationTest extends TestCase {
   private final Mockery context = new Mockery();

   private final Replacement replacement = context.mock(Replacement.class);
   private final Expression functionExpression = context.mock(Expression.class, "functionExpression");
   private final Expression newFunctionExpression = context.mock(Expression.class, "newFunctionExpression");
   private final Expression argumentExpression = context.mock(Expression.class, "argumentExpression");
   private final Expression newArgumentExpression = context.mock(Expression.class, "newArgumentExpression");
   private final FunctionApplication functionApplication = new FunctionApplication(functionExpression, argumentExpression);

   public void testGetFunction() {
      assertSame(functionExpression, functionApplication.getFunction());
   }

   public void testGetArgument() {
      assertSame(argumentExpression, functionApplication.getArgument());
   }

   public void testToString() {
      assertEquals("(x y)", new FunctionApplication(new Name("x"), new Name("y")).toString());
   }

   public void testReduceFunctionApplicationChange() {
      final Expression newValue = context.mock(Expression.class);
      context.checking(new Expectations() {
         {
            oneOf(replacement).getNewValue(functionApplication);
            will(returnValue(new Optional(newValue)));
         }
      });
      assertSame(newValue, functionApplication.replace(replacement));
   }

   public void testReduceNoChange() {
      context.checking(new Expectations() {
         {
            oneOf(replacement).getNewValue(functionApplication);
            will(returnValue(NULL));

            oneOf(functionExpression).replace(replacement);
            will(returnValue(functionExpression));

            oneOf(argumentExpression).replace(replacement);
            will(returnValue(argumentExpression));
         }
      });
      assertSame(functionApplication, functionApplication.replace(replacement));
   }

   public void testReduceFunctionChange() {
      context.checking(new Expectations() {
         {
            oneOf(replacement).getNewValue(functionApplication);
            will(returnValue(NULL));

            oneOf(functionExpression).replace(replacement);
            will(returnValue(newFunctionExpression));

            oneOf(argumentExpression).replace(replacement);
            will(returnValue(argumentExpression));
         }
      });
      Expression result = functionApplication.replace(replacement);
      assertResult(result, newFunctionExpression, argumentExpression);
   }

   public void testReduceArgumentChange() {
      context.checking(new Expectations() {
         {
            oneOf(replacement).getNewValue(functionApplication);
            will(returnValue(NULL));

            oneOf(functionExpression).replace(replacement);
            will(returnValue(functionExpression));

            oneOf(argumentExpression).replace(replacement);
            will(returnValue(newArgumentExpression));
         }
      });
      Expression result = functionApplication.replace(replacement);
      assertResult(result, functionExpression, newArgumentExpression);
   }

   public void testReduceFunctionAndArgumentChange() {
      context.checking(new Expectations() {
         {
            oneOf(replacement).getNewValue(functionApplication);
            will(returnValue(NULL));

            oneOf(functionExpression).replace(replacement);
            will(returnValue(newFunctionExpression));

            oneOf(argumentExpression).replace(replacement);
            will(returnValue(newArgumentExpression));
         }
      });
      Expression result = functionApplication.replace(replacement);
      assertResult(result, newFunctionExpression, newArgumentExpression);
   }

   private void assertResult(Expression result, Expression expectedFunctionExpression, Expression expectedArgumentExpression) {
      assertNotSame(functionApplication, result);
      assertSame(FunctionApplication.class, result.getClass());
      FunctionApplication fa = (FunctionApplication) result;
      assertSame(expectedFunctionExpression, fa.getFunction());
      assertSame(expectedArgumentExpression, fa.getArgument());
   }
}