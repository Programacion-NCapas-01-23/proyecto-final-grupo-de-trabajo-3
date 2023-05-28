import React from 'react'
import PaymentInputs from './components/PaymentInputs'
import FlippableCard from './components/Card/FlippableCard'

export default function PaymentInfo() {
  return (
    <div className='h-screen w-full flex flex-col items-center justify-center'>
        <FlippableCard/>
        <PaymentInputs/>
    </div>
  )
}
