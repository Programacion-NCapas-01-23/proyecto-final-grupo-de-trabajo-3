import React from 'react'
import QRPlaceholder from '../../assets/QRPlaceholder.png';

export default function ReceiveQR() {
  return (
    <div className='min-h-[calc(100vh-52px-3.5rem)]'>
      <TitileWithLines title={"Receive ticket"} />

      <main className='flex flex-col justify-center items-center h-[72vh]'>
        <DisplayQR />
        <Description />
      </main>
    </div>
  )
}

function TitileWithLines({ title }) {
  return (
    <span className="w-full grid grid-cols-10 px-default-lg pb-default-lg items-center">
      <div className='border h-0 border-primary'></div>
      <h1 className='md:title subtitle text-center md:col-span-3 col-span-7'>{title}</h1>
      <div className='border h-0 border-primary md:col-span-6 col-span-2'></div>
    </span>
  )
}

function DisplayQR() {
  return (
    <img className='v-[35vw] h-[35vh] object-contain' src={QRPlaceholder}></img>
  )
}

function Description() {
  return (
    <div className='w-[90vw] sm:w-[20vw] mt-[3vh]'>
      <p className='heading-md'>Tell the owner of the ticket to scan this QR to begin the transaction</p>
    </div>
  )
}