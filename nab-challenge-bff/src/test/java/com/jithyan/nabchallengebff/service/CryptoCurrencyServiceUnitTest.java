package com.jithyan.nabchallengebff.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jithyan.nabchallengebff.data.CryptoPricesDAO;
import com.jithyan.nabchallengebff.model.CryptoPrices;
import com.jithyan.nabchallengebff.model.Quote;

@ExtendWith(MockitoExtension.class)
public class CryptoCurrencyServiceUnitTest {
   private List<String> sortedCurrencyNames = Arrays.asList("LTC", "BTC", "DOGE", "ETC");
   {
      Collections.sort(sortedCurrencyNames);
   }
   private List<String> unsortedDates = Arrays.asList("2018-12-10", "2018-12-11", "2018-10-09",
         "2017-01-30", "2019-02-27");
   private List<String> unsortedTimes = Arrays.asList("2018-12-10T10:30:55", "2018-12-10T18:40:50",
         "2018-12-10T10:31:55", "2018-12-10T11:30:00", "2018-12-10T09:05:05");

   private List<CryptoPrices> cryptoPriceDbData;
   private CryptoCurrencyService service;

   @Mock
   private CryptoPricesDAO cryptoPricesDAO;

   @BeforeEach
   public void setup() {
      cryptoPriceDbData = new ArrayList<>();
   }


   @Test
   @DisplayName("getAllUniqueCryptoCurrencyNames returns a list of unique names in ascending order")
   public void getAllUniqueCryptoCurrencyNamesReturnsListOfAscendingOrderedUniqueNames() {
      int numNames = sortedCurrencyNames.size();
      for (int i = 0; i < 100; i++) {
         CryptoPrices cp = CryptoPrices.builder()
               .currency(sortedCurrencyNames.get(i % numNames))
               .date(LocalDate.now().toEpochDay())
               .quotes(new ArrayList<Quote>())
               .id(new ObjectId())
               .build();
         cryptoPriceDbData.add(cp);
      }

      when(cryptoPricesDAO.findAll()).thenReturn(cryptoPriceDbData);
      this.service = new CryptoCurrencyService(cryptoPricesDAO);

      List<String> actualCurrencyNames = service.getAllUniqueCryptoCurrencyNames();
      Assertions.assertEquals(sortedCurrencyNames, actualCurrencyNames);
   }


   @Test
   @DisplayName("getAllUniqueCryptoCurrencyNames returns empty list when theres nothing in the DB")
   public void getAllUniqueCryptoCurrencyNamesReturnsEmptyListWhenDbIsEmpty() {
      when(cryptoPricesDAO.findAll()).thenReturn(cryptoPriceDbData);
      this.service = new CryptoCurrencyService(cryptoPricesDAO);

      List<String> actualCurrencyNames = service.getAllUniqueCryptoCurrencyNames();
      Assertions.assertEquals(0, actualCurrencyNames.size());
   }


   @Test
   @DisplayName("getAllUniqueDatesForGivenCryptoCurrency returns a list of unique dates in ascending order")
   public void getAllUniqueDatesForGivenCryptoCurrencyReturnsListOfAscendingOrderedUniqueDates() {
      String givenCurrency = "DOGE";
      int numDates = unsortedDates.size();
      List<Long> expectedEpochDates = unsortedDates.stream()
            .unordered()
            .map(d -> LocalDate.parse(d).toEpochDay())
            .sorted()
            .collect(Collectors.toList());

      for (int i = 0; i < 50; i++) {
         CryptoPrices cp = CryptoPrices.builder()
               .currency(givenCurrency)
               .date(expectedEpochDates.get(i % numDates))
               .quotes(new ArrayList<Quote>())
               .id(new ObjectId())
               .build();

         cryptoPriceDbData.add(cp);
      }

      when(cryptoPricesDAO.findByCurrencyName(givenCurrency)).thenReturn(cryptoPriceDbData);
      this.service = new CryptoCurrencyService(cryptoPricesDAO);

      List<Long> actualEpochDates = service.getAllUniqueDatesForGivenCryptoCurrency(givenCurrency);
      Assertions.assertEquals(expectedEpochDates, actualEpochDates);
   }


