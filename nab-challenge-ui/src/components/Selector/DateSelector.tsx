import React from "react";

import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "react-datepicker/dist/react-datepicker-cssmodules.css";
import { Loader } from "../../shared/HelperComponents";

interface DateSelector {
  selectedDate: Date | null;
  onDateSelected: (date: Date) => void;
  dates: Date[];
}
const DateSelector: React.FC<DateSelector> = React.memo(
  ({ selectedDate, onDateSelected, dates }) => {
    return selectedDate == null ? (
      <Loader text="Loading dates" />
    ) : (
      <DatePicker
        selected={selectedDate}
        onChange={onDateSelected}
        includeDates={dates}
        placeholderText="Select a date"
        locale="en-AU"
        showMonthYearDropdown
        dateFormatCalendar={"yyyy-MMM-dd"}
      />
    );
  }
);

export default DateSelector;
