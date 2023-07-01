import React, { useState } from 'react'
import PaymentInfo from './PaymentInfo'
import OrderReview from './OrderReview';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { shoppingCartState } from '../../state/atoms/shoppingCartState';

export default function Checkout() {
  const [isPaying, setIsPaying] = useState(false);
  const navigate = useNavigate()
  const shoppingCart = useRecoilValue(shoppingCartState)

  const handleBack = () => {
    if (isPaying)
      setIsPaying(prev => !prev)
    else
      navigate(-1)
  }

  const handleContinue = () => {
    if (!isPaying)
      setIsPaying(true)
    else
      navigate(-1)
  }

  return (
    <div className='min-h-[calc(100vh-52px-3.5rem)]'>
      <TitileWithLines title={isPaying ? "Payment" : "Checkout"} />
      {shoppingCart.length === 0 ? (
        <div className="flex flex-col h-full w-full gap-3 items-center align-middle">
          <img className="w-1/2" src="/src/assets/sad.png" alt="" />
          <p className="text-3xl tracking-wide leading-7 font-bold">Oh no...</p>
          <p className="p-default">
            It looks like your cart is empty. Go and check out the events, so you can buy some tickets!
          </p>
        </div>
      ) : (
        isPaying ? <PaymentInfo /> : <OrderReview />
      )}
      <div className='flex w-full items-center md:justify-center md:gap-28 justify-evenly'>
        {shoppingCart.length === 0 ? (
          <button onClick={() => {navigate("/")}} className='action-button'> See events </button>
        ) : (
          <>
            <button onClick={handleBack} className='subaction-button'> {isPaying ? "Cancel" : "Go Back"} </button>
            <button onClick={handleContinue} className='action-button'> {isPaying ? "Pay Now" : "Continue"} </button>
          </>
        )}
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