package com.jithyan.nabchallengebff.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Supplier;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jithyan.nabchallengebff.constants.Constants;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document("cryptoPrices")
public class CryptoPrices {
   @BsonProperty("_id")
   private final ObjectId id;
   private final String currency;
   private final long date;
   private final List<Quote> quotes;
   @JsonIgnore
   @BsonIgnore
   /**
    * Lazily calculates the best profit possible for this currency on this day.
    */
   private final Supplier<BestProfit> bestProfit = calculateBestProfit();


   /**
    * Finds the best time to buy and sell given a list of Quotes sorted by time (ascending)
    * for a currency.
    * Based on the algorithm from the site below, which solves it linearly:
    * http://p-nand-q.com/python/algorithms/searching/max-profit.html
    * 
    * @return The profit, buy and sell details of the best profit calculation
    */
   private Supplier<BestProfit> calculateBestProfit() {
      return () -> {
         Quote lastMin = null;
         Quote min = null;
         Quote max = null;
         BigDecimal maxProfit = new BigDecimal(0);

         for (Quote curr : quotes) {
            BigDecimal currPrice = curr.getPrice();

            if (min == null) {
               min = curr;
               lastMin = curr;
            } else if (currPrice.compareTo(min.getPrice()) < 0) {
               lastMin = max != null && Long.compare(min.getTime(), max.getTime()) > 0
                     ? lastMin
                     : min;
               min = curr;
            } else if (max == null) {
               max = curr;
            } else if (currPrice.subtract(min.getPrice()).compareTo(maxProfit) > 0) {
               max = curr;
               maxProfit = currPrice.subtract(min.getPrice());
            }
         }

         //Edge case if we find a new min after a max
         if (max != null && Long.compare(min.getTime(), max.getTime()) > 0) {
            min = lastMin;
         }

         return buildBestProfitFromResult(min, max);
      };
   }


   private BestProfit buildBestProfitFromResult(Quote min, Quote max) {
      String dateFormatted = LocalDateTime
            .ofEpochSecond(date / 1000L, 0,
                  Constants.CURRENT_ZONE_ID)
            .format(DateTimeFormatter.ofPattern("dd-MMM-uuuu"));

      // Edge case where prices have only gone down
      if (max == null) {
         return BestProfit.noBestProfit(dateFormatted);
      }
      String profitRounded = max.getPrice().subtract(min.getPrice())
            .setScale(2, BigDecimal.ROUND_HALF_UP)
            .toPlainString();

      String sellPriceFormatted = max.getPrice()
            .setScale(2, BigDecimal.ROUND_HALF_UP)
            .toPlainString();
      String sellTimeFormatted = LocalDateTime.ofEpochSecond(max.getTime() / 1000L, 0,
            Constants.CURRENT_ZONE_ID)
            .format(DateTimeFormatter.ISO_TIME);

      String buyPriceFormatted = min.getPrice()
            .setScale(2, BigDecimal.ROUND_HALF_UP)
            .toPlainString();
      String buyTimeFormatted = LocalDateTime.ofEpochSecond(min.getTime() / 1000L, 0,
            Constants.CURRENT_ZONE_ID)
            .format(DateTimeFormatter.ISO_TIME);

      return BestProfit.builder()
            .dateFormatted(dateFormatted)
            .profit(profitRounded)
            .buy(new BestProfit.FormattedQuote(buyPriceFormatted, buyTimeFormatted))
            .sell(new BestProfit.FormattedQuote(sellPriceFormatted, sellTimeFormatted))
            .build();
   }
}
