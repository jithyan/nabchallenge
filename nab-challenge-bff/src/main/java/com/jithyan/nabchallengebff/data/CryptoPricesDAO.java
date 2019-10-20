package com.jithyan.nabchallengebff.data;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jithyan.nabchallengebff.model.CryptoPrices;

public interface CryptoPricesDAO extends MongoRepository<CryptoPrices, ObjectId> {

   @Query(value = "{'currency': ?0}")
   public List<CryptoPrices> findByCurrencyName(String currencyName);


   @Query(value = "{'currency': ?0, 'date': ?1}", sort = "{'quotes.time': 1}")
   public CryptoPrices findByCurrencyAndDateSortedAscByTime(String currency, long epochDate);

}
