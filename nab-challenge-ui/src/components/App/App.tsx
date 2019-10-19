import React, { useState } from "react";

import Selector from "../Selector";
import Result from "../Result";
import { ResultProps } from "../Result/Result";

const App: React.FC = () => {
  const [selectedValues, setSelectedValues] = useState<ResultProps>({
    currencyName: "",
    date: ""
  });

  const showResult = selectedValues.currencyName !== "";

  return (
    <div className="ui container">
      <h1 className="ui top attached centered header">
        Crypto Currency Trading Daily Profit Maximizer
      </h1>
      <h3 className="ui centered header">
        Analyzes historical price data for crypto currencies to determine the
        best buy and sell times for a day.
      </h3>
      <div className="ui segment" />
      {showResult ? (
        <Result {...selectedValues} />
      ) : (
        <Selector
          onSelection={(selectedCurrency, selectedDate) => {
            setSelectedValues({
              currencyName: selectedCurrency,
              date: selectedDate
            });
          }}
        />
      )}
    </div>
  );
};

export default App;
