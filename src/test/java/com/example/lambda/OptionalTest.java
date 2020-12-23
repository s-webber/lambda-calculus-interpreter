package com.example.lambda;

import junit.framework.TestCase;

public class OptionalTest extends TestCase {
   public void testSet() {
      Name value = new Name("x");
      Optional optional = new Optional(value);
      assertTrue(optional.isSet());
      assertSame(value, optional.getValue());
   }

   public void testNull() {
      assertFalse(Optional.NULL.isSet());
      assertNull(Optional.NULL.getValue());
   }
}
