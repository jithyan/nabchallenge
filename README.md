# NAB Coding Challenge

Find the best profit possible in a single day given historical crypto currency price data.

## Comprises of 3 projects

- **db**: A MongDB database, consisting of a schema definition file and JSON data to import.
- **nab-challenge-bff**: A Java 8 + Spring Boot RESTful API
- **nab-challenge-ui**: A Typescript + React Web App

## Simplifying assumptions / Limitations

- Null path/request variables will return an empty list / dummy data
- There will always be quotes for a currency and no dirty data.
- Didn't implement pagination.
- Skipped integration testing for the BFF and any component testing for UI due to time constraints.
- Didn't implement any standard exception handling for BFF.
