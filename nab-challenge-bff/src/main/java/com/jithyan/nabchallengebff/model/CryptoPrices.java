package com.jithyan.nabchallengebff.model;

import java.util.List;
import java.util.function.Supplier;

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
   public Supplier<BestProfit> calculateBestProfit() {
      return () -> BestProfit.builder().build();
   }
}
