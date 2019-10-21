# Database

## Setup

`mongo setup.js`

The above will create a new database called **NAB**, and a collection called **cryptoPrices**. It also defines the schema for the collection.

## Import of Data

Execute the following command after completing the setup:

`mongoimport --jsonArray --db NAB --collection cryptoPrices --file cryptoprices.json`

## Notes

The `gendata` folder contains a simple script for generating the crypto price data.

It was written in Typescript.

To execute:
`ts-node index.ts`

_You will require the Typescript and ts-node packages installed globally via NPM_
