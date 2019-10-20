package com.jithyan.nabchallengebff.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BestProfit {
   private final String dateFormatted;
   private final FormattedQuote buy;
   private final FormattedQuote sell;
   private final String profit;

   @Data
   public static class FormattedQuote {
      private final String priceFormatted;
      private final String timeFormatted;
   }
}
