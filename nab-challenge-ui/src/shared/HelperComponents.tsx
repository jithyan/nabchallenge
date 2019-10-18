import React from "react";

interface LoaderProp {
  text: string;
}

export const Loader: React.FC<LoaderProp> = ({ text = "Loading" }) => {
  return (
    <div>
      <div className="ui active loader">{text}</div>
    </div>
  );
};
