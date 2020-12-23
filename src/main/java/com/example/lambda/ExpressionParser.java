package com.example.lambda;

import static com.example.lambda.ExpressionParser.ReservedCharacters.APPLICATION_END_TOKEN;
import static com.example.lambda.ExpressionParser.ReservedCharacters.APPLICATION_START_TOKEN;
import static com.example.lambda.ExpressionParser.ReservedCharacters.LAMBDA_TOKEN;
import static com.example.lambda.ExpressionParser.ReservedCharacters.NAME_SEPARATOR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Constructs a {@link Expression} from a string containing lambda calculus syntax. */
class ExpressionParser {
   /** @param input e.g.: {@code (\s.(s s) (\x.x \y.y))} */
   static Expression createExpression(String input) {
      Tokens tokens = new Tokens(input);
      Expression result = createExpression(tokens, new HashMap<String, Name>());
      if (tokens.hasNext()) {
         throw new IllegalArgumentException();
      }
      return result;
   }

   private static Expression createExpression(Tokens tokens, Map<String, Name> boundVariables) {
      final Expression result;

      String next = tokens.nextToken();
      if (equal(APPLICATION_START_TOKEN, next)) {
         Expression function = createExpression(tokens, boundVariables);
         Expression argument = createExpression(tokens, boundVariables);
         if (!equal(APPLICATION_END_TOKEN, tokens.nextToken())) {
            throw new IllegalArgumentException();
         }
         result = new FunctionApplication(function, argument);
      } else if (equal(LAMBDA_TOKEN, next)) {
         Name name = createName(tokens);
         boundVariables.put(name.getName(), name);
         if (!equal(NAME_SEPARATOR, tokens.nextToken())) {
            throw new IllegalArgumentException();
         }
         Expression body = createExpression(tokens, boundVariables);
         result = new Function(name, body);
      } else if (boundVariables.containsKey(next)) {
         result = boundVariables.get(next);
      } else {
         result = new Name(next);
      }

      return result;
   }

   private static Name createName(Tokens tokens) {
      String next = tokens.nextToken();
      if (ReservedCharacters.isReservedCharacter(next)) {
         throw new IllegalArgumentException();
      }
      return new Name(next);
   }

   private static boolean equal(ReservedCharacters t, String s) {
      return t.value.equals(s);
   }

   private static class Tokens {
      private final List<String> tokens;
      private int ctr = 0;

      Tokens(String input) {
         tokens = new ArrayList<String>();
         StringBuilder current = new StringBuilder();
         for (char c : input.toCharArray()) {
            if (ReservedCharacters.isReservedCharacter(c)) {
               addToken(current);
               tokens.add(Character.toString(c));
            } else if (Character.isWhitespace(c)) {
               addToken(current);
            } else {
               current.append(c);
            }
         }
         addToken(current);
      }

      private void addToken(StringBuilder current) {
         if (current.length() > 0) {
            tokens.add(current.toString());
            current.setLength(0);
         }
      }

      String nextToken() {
         if (!hasNext()) {
            throw new IllegalArgumentException();
         }
         return tokens.get(ctr++);
      }

      public boolean hasNext() {
         return ctr < tokens.size();
      }
   }

   static enum ReservedCharacters {
      APPLICATION_START_TOKEN("("),
      APPLICATION_END_TOKEN(")"),
      LAMBDA_TOKEN("\\"),
      NAME_SEPARATOR(".");

      final String value;

      ReservedCharacters(String value) {
         this.value = value;
      }

      static boolean isReservedCharacter(char c) {
         return isReservedCharacter(Character.toString(c));
      }

      static boolean isReservedCharacter(String s) {
         for (ReservedCharacters t : ReservedCharacters.values()) {
            if (s.equals(t.value)) {
               return true;
            }
         }
         return false;
      }
   }
}
