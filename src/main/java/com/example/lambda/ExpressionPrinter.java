package com.example.lambda;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/** Constructs a string representation of an expression. */
public class ExpressionPrinter {
   public static String toString(Expression e) throws IOException {
      try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
         printExpression(e, 0, pw);
         pw.close();
         return sw.toString();
      }
   }

   private static void printExpression(Expression e, int indent, PrintWriter w) {
      if (e instanceof Function) {
         printFunction((Function) e, indent, w);
      } else if (e instanceof FunctionApplication) {
         printFunctionApplication((FunctionApplication) e, indent, w);
      } else if (e instanceof Name) {
         printName((Name) e, indent, w);
      } else {
         throw new IllegalArgumentException();
      }
   }

   private static void printFunction(Function f, int indent, PrintWriter w) {
      if (indent != 0) {
         w.println(f);
      }
      println("[function]", indent, w);
      indent++;
      println("[bound variable] - " + f.getBoundVariable(), indent, w);
      print("[body] - ", indent, w);
      printExpression(f.getBody(), indent + 1, w);
   }

   private static void printFunctionApplication(FunctionApplication a, int indent, PrintWriter w) {
      if (indent != 0) {
         w.println(a);
      }
      println("[application]", indent, w);
      indent++;
      print("[function exp] - ", indent, w);
      printExpression(a.getFunction(), indent + 1, w);
      print("[argument exp] - ", indent, w);
      printExpression(a.getArgument(), indent + 1, w);
   }

   private static void printName(Name n, int indent, PrintWriter w) {
      w.println("[name] - " + n);
   }

   private static void println(Object s, int indent, PrintWriter w) {
      print(s, indent, w);
      w.println();
   }

   private static void print(Object s, int indent, PrintWriter w) {
      for (int i = 0; i < indent; i++) {
         w.print("  ");
      }
      w.print(s);
   }
}