package com.jithyan.nabchallengebff.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jithyan.nabchallengebff.data.CryptoPricesDAO;
import com.jithyan.nabchallengebff.data.QuoteDAO;
import com.jithyan.nabchallengebff.model.CryptoPrices;
import com.jithyan.nabchallengebff.model.Quote;

@ExtendWith(MockitoExtension.class)
public class CryptoCurrencyServiceUnitTest {
   private List<String> bitCoinNames = Arrays.asList("LTC", "BTC", "DOGE", "ETC");

   private CryptoCurrencyService service;
   @Mock
   private QuoteDAO quoteDAO;
   @Mock
   private CryptoPricesDAO cryptoPricesDAO;


   @Test
   @DisplayName("getAllUniqueCryptoCurrencyNames() returns a list of unique names")
   public void getAllUniqueCryptoCurrencyNamesReturnsListOfUniqueNames() {
      Set<String> expectedCurrencyNames = new HashSet<>();
      Collections.addAll(expectedCurrencyNames, (String[]) bitCoinNames.toArray());

      List<CryptoPrices> dbData = new ArrayList<>();
      int numNames = bitCoinNames.size();
      for (int i = 0; i < 100; i++) {
         CryptoPrices cp = CryptoPrices.builder()
               .currency(bitCoinNames.get(i % numNames))
               .date(LocalDate.now().toEpochDay())
               .quotes(new ArrayList<Quote>())
               .id(new ObjectId())
               .build();
         dbData.add(cp);
      }

      when(cryptoPricesDAO.findAll()).thenReturn(dbData);
      this.service = new CryptoCurrencyService(quoteDAO, cryptoPricesDAO);

      Set<String> actualCurrencyNames = new HashSet<>(service.getAllUniqueCryptoCurrencyNames());
      Assertions.assertEquals(expectedCurrencyNames, actualCurrencyNames);
   }


   @Test
   @DisplayName("getAllUniqueCryptoCurrencyNames() returns empty list when theres nothing in the DB")
   public void getAllUniqueCryptoCurrencyNamesReturnsEmptyListWhenDbIsEmpty() {
      List<CryptoPrices> dbData = new ArrayList<>();
      when(cryptoPricesDAO.findAll()).thenReturn(dbData);
      this.service = new CryptoCurrencyService(quoteDAO, cryptoPricesDAO);

      List<String> actualCurrencyNames = service.getAllUniqueCryptoCurrencyNames();
      Assertions.assertEquals(0, actualCurrencyNames.size());
   }
}
