package com.how2examples.lambda;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

public class NameTest extends TestCase {
   private final Mockery context = new Mockery();

   private final Name name = new Name("x");

   public void testGetName() {
      assertEquals("x", name.getName());
   }

   public void testToString() {
      assertEquals("x", name.toString());
   }

   public void testReduceFalse() {
      final Replacement replacement = context.mock(Replacement.class);
      context.checking(new Expectations() {
         {
            oneOf(replacement).getNewValue(name);
            will(returnValue(Optional.NULL));
         }
      });
      assertSame(name, name.replace(replacement));
   }

   public void testReduceTrue() {
      final Replacement replacement = context.mock(Replacement.class);
      final Expression newValue = context.mock(Expression.class);
      context.checking(new Expectations() {
         {
            oneOf(replacement).getNewValue(name);
            will(returnValue(new Optional(newValue)));
         }
      });
      assertSame(newValue, name.replace(replacement));
   }
}
