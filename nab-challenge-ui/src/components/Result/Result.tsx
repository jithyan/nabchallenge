import React, { useState, useEffect } from "react";

import BestProfitDisplay from "./BestProfitDisplay";
import PriceGraph, { DataPoints } from "./PriceGraph";

import api from "../../shared/api/api";
import {
  ApiMultiRecordResponse,
  Quote,
  BestProfitResponse
} from "../../shared/api/api.model";
import { Loader, ErrorMessage } from "../../shared/HelperComponents";

export interface ResultProps {
  currencyName: string;
  dateAsEpoch: number;
}

const Result: React.FC<ResultProps> = ({ currencyName, dateAsEpoch }) => {
  const [graphData, setGraphData] = useState<DataPoints[]>([] as DataPoints[]);
  const [profitData, setProfitData] = useState<BestProfitResponse>({
    dateFormatted: "",
    buy: { price: "", time: "" },
    sell: { price: "", time: "" },
    profit: ""
  });

  const [showError, setShowError] = useState(false);

  useEffect(() => {
    api
      .get(`/crypto-price/${currencyName}?date=${dateAsEpoch}`)
      .then(response => {
        const json: ApiMultiRecordResponse<Quote> = response.data;
        const dataPoints: DataPoints[] = json.records.map(q => {
          return { x: q.time, y: q.price };
        });
        setGraphData(dataPoints);
      })
      .catch(error => {
        setShowError(true);
      });

    api
      .get(`/best-profit/${currencyName}/${dateAsEpoch}`)
      .then(response => {
        setProfitData(response.data as BestProfitResponse);
      })
      .catch(error => {
        setShowError(true);
      });
  }, [currencyName, dateAsEpoch]);

  const dataLoaded = graphData.length !== 0 && profitData.dateFormatted !== "";

  return (
    <div className="ui equal width center aligned grid">
      <ErrorMessage
        msg="Error contacting API"
        open={showError}
        onClose={() => setShowError(false)}
      />
      {dataLoaded ? (
        <>
          <div className="row">
            <div className="column">
              <PriceGraph
                title={`Price Fluctuation for ${currencyName} on ${profitData.dateFormatted}`}
                data={graphData}
                width={900}
                height={400}
              />
            </div>
          </div>
          <div className="row">
            <div className="column">
              <BestProfitDisplay
                profit={profitData.profit}
                date={profitData.dateFormatted}
                currencyName={currencyName}
                buyPrice={profitData.buy.price}
                sellPrice={profitData.sell.price}
                buyTime={profitData.buy.time}
                sellTime={profitData.sell.time}
              />
            </div>
          </div>
        </>
      ) : (
        <Loader text="Loading data" />
      )}
    </div>
  );
};

export default Result;
