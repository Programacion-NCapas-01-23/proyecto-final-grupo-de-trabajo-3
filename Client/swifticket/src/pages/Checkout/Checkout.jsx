import React from 'react'
import PaymentInfo from './PaymentInfo'
import TitleWithLines from '../../components/TitleWithLines'

export default function Checkout() {
  return (
    <div className='min-h-[calc(100vh-52px-4rem)]'>
      <TitleWithLines title="Payment"/>
      <PaymentInfo/>
    </div>
  )
}