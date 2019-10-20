package com.jithyan.nabchallengebff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jithyan.nabchallengebff.data.CryptoPricesDAO;
import com.jithyan.nabchallengebff.data.QuoteDAO;
import com.jithyan.nabchallengebff.model.BestProfit;
import com.jithyan.nabchallengebff.model.Quote;

@Service
@CacheConfig(cacheNames = { "crypto" })
public class CryptoCurrencyService {
   private final QuoteDAO quoteDAO;
   private final CryptoPricesDAO cryptoPricesDAO;

   @Autowired
   public CryptoCurrencyService(QuoteDAO quoteDAO, CryptoPricesDAO cryptoPricesDAO) {
      this.quoteDAO = quoteDAO;
      this.cryptoPricesDAO = cryptoPricesDAO;
   }


   public List<String> getAllUniqueCryptoCurrencyNames() {
      return null;
   }


   @Cacheable
   public List<String> getAllUniqueDatesForGivenCryptoCurrency(String currencyName) {
      return null;
   }


   public List<Quote> getAllQuotesForCurrency(String currencyName) {
      return null;
   }


   @Cacheable
   public List<Quote> getAllQuotesForCurrency(String currencyName, long epochDate) {
      return null;
   }


   @Cacheable
   public BestProfit calculateBestProfitForCurrencyGivenDate(String currencyName, long epochDate) {
      return null;
   }


}
