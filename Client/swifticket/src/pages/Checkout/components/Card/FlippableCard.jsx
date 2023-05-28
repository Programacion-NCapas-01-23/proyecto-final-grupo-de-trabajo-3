import React from 'react'

export default function FlippableCard() {

    let cardBackgroundNumber = Math.floor(Math.random() * (25 - 1 + 1)) + 1;
    let cardBgUrl = `/card-background/${cardBackgroundNumber}.jpeg`

  return (
    <div className='border border-red-500'>
        <img className='h-80 brightness-50' src={cardBgUrl} alt="card" />
        FlippableCard
    </div>
  )
}
