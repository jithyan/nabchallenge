import React from "react";

import BestProfitDisplay from './BestProfitDisplay';
import PriceGraph from './PriceGraph';

const props:any = {
    data: [
        { x: 0, y: 12.3 },
        { x: 1, y: 10.2 },
        { x: 2, y: 13.3 },
        { x: 3, y: 11.5 },
        { x: 4, y: 14 },
        { x: 5, y: 11.9 }
      ],
      width: 600,
      height: 300,
      title: "Currency Price Fluctuation"
}

const profitProps:any =  {
    buyTime: "12:30pm",
    sellTime: "1:55pm",
    sellPrice: "$12.00",
    buyPrice: "$15.00",
    profit: "$3.00",
    currencyName: "ETC",
    date: "12 December 2018"
}

const Result: React.FC = () => {
  return (
    <div className="ui equal width center aligned grid">

    <div className="row">
      <div className="column">
        <PriceGraph {...props} />
      </div>
    </div>

    <div className="row">
      <div className="column">
        <BestProfitDisplay {...profitProps}/>
      </div>
    </div>

  </div>
  );
  
};

export default Result;
