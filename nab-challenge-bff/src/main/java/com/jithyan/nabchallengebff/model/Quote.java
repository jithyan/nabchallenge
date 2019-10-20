package com.jithyan.nabchallengebff.model;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("cryptoPrices")
public class Quote {
   private final BigDecimal price;
   private final long time;
}