   @Test
   @DisplayName("getAllUniqueDatesForGivenCryptoCurrency returns empty list when theres nothing in the DB")
   public void getAllUniqueDatesForGivenCryptoCurrencyReturnsEmptyListWhenDbEmpty() {
      String givenCurrency = "DOGE";

      when(cryptoPricesDAO.findByCurrencyName(givenCurrency)).thenReturn(cryptoPriceDbData);
      this.service = new CryptoCurrencyService(cryptoPricesDAO);

      List<Long> actualEpochDates = service.getAllUniqueDatesForGivenCryptoCurrency(givenCurrency);
      Assertions.assertEquals(0, actualEpochDates.size());
   }


   @Test
   @DisplayName("getAllQuotesForCurrency(currencyName) returns all quotes for a given currency in ascending order")
   public void getAllQuotesForCurrencyReturnsAllQuotesForGivenCurrencyInAscendingOrder() {
      String givenCurrency = "DOGE";

      int numDates = unsortedDates.size();
      List<Long> epochDates = unsortedDates.stream()
            .unordered()
            .map(d -> LocalDate.parse(d).toEpochDay())
            .sorted()
            .collect(Collectors.toList());

      for (int i = 0; i < 50; i++) {
         CryptoPrices cp = CryptoPrices.builder()
               .currency(givenCurrency)
               .date(epochDates.get(i % numDates))
               .quotes(generateRandomQuotesOrderedByTime())
               .id(new ObjectId())
               .build();

         cryptoPriceDbData.add(cp);
      }

      when(cryptoPricesDAO.findByCurrencyName(givenCurrency)).thenReturn(cryptoPriceDbData);
      this.service = new CryptoCurrencyService(cryptoPricesDAO);

      List<Quote> expectedQuotes = cryptoPriceDbData
            .stream()
            .flatMap(cp -> cp.getQuotes().stream())
            .sorted((x, y) -> Long.compare(x.getTime(), y.getTime()))
            .collect(Collectors.toList());


      List<Quote> actualQuotes = service.getAllQuotesForCurrency(givenCurrency);
      Assertions.assertEquals(expectedQuotes, actualQuotes);
   }


   @Test
   @DisplayName("calculateBestProfitForCurrencyGivenDate(date) returns No Profit for empty quotes list")
   public void calculateBestProfitForCurrencyGivenDateReturnsNoProfitForEmptyQuotes() {
      String givenCurrency = "LTC";
      long givenDate = LocalDate.parse(unsortedDates.get(0)).toEpochDay();

      CryptoPrices cp = CryptoPrices.builder()
            .currency("LTC")
            .date(LocalDate.parse(unsortedDates.get(0)).toEpochDay())
            .quotes(new ArrayList<Quote>())
            .id(new ObjectId())
            .build();

      when(cryptoPricesDAO.findByCurrencyAndDateSortedAscByTime(givenCurrency, givenDate))
            .thenReturn(cp);
      service = new CryptoCurrencyService(cryptoPricesDAO);

      String expectedProfit = "Can only sell at a loss today";
      String actualProfit = service
            .calculateBestProfitForCurrencyGivenDate(givenCurrency, givenDate).getProfit();

      Assertions.assertEquals(expectedProfit, actualProfit);

   }


   @Test
   @DisplayName("calculateBestProfitForCurrencyGivenDate(date) returns No Profit when the prices for the day only fall")
   public void calculateBestProfitForCurrencyGivenDateReturnsNoProfitForQuotePricesDescending() {
      String givenCurrency = "LTC";
      long givenDate = LocalDate.parse(unsortedDates.get(0)).toEpochDay();

      List<BigDecimal> descendingPrices = Arrays.asList(new BigDecimal(10), new BigDecimal(9),
            new BigDecimal(7), new BigDecimal(0));
      List<Quote> quotes = new ArrayList<>();

      long time = 1000L;
      int i = 1;
      for (BigDecimal price : descendingPrices) {
         quotes.add(new Quote(price, time * i));
         i++;
      }

      CryptoPrices cp = CryptoPrices.builder()
            .currency("LTC")
            .date(LocalDate.parse(unsortedDates.get(0)).toEpochDay())
            .quotes(quotes)
            .id(new ObjectId())
            .build();

      when(cryptoPricesDAO.findByCurrencyAndDateSortedAscByTime(givenCurrency, givenDate))
            .thenReturn(cp);
      service = new CryptoCurrencyService(cryptoPricesDAO);

      String expectedProfit = "Can only sell at a loss today";
      String actualProfit = service
            .calculateBestProfitForCurrencyGivenDate(givenCurrency, givenDate).getProfit();

      Assertions.assertEquals(expectedProfit, actualProfit);

   }


