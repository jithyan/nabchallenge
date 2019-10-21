# UI

A very basic web app that allows you to view the best profit for a specified crypto currency and date.

## Execution

1. Make sure the BFF and MongoDB database is running. Alternatively you can use the mock server (see below).
2. Install dependencies: `yarn build`
3. Start dev server: `yarn start`

## Mock Server

1. `cd mock_server`
2. `yarn install`
3. `yarn start`
4. Open the file located at `nab-challenge-ui\src\shared\api\api.ts`
5. Change `BaseURL` to refer `mockingBaseURL`.

### Usage

- The mock API will expose endpoints at `http://localhost:4000/mocking/`
- The responses can be altered by altering the JSON files in `mock_server\mocks\responses`. Alternatively the request mapping can be altered in the `requests` folder.
