import React from 'react';
import { devEvents } from "../Cards"
import { useParams } from 'react-router-dom';

const OneEvent = () => {
  
  const {eventId} = useParams();
  console.log(eventId);
  const currentEvent = devEvents[eventId];
  return (
    <section className='min-h-[calc(100vh-52px-4rem)]'>
      <img src={currentEvent.img} alt="" />
    </section>
  );
};

export default OneEvent;
