# BFF

## Tech

- Java 8
- Spring Boot 2.2
- JUnit 5 + Mockito + RestAssured

## Build

`mvn clean install`

## Execution

- Make sure the MongoDB instance is running.
- `java -jar target\nabchallenge-bff-0.1.jar`
- This will launch the server on port 50555, and connect to the default MongoDB address at localhost:27017
- The above config can be changed in the `application.properties` file.

## Endpoint

/nab-challenge/v1/best-profit/{currencyName}/{epochDate}

> Returns the optimal buy and sell time for a given crypto currency at a specified date.

/nab-challenge/v1/available-cryptos

> Returns a distinct list of names of crypto currencies in the database, in ascending order.

/nab-challenge/v1/available-dates/{currencyName}

> Returns all the available dates there is price data available for a given crypto currency.

/nab-challeng/v1/crypto-price/{currencyName}?date={epochDate}

> Returns all the quotes (price and time) for a given crypto currency. This can be filtered by an date (epoch Milliseconds, optional).