   @Test
   @DisplayName("calculateBestProfitForCurrencyGivenDate(date) returns 0.00 profit when the prices are stagnant")
   public void calculateBestProfitForCurrencyGivenDateReturnsZeroProfitForQuotePricesEqual() {
      String givenCurrency = "LTC";
      long givenDate = LocalDate.parse(unsortedDates.get(0)).toEpochDay();

      List<BigDecimal> descendingPrices = Arrays.asList(new BigDecimal(10), new BigDecimal(10),
            new BigDecimal(10), new BigDecimal(10));
      List<Quote> quotes = new ArrayList<>();

      long time = 1000L;
      int i = 1;
      for (BigDecimal price : descendingPrices) {
         quotes.add(new Quote(price, time * i));
         i++;
      }

      CryptoPrices cp = CryptoPrices.builder()
            .currency("LTC")
            .date(LocalDate.parse(unsortedDates.get(0)).toEpochDay())
            .quotes(quotes)
            .id(new ObjectId())
            .build();

      when(cryptoPricesDAO.findByCurrencyAndDateSortedAscByTime(givenCurrency, givenDate))
            .thenReturn(cp);
      service = new CryptoCurrencyService(cryptoPricesDAO);

      String expectedProfit = "0.00";
      String actualProfit = service
            .calculateBestProfitForCurrencyGivenDate(givenCurrency, givenDate).getProfit();

      Assertions.assertEquals(expectedProfit, actualProfit);
   }


   @Test
   @DisplayName("calculateBestProfitForCurrencyGivenDate(date) returns the correct profit when the best buy price is not the minium quote price")
   public void calculateBestProfitForCurrencyGivenDateReturnsCorrectProfitForWhenBestBuyPriceIsNotTheMinimum() {
      String givenCurrency = "LTC";
      long givenDate = LocalDate.parse(unsortedDates.get(0)).toEpochDay();

      List<BigDecimal> descendingPrices = Arrays.asList(new BigDecimal(20), new BigDecimal(25),
            new BigDecimal(10), new BigDecimal(41), new BigDecimal(7), new BigDecimal(35),
            new BigDecimal(5), new BigDecimal(35), new BigDecimal(3), new BigDecimal(32));
      List<Quote> quotes = new ArrayList<>();

      long time = 1000L;
      int i = 1;
      for (BigDecimal price : descendingPrices) {
         quotes.add(new Quote(price, time * i));
         i++;
      }

      CryptoPrices cp = CryptoPrices.builder()
            .currency("LTC")
            .date(LocalDate.parse(unsortedDates.get(0)).toEpochDay())
            .quotes(quotes)
            .id(new ObjectId())
            .build();

      when(cryptoPricesDAO.findByCurrencyAndDateSortedAscByTime(givenCurrency, givenDate))
            .thenReturn(cp);
      service = new CryptoCurrencyService(cryptoPricesDAO);

      String expectedProfit = "31.00";
      String actualProfit = service
            .calculateBestProfitForCurrencyGivenDate(givenCurrency, givenDate).getProfit();

      Assertions.assertEquals(expectedProfit, actualProfit);
   }


   private List<Quote> generateRandomQuotesOrderedByTime() {
      return unsortedTimes.stream()
            .unordered()
            .map(d -> LocalDateTime.parse(d)
                  .toEpochSecond(ZoneId.of(ZoneOffset.systemDefault().getId()).getRules()
                        .getOffset(Instant.now())))
            .sorted()
            .map(t -> new Quote(new BigDecimal(Math.random() * 100), t))
            .collect(Collectors.toList());

   }

}
