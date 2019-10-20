import React, { useState, useEffect } from "react";
import api from "../../shared/api/api";

import DateSelector from "./DateSelector";
import CurrencySelector from "./CurrencySelector";
import { ErrorMessage } from "../../shared/HelperComponents";
import { ApiMultiRecordResponse } from "../../shared/api/api.model";

interface SelectorProps {
  onSelection: (selectedCurrency: string, selectedDateAsEpoch: number) => void;
}

export const Selector: React.FC<SelectorProps> = ({ onSelection }) => {
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
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);

  useEffect(() => {
    if (selectedCurrency !== "") {
      api
        .get(`/available-dates/${selectedCurrency}`)
        .then(response => {
          const json: ApiMultiRecordResponse<number> = response.data;
          const dates = json.records.map(date => new Date(date));
          setDates(dates);
          setSelectedDate(dates[0]);
        })
        .catch(error => setShowError(true));
    }
  }, [selectedCurrency]);

  const onDateSelected = (date: Date) => setSelectedDate(date);

  const onValueSelected = (event: React.FormEvent<HTMLFormElement>) => {
    setCurrentValue(event.currentTarget.value);
  };

  return (
    <div className="ui small inverted teal raised very padded text centered container segment">
      <ErrorMessage
        msg="Error contacting API"
        onClose={() => setShowError(false)}
        open={showError}
      />
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
        </>
      )}
      {selectedCurrency !== "" && selectedDate && (
        <button
          onClick={() => onSelection(selectedCurrency, selectedDate.getTime())}
          className="ui positive button"
        >
          Go
        </button>
      )}
    </div>
  );
};

export default Selector;
