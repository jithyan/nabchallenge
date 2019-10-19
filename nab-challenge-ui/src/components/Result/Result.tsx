import React, { useState, useEffect } from "react";

import BestProfitDisplay, { BestProfitDisplayProps } from "./BestProfitDisplay";
import PriceGraph, { DataPoints } from "./PriceGraph";

import api from "../../shared/api/api";
import {
  ApiMultiRecordResponse,
  Quote,
  BestProfitResponse
} from "../../shared/api/api.model";
import { Loader, ErrorMessage } from "../../shared/HelperComponents";

const props: any = {
  data: [
    { x: 0, y: 12.3 },
    { x: 1, y: 10.2 },
    { x: 2, y: 13.3 },
    { x: 3, y: 11.5 },
    { x: 4, y: 14 },
    { x: 5, y: 11.9 }
  ],
  title: "Currency Price Fluctuation"
};

export interface ResultProps {
  currencyName: string;
  date: string;
}

const Result: React.FC<ResultProps> = ({ currencyName, date }) => {
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
      .get(`/crypto-price/${currencyName}?date=${date}`)
      .then(response => {
        const json: ApiMultiRecordResponse<Quote> = response.data;
        const dataPoints: DataPoints[] = json.records.map(q => {
          return { x: q.unixTimestamp, y: q.price };
        });
        setGraphData(dataPoints);
      })
      .catch(error => {
        setShowError(true);
      });

    api
      .get(`/best-profit/${currencyName}/${date}`)
      .then(response => {
        setProfitData(response.data as BestProfitResponse);
      })
      .catch(error => {
        setShowError(true);
      });
  }, []);

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
                {...props}
                data={graphData}
                style={{ display: "flex", justifyContent: "center" }}
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
