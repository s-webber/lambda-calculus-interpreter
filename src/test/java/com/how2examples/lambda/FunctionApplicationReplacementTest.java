package com.how2examples.lambda;

import junit.framework.TestCase;

import org.jmock.Mockery;

public class FunctionApplicationReplacementTest extends TestCase {
   private final Mockery context = new Mockery();
   final Expression from = mock("from");
   final FunctionApplication to = new FunctionApplication(mock("function"), mock("argument"));
   final FunctionApplicationReplacement r = new FunctionApplicationReplacement(from, to);

   public void testReturnNull() {
      assertSame(Optional.NULL, r.getNewValue(mock("test")));
   }

   public void testReturnNewValue() {
      // assert multiple calls to getNewValue() return different same value
      FunctionApplication newValue1 = (FunctionApplication) r.getNewValue(from).getValue();
      assertNotSame(to, newValue1);
      assertSame(to.getFunction(), newValue1.getFunction());
      assertSame(to.getArgument(), newValue1.getArgument());

      FunctionApplication newValue2 = (FunctionApplication) r.getNewValue(from).getValue();
      assertNotSame(to, newValue2);
      assertSame(to.getFunction(), newValue2.getFunction());
      assertSame(to.getArgument(), newValue2.getArgument());

      assertNotSame(newValue1, newValue2);
   }

   private Expression mock(String name) {
      return context.mock(Expression.class, name);
   }
}
