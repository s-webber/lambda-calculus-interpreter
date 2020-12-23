package com.example.lambda;

import junit.framework.TestCase;

import org.jmock.Mockery;

public class LazyReplacementTest extends TestCase {
   private final Mockery context = new Mockery();
   final Expression from = mock("from");
   final Expression to = mock("to");
   final LazyReplacement r = new LazyReplacement(from, to);

   public void testReturnNull() {
      assertSame(Optional.NULL, r.getNewValue(mock("test")));
   }

   public void testReturnNewValue() {
      assertSame(to, r.getNewValue(from).getValue());
   }

   private Expression mock(String name) {
      return context.mock(Expression.class, name);
   }
}
