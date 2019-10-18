import React from "react";

import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "react-datepicker/dist/react-datepicker-cssmodules.css";

interface DateSelector {
  selectedDate: Date;
  onDateSelected: (date: Date) => void;
  dates: Date[];
}
const DateSelector: React.FC<DateSelector> = ({
  selectedDate,
  onDateSelected,
  dates
}) => {
  return (
    <DatePicker
      selected={selectedDate}
      onChange={onDateSelected}
      includeDates={dates}
      placeholderText="This only includes today and tomorrow"
    />
  );
};

export default DateSelector;
