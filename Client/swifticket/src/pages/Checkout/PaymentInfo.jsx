import React, { useState } from "react";
import "react-credit-cards-2/dist/es/styles-compiled.css";
import Cards from "react-credit-cards-2";
import { shoppingCartState } from "../../state/atoms/shoppingCartState";
import { useRecoilState } from "recoil";

export default function PaymentInfo() {

  const [state, setState] = useState({
    number: "",
    expiry: "",
    cvc: "",
    name: "",
    focus: "",
  });

  const handleInputChange = (evt) => {
    const { name, value } = evt.target;
    setState((prev) => ({ ...prev, [name]: value }));
    console.log({ name, value });
  };

  const handleInputNumberChange = (evt) => {
    const { name, value } = evt.target;
    const truncatedValue = value.slice(0, 16);
    setState((prev) => ({ ...prev, [name]: truncatedValue }));
    console.log({ name, value: truncatedValue });
  };

  const handleInputDateChange = (evt) => {
    const { name, value } = evt.target;
    const formatedDate = convertDateFormat(value);
    setState((prev) => ({ ...prev, [name]: formatedDate }));
  };

  function convertDateFormat(dateString) {
    const [year, month] = dateString.split("-");
    const formattedDate = `${month}${year.slice(-2)}`;
    return formattedDate;
  }

  const handleInputFocus = (evt) => {
    setState((prev) => ({ ...prev, focus: evt.target.name }));
  };

  return (
    <div className="w-full flex flex-col items-center justify-center md:pt-default-xl">
      <div className="w-min">
        {/* TARJETA CHINGONA */}
        <Cards
          number={state.number}
          expiry={state.expiry}
          cvc={state.cvc}
          name={state.name}
          focused={state.focus}
        />

        <div className="py-default md:min-w-max">
          <input
            className="p-2 text-black w-full rounded-sm my-default-xs"
            name="number"
            type="number"
            onChange={handleInputNumberChange}
            onFocus={handleInputFocus}
            placeholder="Card Number"
          />
          <input
            className="p-2 text-black w-full rounded-sm"
            name="name"
            type="text"
            autoComplete='off'
            onChange={handleInputChange}
            onFocus={handleInputFocus}
            placeholder="Card Holder"
          />
          <div className="grid grid-cols-2 my-default-xs gap-3">
            <input
              className="p-2 text-black w-full rounded-sm"
              name="cvc"
              type="password"
              onChange={handleInputChange}
              onFocus={handleInputFocus}
              placeholder="CVC"
            />
            <input
              className="p-2 text-black w-full rounded-sm"
              name="expiry"
              type="month"
              onChange={handleInputDateChange}
              onFocus={handleInputFocus}
              placeholder="Expires"
            />
          </div>
        </div>
      </div>
    </div>
  );
}
