package com.jithyan.nabchallengebff.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
         List<Quote> orderedQuotes = quotes.stream()
               .sorted((x, y) -> Long.compare(x.getTime(), y.getTime()))
               .collect(Collectors.toList());

         Quote min = null;
         Quote max = null;
         BigDecimal maxProfit = new BigDecimal(0);

         for (Quote curr : orderedQuotes) {
            if (min == null || curr.getPrice().compareTo(min.getPrice()) < 0) {
               min = curr;
            } else if (max == null
                  || curr.getPrice().subtract(min.getPrice()).compareTo(maxProfit) > 0) {
               max = curr;
               maxProfit = curr.getPrice().subtract(min.getPrice());
            }
         }


         String dateFormatted = LocalDateTime
               .ofEpochSecond(date / 1000L, 0,
                     ZoneId.of(ZoneOffset.systemDefault().getId()).getRules()
                           .getOffset(Instant.now()))
               .format(DateTimeFormatter.ofPattern("dd-MMM-uuuu"));

         String profitRounded = maxProfit
               .setScale(2, BigDecimal.ROUND_HALF_UP)
               .toPlainString();

         String sellPriceFormatted = max
               .getPrice()
               .setScale(2, BigDecimal.ROUND_HALF_UP)
               .toPlainString();
         String sellTimeFormatted = LocalDateTime
               .ofEpochSecond(max.getTime() / 1000L, 0,
                     ZoneId.of(ZoneOffset.systemDefault().getId()).getRules()
                           .getOffset(Instant.now()))
               .format(DateTimeFormatter.ISO_TIME);

         String buyPriceFormatted = min
               .getPrice()
               .setScale(2, BigDecimal.ROUND_HALF_UP)
               .toPlainString();
         String buyTimeFormatted = LocalDateTime
               .ofEpochSecond(min.getTime() / 1000L, 0,
                     ZoneId.of(ZoneOffset.systemDefault().getId()).getRules()
                           .getOffset(Instant.now()))
               .format(DateTimeFormatter.ISO_TIME);

         return BestProfit.builder()
               .dateFormatted(dateFormatted)
               .profit(profitRounded)
               .buy(new BestProfit.FormattedQuote(buyPriceFormatted, buyTimeFormatted))
               .sell(new BestProfit.FormattedQuote(sellPriceFormatted, sellTimeFormatted))
               .build();
      };
   }
}
