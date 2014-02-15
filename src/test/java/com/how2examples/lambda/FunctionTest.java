package com.how2examples.lambda;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

public class FunctionTest extends TestCase {
   private final Mockery context = new Mockery();

   private final Replacement replacement = context.mock(Replacement.class);
   private final Expression boundVariable = context.mock(Expression.class, "boundVariable");
   private final Expression newBoundVariable = context.mock(Expression.class, "newBoundVariable");
   private final Expression body = context.mock(Expression.class, "body");
   private final Expression newBody = context.mock(Expression.class, "newBody");
   private final Function function = new Function(boundVariable, body);

   public void testGetBoundVariable() {
      assertSame(boundVariable, function.getBoundVariable());
   }

   public void testGetBody() {
      assertSame(body, function.getBody());
   }

   public void testToString() {
      assertEquals("\\x.y", new Function(new Name("x"), new Name("y")).toString());
   }

   public void testReduceNoChange() {
      context.checking(new Expectations() {
         {
            oneOf(boundVariable).replace(replacement);
            will(returnValue(boundVariable));

            oneOf(body).replace(replacement);
            will(returnValue(body));
         }
      });
      assertSame(function, function.replace(replacement));
   }

   public void testReduceBoundVariableChange() {
      context.checking(new Expectations() {
         {
            oneOf(boundVariable).replace(replacement);
            will(returnValue(newBoundVariable));

            oneOf(body).replace(replacement);
            will(returnValue(body));
         }
      });
      Function result = function.replace(replacement);
      assertResult(result, newBoundVariable, body);
   }

   public void testReduceBodyChange() {
      context.checking(new Expectations() {
         {
            oneOf(boundVariable).replace(replacement);
            will(returnValue(boundVariable));

            oneOf(body).replace(replacement);
            will(returnValue(newBody));
         }
      });
      Function result = function.replace(replacement);
      assertResult(result, boundVariable, newBody);
   }

   public void testReduceBoundVariableAndBodyChange() {
      context.checking(new Expectations() {
         {
            oneOf(boundVariable).replace(replacement);
            will(returnValue(newBoundVariable));

            oneOf(body).replace(replacement);
            will(returnValue(newBody));
         }
      });
      Function result = function.replace(replacement);
      assertResult(result, newBoundVariable, newBody);
   }

   private void assertResult(Function result, Expression expectedBoundVariable, Expression expectedBody) {
      assertNotSame(function, result);
      assertSame(expectedBoundVariable, result.getBoundVariable());
      assertSame(expectedBody, result.getBody());
   }
}
