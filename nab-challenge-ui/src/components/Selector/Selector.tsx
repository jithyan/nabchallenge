import React, { useState } from "react";

import DateSelector from "./DateSelector";
import CurrencySelector from "./CurrencySelector";

export const Selector: React.FC = () => {
  const showCurr = false;
  const [currencyNames, setCurrencyNames] = useState<string[]>([] as string[]);
  const [currentValue, setCurrentValue] = useState<string>("");

  const [dates, setDates] = useState<Date[]>([
    new Date("2018-12-10"),
    new Date("2018-12-11")
  ]);
  const [selectedDate, setSelectedDate] = useState<Date>(dates[0]);

  const onDateSelected = (date: Date) => setSelectedDate(date);

  const onValueSelected = (event: React.FormEvent<HTMLFormElement>) => {
    setCurrentValue(event.currentTarget.value);
  };

  return (
    <div className="ui raised very padded text container segment">
      {showCurr ? (
        <>
          <DateSelector
            selectedDate={selectedDate}
            onDateSelected={onDateSelected}
            dates={dates}
          />
          <h1>{selectedDate.toDateString()}</h1>
        </>
      ) : (
        <>
          <CurrencySelector
            currencyNames={["ETC", "Doge"]}
            onCurrencySelected={onValueSelected}
          />
          {currentValue !== "" ? <button>Next</button> : null}
          <h1>{currentValue}</h1>
        </>
      )}
    </div>
  );
};

export default Selector;