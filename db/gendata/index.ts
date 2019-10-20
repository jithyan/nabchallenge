import * as moment from "moment";
import * as fs from "fs";

const cryptos = ["LTC", "BTC", "ETC"];
const dates: Date[] = [];

const date = new Date(2017, 0);
for (var i = 0; i < 365; i++) {
  dates.push(
    moment(date)
      .add(i, "d")
      .toDate()
  );
}

type Quote = { time: number; price: number };
type Doc = { currency: string; date: number; quotes: Quote[] };

function gen_quotes(date: Date): Quote[] {
  const basePrice = Math.random() * 300;
  const quotes: Quote[] = [];
  const dayInMins = 60 * 24;
  var time = date.getTime();

  for (var i = 0; i < dayInMins; i++) {
    const price = parseFloat((basePrice + Math.random() * 30).toFixed(2));
    quotes.push({
      time,
      price
    });
    time = time + 60000;
  }
  return quotes;
}

const docs: Doc[] = [];
cryptos.forEach(function(currency) {
  dates.forEach(function(date) {
    const doc = {
      currency,
      date: date.getTime(),
      quotes: gen_quotes(date)
    };
    docs.push(doc);
  });
});

console.log(docs.length);
fs.writeFileSync("./cryptoprices.json", JSON.stringify(docs));
