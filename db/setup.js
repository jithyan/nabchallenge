conn = new Mongo();
db = conn.getDB("NAB");

db.createCollection("cryptoPrices", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      properties: {
        currency: {
          bsonType: "string"
        },
        date: {
          bsonType: "date"
        },
        quotes: {
          bsonType: "array",
          items: {
            $jsonSchema: {
              bsonType: "object",
              properties: {
                time: {
                  bsonType: "date"
                },
                price: {
                  bsonType: "decimal"
                }
              }
            }
          }
        }
      }
    }
  }
});

db.cryptoPrices.createIndex({ currency: 1, date: -1, "quotes.time": 1 });
