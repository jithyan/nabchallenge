package com.jithyan.nabchallengebff.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jithyan.nabchallengebff.constants.Constants;
import com.jithyan.nabchallengebff.data.CryptoPricesDAO;
import com.jithyan.nabchallengebff.model.BestProfit;
import com.jithyan.nabchallengebff.model.CryptoPrices;
import com.jithyan.nabchallengebff.model.Quote;

@Service
@CacheConfig(cacheNames = { "crypto" })
public class CryptoCurrencyService {
   private final CryptoPricesDAO cryptoPricesDAO;

   @Autowired
   public CryptoCurrencyService(CryptoPricesDAO cryptoPricesDAO) {
      this.cryptoPricesDAO = cryptoPricesDAO;
   }


   //Extremely slow implementation, ideally should do all the processing
   //in the DB, but I'm not too familiar performing MongoDB queries in Java.
   @Cacheable
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
      return currencyName == null
            ? Collections.emptyList()
            : cryptoPricesDAO.findByCurrencyName(currencyName).stream()
                  .unordered()
                  .flatMap(cp -> cp.getQuotes().stream())
                  .sorted((x, y) -> Long.compare(x.getTime(), y.getTime()))
                  .collect(Collectors.toList());
   }


   @Cacheable
   public List<Quote> getAllQuotesForCurrency(String currencyName, long epochDate) {
      return currencyName == null
            ? Collections.emptyList()
            : cryptoPricesDAO.findByCurrencyAndDateSortedAscByTime(currencyName, epochDate)
                  .getQuotes();
   }


   @Cacheable
   public BestProfit calculateBestProfitForCurrencyGivenDate(String currencyName, long epochDate) {
      CryptoPrices retrievedCryptoPrices = cryptoPricesDAO
            .findByCurrencyAndDateSortedAscByTime(currencyName, epochDate);

      return retrievedCryptoPrices == null
            ? BestProfit.noBestProfit(LocalDateTime
                  .ofEpochSecond(epochDate / 1000L, 0,
                        Constants.CURRENT_ZONE_ID)
                  .format(DateTimeFormatter.ofPattern("dd-MMM-uuuu")))
            : retrievedCryptoPrices.getBestProfit().get();
   }


}
