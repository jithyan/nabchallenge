package com.jithyan.nabchallengebff.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Supplier;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
   private final Supplier<BestProfit> bestProfit = calculateBestProfit();


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
               lastMin = min;
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
                  ZoneId.of(ZoneOffset.systemDefault().getId()).getRules()
                        .getOffset(Instant.now()))
            .format(DateTimeFormatter.ofPattern("dd-MMM-uuuu"));

      // If max == null, edge case where prices have only gone down
      String profitRounded = max == null
            ? "Can only sell at a loss today"
            : max.getPrice().subtract(min.getPrice())
                  .setScale(2, BigDecimal.ROUND_HALF_UP)
                  .toPlainString();

      String sellPriceFormatted = max == null
            ? ""
            : max.getPrice()
                  .setScale(2, BigDecimal.ROUND_HALF_UP)
                  .toPlainString();
      String sellTimeFormatted = max == null
            ? "Don't Sell"
            : LocalDateTime.ofEpochSecond(max.getTime() / 1000L, 0,
                  ZoneId.of(ZoneOffset.systemDefault().getId()).getRules()
                        .getOffset(Instant.now()))
                  .format(DateTimeFormatter.ISO_TIME);

      String buyPriceFormatted = max == null
            ? ""
            : min.getPrice()
                  .setScale(2, BigDecimal.ROUND_HALF_UP)
                  .toPlainString();
      String buyTimeFormatted = max == null
            ? "Don't Buy"
            : LocalDateTime.ofEpochSecond(min.getTime() / 1000L, 0,
                  ZoneId.of(ZoneOffset.systemDefault().getId()).getRules()
                        .getOffset(Instant.now()))
                  .format(DateTimeFormatter.ISO_TIME);

      return BestProfit.builder()
            .dateFormatted(dateFormatted)
            .profit(profitRounded)
            .buy(new BestProfit.FormattedQuote(buyPriceFormatted, buyTimeFormatted))
            .sell(new BestProfit.FormattedQuote(sellPriceFormatted, sellTimeFormatted))
            .build();
   }
}
