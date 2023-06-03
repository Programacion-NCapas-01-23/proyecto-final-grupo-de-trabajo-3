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
  }

  const handleInputFocus = (evt) => {
    setState((prev) => ({ ...prev, focus: evt.target.name }));
  }

  return (
    <div className='h-screen w-full flex flex-col items-center justify-center'>

      {/* TARJETA CHINGONA */}
      <Cards
        number={state.number}
        expiry={state.expiry}
        cvc={state.cvc}
        name={state.name}
        focused={state.focus}
      />

      {/* AQUI ESTAN LOS INPUTS*/}
      <div className='p-default'>
      {/* QUE TIENEN VALIDACIONES CHIDAS */}
        <PaymentInputsWrapper className="w-full text-black mb-default-xs" {...wrapperProps}>
          <svg {...getCardImageProps({ images })} />
          <input {...getCardNumberProps()} />
          <input {...getExpiryDateProps()} />
          <input {...getCVCProps()} />
        </PaymentInputsWrapper>


        {/* QUE LE DAN ESTILO A LA TAARJETA */}
        <input className=" w-full p-2 text-black rounded-sm" name='number' onChange={handleInputChange} onFocus={handleInputFocus} placeholder='Card Holder' />
        <div className='grid grid-cols-4 my-default-xs gap-3'>
          <input className=" w-full p-2 col-span-2 text-black rounded-sm" name='name' onChange={handleInputChange} onFocus={handleInputFocus} placeholder='Card Holder' />
          <input className=" w-full p-2 text-black rounded-sm" name='cvc' onChange={handleInputChange} onFocus={handleInputFocus} placeholder='CVC' />
          <input className=" w-full p-2 text-black rounded-sm" name='expiry' onChange={handleInputChange} onFocus={handleInputFocus} placeholder='Expires' />
        </div>

      </div>


    </div>
  )
}
