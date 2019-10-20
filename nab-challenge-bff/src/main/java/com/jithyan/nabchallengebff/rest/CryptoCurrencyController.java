package com.jithyan.nabchallengebff.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jithyan.nabchallengebff.model.BestProfit;
import com.jithyan.nabchallengebff.model.MultiRecordResponse;
import com.jithyan.nabchallengebff.model.Quote;

@RestController("/v1")
public class CryptoCurrencyController {

   @GetMapping("/best-profit/{epochDate}")
   public BestProfit bestProfit(@PathVariable(required = true) Long epochDate) {
      return null;
   }


   @GetMapping("/available-cryptos")
   public MultiRecordResponse<String> availableCryptoCurrencies() {
      return null;
   }


   @GetMapping("/available-dates/{currencyName}")
   public MultiRecordResponse<Long> availableDatesForCryptoCurrency(
         @PathVariable(required = true) String currencyName) {
      return null;
   }


   @GetMapping("/crypto-price/{currencyName}")
   public MultiRecordResponse<Quote> quotesForCryptoCurrency(
         @PathVariable(required = true) String currencyName,
         @RequestParam(required = false) Long epochDate) {
      return null;
   }
}
