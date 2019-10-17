conn = new Mongo();
db = conn.getDB("NAB");

db.createCollection("cryptoPrices");

db.cryptoPrices.createIndex({ currency: 1, date: -1 });
