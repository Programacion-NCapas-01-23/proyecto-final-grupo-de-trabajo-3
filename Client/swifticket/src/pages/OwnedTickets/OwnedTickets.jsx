import React from 'react';
import { devEvents } from '../Cards';
import EventCardSt from '../../components/EventCards/Standard/EventCardSt';

const OwnedTickets = () => {

  const owned = devEvents.slice(4,7)
    
  return (
    <div className="min-h-[calc(100vh-52px-4rem)]">
      <TitileWithLines title="Owned tickets"/>
      <div className='flex flex-wrap w-full justify-evenly overflow-auto'>
      {owned.map((event, index) => (
            <EventCardSt key={index} event={event} />
          ))}
      </div>
    </div>
  );
};

export default OwnedTickets;

function TitileWithLines({ title }) {
  return (
    <span className="w-full grid grid-cols-10 px-default-lg pb-default-lg items-center">
      <div className='border h-0 border-primary'></div>
      <h1 className='md:title subtitle text-center md:col-span-3 col-span-7'>{title}</h1>
      <div className='border h-0 border-primary md:col-span-6 col-span-2'></div>
    </span>
  )
}