package com.jithyan.nabchallengebff.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jithyan.nabchallengebff.model.Quote;

public interface QuoteDAO extends MongoRepository<Quote, String> {

   @Query(
         value = "{'currency': ?0, 'date': ?1}", sort = "{quotes.time: 1}",
         fields = "{'quotes': 1}")
   public List<Quote> findByCurrencyAndDate(String currency, long epochDate);
}
