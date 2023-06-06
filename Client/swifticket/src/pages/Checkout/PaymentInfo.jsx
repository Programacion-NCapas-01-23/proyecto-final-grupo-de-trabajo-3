import React, { useState } from 'react'
import images from 'react-payment-inputs/images';
import { PaymentInputsWrapper, usePaymentInputs } from 'react-payment-inputs';
import 'react-credit-cards-2/dist/es/styles-compiled.css';
import Cards from 'react-credit-cards-2';


export default function PaymentInfo() {
  const {
    wrapperProps,
    getCardImageProps,
    getCardNumberProps,
    getExpiryDateProps,
    getCVCProps
  } = usePaymentInputs();

  const [state, setState] = useState({
    number: '',
    expiry: '',
    cvc: '',
    name: '',
    focus: '',
  });

  const handleInputChange = (evt) => {
    const { name, value } = evt.target;
    setState((prev) => ({ ...prev, [name]: value }));
    console.log({name, value});
  }

  const handleInputDateChange = (evt) => {
    const {name, value} = evt.target;
    
    const formatedDate = convertDateFormat(value);
    
    setState((prev => ({...prev, [name]: formatedDate})));

  }

  function convertDateFormat(dateString) {
    const [year, month] = dateString.split('-');
    const formattedDate = `${month}${year.slice(-2)}`;
    return formattedDate;
  }

  const handleInputFocus = (evt) => {
    setState((prev) => ({ ...prev, focus: evt.target.name }));
  }

  return (
    <div className='h-screen w-full flex flex-col items-center justify-center'>
      <div className='w-min'>


        {/* TARJETA CHINGONA */}
        <Cards
          number={state.number}
          expiry={state.expiry}
          cvc={state.cvc}
          name={state.name}
          focused={state.focus}
        />

        {/* AQUI ESTAN LOS INPUTS*/}
        <div className='py-default'>
          {/* QUE TIENEN VALIDACIONES CHIDAS */}
          {/* <PaymentInputsWrapper className="w-full text-black mb-default-xs" {...wrapperProps}>
          <svg {...getCardImageProps({ images })} />
          <input {...getCardNumberProps()} />
          <input {...getExpiryDateProps()} />
          <input {...getCVCProps()} />
        </PaymentInputsWrapper> */}


          {/* QUE LE DAN ESTILO A LA TAARJETA */}
          <input className="p-2 text-black w-full rounded-sm my-default-xs" name='number' type="number" onChange={handleInputChange} onFocus={handleInputFocus} placeholder='Card Number' />
          <input className="p-2 text-black w-full rounded-sm" name='name' type="text" onChange={handleInputChange} onFocus={handleInputFocus} placeholder='Card Holder' />
          <div className='grid grid-cols-2 my-default-xs gap-3'>
            <input className="p-2 text-black w-full rounded-sm" name='cvc' type="number" onChange={handleInputChange} onFocus={handleInputFocus} placeholder='CVC' />
            <input className="p-2 text-black w-full rounded-sm" name='expiry' type="month" onChange={handleInputDateChange} onFocus={handleInputFocus} placeholder='Expires' />
          </div>

        </div>


      </div>
    </div>
  )
}
