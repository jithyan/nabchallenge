package com.jithyan.nabchallengebff.model;

import lombok.Builder;
import lombok.Data;

/**
 * A formatted representation of the best profit calculated for
 * a single currency in a day.
 * 
 * @author Jithya
 *
 */
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

   public static BestProfit noBestProfit(String dateFormatted) {
      return BestProfit.builder()
            .dateFormatted(dateFormatted)
            .profit("Can only sell at a loss today")
            .buy(new BestProfit.FormattedQuote("", "Don't Buy"))
            .sell(new BestProfit.FormattedQuote("", "Don't Sell"))
            .build();
   }


}
