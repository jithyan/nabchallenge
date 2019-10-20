package com.jithyan.nabchallengebff.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jithyan.nabchallengebff.data.CryptoPricesDAO;
import com.jithyan.nabchallengebff.data.QuoteDAO;
import com.jithyan.nabchallengebff.model.BestProfit;
import com.jithyan.nabchallengebff.model.CryptoPrices;
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
      return cryptoPricesDAO.findAll().stream()
            .unordered()
            .map(CryptoPrices::getCurrency)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
   }


   @Cacheable
   public List<Long> getAllUniqueDatesForGivenCryptoCurrency(String currencyName) {
      return currencyName == null
            ? Collections.emptyList()
            : cryptoPricesDAO.findByCurrencyName(currencyName).stream()
                  .unordered()
                  .map(CryptoPrices::getDate)
                  .distinct()
                  .sorted()
                  .collect(Collectors.toList());
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
