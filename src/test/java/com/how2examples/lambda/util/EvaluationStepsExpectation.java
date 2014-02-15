package com.how2examples.lambda.util;

public class EvaluationStepsExpectation {
   private final String input;
   private String[] normalOrderSteps;
   private String[] applicativeOrderSteps;
   private String[] lazySteps;

   public EvaluationStepsExpectation(String input) {
      this.input = input;
   }

   public String[] getNormalOrderSteps() {
      return normalOrderSteps;
   }

   public void setNormalOrderSteps(String... normalOrderSteps) {
      this.normalOrderSteps = normalOrderSteps;
   }

   public String[] getApplicativeOrderSteps() {
      return applicativeOrderSteps;
   }

   public void setApplicativeOrderSteps(String... applicativeOrderSteps) {
      this.applicativeOrderSteps = applicativeOrderSteps;
   }

   public String[] getLazySteps() {
      return lazySteps;
   }

   public void setLazySteps(String... lazySteps) {
      this.lazySteps = lazySteps;
   }

   public String getInput() {
      return input;
   }
}
