import React, { useState } from 'react'
import PaymentInfo from './PaymentInfo'
import OrderReview from './OrderReview';

export default function Checkout() {
  const [isPaying, setIsPaying] = useState(false);
  
  return (
    <div className='min-h-[calc(100vh-52px-4rem)]'>
      <TitileWithLines title={isPaying ? "Payment" : "Checkout"}/>
      {isPaying ? <PaymentInfo /> : <OrderReview/>}
      <div className='flex w-full items-center md:justify-center md:gap-28 justify-evenly'>
        <button className='subaction-button'> {isPaying ? "Cancel" : "Go Back"} </button>
        <button onClick={() => {setIsPaying(!isPaying)}} className='action-button'> {isPaying ? "Pay Now" : "Continue"} </button>
      </div>
    </div>
  )
}

function TitileWithLines({ title }) {
  return (
    <span className="w-full grid grid-cols-6 px-default-lg pt-default-lg md:pb-default-xl pb-default-lg items-center">
      <div className='border h-0 border-primary'></div>
      <h1 className='md:title subtitle text-center md:col-span-1 col-span-2'>{title}</h1>
      <div className='border h-0 border-primary md:col-span-4 col-span-3'></div>
    </span>
  )
}