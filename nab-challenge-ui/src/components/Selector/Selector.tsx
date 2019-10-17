import React, { useState } from "react";

const Selector: React.FC = () => {
  const [currencyNames, setCurrencyNames] = useState<string[]>([] as string[]);
  const [currentValue, setCurrentValue] = useState<string>("");

  const onValueSelected = (event: React.FormEvent<HTMLFormElement>) => {
    setCurrentValue(event.currentTarget.value);
  };

  return (
    <div className="ui raised very padded text container segment">
      <CurrencySelector
        currencyNames={[]}
        onCurrencySelected={onValueSelected}
      />
      {currentValue !== "" ? <button>Next</button> : null}
      <h1>{currentValue}</h1>
    </div>
  );
};

interface CurrencySelectorProps {
  currencyNames: string[];
  onCurrencySelected: (event: any) => void;
}
const CurrencySelector: React.FC<CurrencySelectorProps> = ({
  currencyNames,
  onCurrencySelected
}) => {
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
};

interface LoaderProp {
  text: string;
}
const Loader: React.FC<LoaderProp> = ({ text = "Loading" }) => {
  return (
    <div>
      <div className="ui active loader">{text}</div>
    </div>
  );
};

export default Selector;
