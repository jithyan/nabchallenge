import React from "react";
import { Loader } from "../../shared/HelperComponents";

interface CurrencySelectorProps {
  currencyNames: string[];
  onCurrencySelected: (event: any) => void;
}
const CurrencySelector: React.FC<CurrencySelectorProps> = React.memo(
  ({ currencyNames, onCurrencySelected }) => {
    const isLoading = currencyNames.length === 0;
    const options = currencyNames.map(name => (
      <option key={name} value={name}>
        {name}
      </option>
    ));

    return isLoading ? (
      <Loader text="Retrieving currencies" />
    ) : (
      <form className="ui form">
        <select
          onChange={onCurrencySelected}
          className="ui dropdown"
          placeholder="Pick a value"
        >
          <option value="" key="">
            Pick a currency
          </option>
          {options}
        </select>
      </form>
    );
  }
);

export default CurrencySelector;
