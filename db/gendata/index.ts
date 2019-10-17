import * as moment from "moment";
import * as fs from "fs";

const cryptos = ["LTC", "BTC", "ETC"];
const dates: Date[] = [];

var date = new Date(2017, 0);
for (var i = 0; i < 365; i++) {
    dates.push(date);
    date = moment(date)
        .add(1, "d")
        .toDate();
}

type Quote = { time: number, price: number };
type Doc = { currency: string, date: Date, quotes: Quote[] };

function gen_quotes(date: Date): Quote[] {
    const quotes: Quote[] = [];
    const dayInMins = 60 * 24;
    var time = date;

    for (var i = 0; i < dayInMins; i++) {
        const price = parseFloat((Math.random() * 100).toFixed(2));
        const unixTime = parseInt(Math.floor(time.getTime() / 1000).toFixed(0));
        time = moment(date)
            .add(1, "m")
            .toDate();
        quotes.push({
            time: unixTime,
            price
        })
    }
    return quotes;
}

const docs: Doc[] = [];
cryptos.forEach(function (currency) {
    dates.forEach(function (date) {
        const doc = {
            currency,
            date,
            quotes: gen_quotes(date)
        };
        docs.push(doc);
    });
});

fs.writeFileSync("./cryptoprices.json", JSON.stringify(docs));
