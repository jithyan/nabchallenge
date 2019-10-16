conn = new Mongo();
db = conn.getDB("NAB");

db.createCollection("cryptoPrices", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["currency", "date"],
            properties: {
                currency: {
                    bsonType: "string",
                },
                date: {
                    bsonType: "date",
                },
                quotes: {
                    bsonType: ["array"],
                    required: ["time", "price"],
                    properties: {
                        time: {
                            bsonType: "long"
                        },
                        price: {
                            bsonType: "decimal"
                        }
                    }
                }
            }
        }
    }
});

db.cryptoPrices.createIndex(
    { currency: 1, date: -1 } ,
);