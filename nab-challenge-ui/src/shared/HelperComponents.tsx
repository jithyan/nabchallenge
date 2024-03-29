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

export const ErrorMessage: React.FC<{
  msg: string;
  onClose: () => void;
  open: boolean;
}> = ({ msg, onClose, open }) => {
  return !open ? null : (
    <div className="ui negative message">
      <i className="close icon" onClick={onClose}></i>
      <div className="header">{msg}</div>
    </div>
  );
};
