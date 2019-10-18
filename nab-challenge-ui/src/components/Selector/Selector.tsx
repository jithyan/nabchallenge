import React, { useState, useEffect } from "react";
import api from "../../shared/api";

import DateSelector from "./DateSelector";
import CurrencySelector from "./CurrencySelector";
import { ErrorMessage } from "../../shared/HelperComponents";
import { ApiMultiRecordResponse } from "../../shared/api.model";

export const Selector: React.FC = () => {
  const [currencyNames, setCurrencyNames] = useState<string[]>([] as string[]);
  const [showError, setShowError] = useState(false);

  useEffect(() => {
    api
      .get("/available-cryptos")
      .then(response => {
        const json: ApiMultiRecordResponse<string> = response.data;
        setCurrencyNames(json.records);
      })
      .catch(error => setShowError(true));
  }, []);

  const [selectedCurrency, setCurrentValue] = useState<string>("");

  const [dates, setDates] = useState<Date[]>([]);
  useEffect(() => {
    api.get(`/available-dates/${selectedCurrency}`).then(response => {
      const json: ApiMultiRecordResponse<string> = response.data;
      const dates = json.records.map(date => new Date(date));
      setDates(dates);
    });
  }, [selectedCurrency]);

  const [selectedDate, setSelectedDate] = useState<Date>(dates[0]);

  const onDateSelected = (date: Date) => setSelectedDate(date);

  const onValueSelected = (event: React.FormEvent<HTMLFormElement>) => {
    setCurrentValue(event.currentTarget.value);
  };

  return (
    <div className="ui raised very padded text container segment">
      {showError && (
        <ErrorMessage
          msg="Error contacting API"
          onClose={() => setShowError(false)}
        />
      )}
      {selectedCurrency !== "" ? (
        <>
          <DateSelector
            selectedDate={selectedDate}
            onDateSelected={onDateSelected}
            dates={dates}
          />
        </>
      ) : (
        <>
          <CurrencySelector
            currencyNames={currencyNames}
            onCurrencySelected={onValueSelected}
          />
          {selectedCurrency !== "" ? <button>Next</button> : null}
          <h1>{selectedCurrency}</h1>
        </>
      )}
    </div>
  );
};

export default Selector;
