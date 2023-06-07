import React from 'react';
import { devEvents } from "../Cards"
import { useParams } from 'react-router-dom';
import Landing from '../../Landing';

const OneEvent = () => {

  const { eventId } = useParams();
  console.log(eventId);
  const currentEvent = devEvents[eventId];
  if (currentEvent != undefined)
    return (
      <section className={`min-h-[calc(100vh-52px-4rem)] bg-contain bg-no-repeat`} style={{ backgroundImage: `url(${currentEvent.img})` }}>
      </section>

    );
  else
  return (
    <Landing/>
  )
};

export default OneEvent;
