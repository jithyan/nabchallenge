import React from "react";

interface BestProfitDisplayProps {
  date: string;
  currencyName: string;
  buyTime: string;
  sellTime: string;
  buyPrice: string;
  sellPrice: string;
  profit: string;
}
const BestProfitDispay: React.FC<BestProfitDisplayProps> = ({
  currencyName,
  date,
  buyTime,
  sellTime,
  buyPrice,
  sellPrice,
  profit
}) => {
  return (
    <div className="ui raised text container segment">
      <h3 className="grey inverted ui centered block header">Best Profit</h3>
      <div className="ui equal width center aligned padded grid">
          
        <div className="row">
          <div className="black column">{date}</div>
        </div>

        <div className="row">
          <div className="black column" style={{ opacity: 0.95 }}>
            {currencyName}
          </div>
        </div>
        <div className="row">
          <div className="green column">Buy</div>
          <div className="orange column">Sell</div>
        </div>

        <div className="row">
          <div className="green column" style={{ opacity: 0.6 }}>
            {buyPrice}
          </div>
          <div className="orange column" style={{ opacity: 0.6 }}>
            {sellPrice}
          </div>
        </div>

        <div className="row">
          <div className="green column" style={{ opacity: 0.6 }}>
            {buyTime}
          </div>
          <div className="orange column" style={{ opacity: 0.6 }}>
            {sellTime}
          </div>
        </div>
        
        <div className="row">
          <div className="red column" style={{ opacity: 0.9 }}>
            {profit}
          </div>
        </div>

      </div>
    </div>
  );
};

export default BestProfitDispay;
