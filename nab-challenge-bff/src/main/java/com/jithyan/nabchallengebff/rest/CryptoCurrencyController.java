package com.jithyan.nabchallengebff.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jithyan.nabchallengebff.model.BestProfit;
import com.jithyan.nabchallengebff.model.MultiRecordResponse;
import com.jithyan.nabchallengebff.model.Quote;
import com.jithyan.nabchallengebff.service.CryptoCurrencyService;

@RestController
@CrossOrigin
public class CryptoCurrencyController {
   private final CryptoCurrencyService service;

   @Autowired
   public CryptoCurrencyController(CryptoCurrencyService service) {
      this.service = service;
   }


   @GetMapping("/v1/best-profit/{currencyName}/{epochDate}")
   public BestProfit bestProfit(@PathVariable(required = true) Long epochDate,
         @PathVariable(required = true) String currencyName) {
      return service.calculateBestProfitForCurrencyGivenDate(currencyName, epochDate);
   }


   @GetMapping("/v1/available-cryptos")
   public MultiRecordResponse<String> availableCryptoCurrencies() {
      return new MultiRecordResponse<>(service.getAllUniqueCryptoCurrencyNames());
   }


   @GetMapping("/v1/available-dates/{currencyName}")
   public MultiRecordResponse<Long> availableDatesForCryptoCurrency(
         @PathVariable(required = true) String currencyName) {
      return new MultiRecordResponse<>(
            service.getAllUniqueDatesForGivenCryptoCurrency(currencyName));
   }


   @GetMapping("/v1/crypto-price/{currencyName}")
   public MultiRecordResponse<Quote> quotesForCryptoCurrency(
         @PathVariable(required = true) String currencyName,
         @RequestParam(required = false) Long date) {

      return date == null
            ? new MultiRecordResponse<>(service.getAllQuotesForCurrency(currencyName))
            : new MultiRecordResponse<>(service.getAllQuotesForCurrency(currencyName, date));

   }
}
