import { useEffect, useState } from 'react';
import EventCardSt from '../components/EventCards/Standard/EventCardSt';
import EventCardMi from '../components/EventCards/Minimized/EventCardMi';
import { getAllEvents } from '../services/Events.Services';
import { validateToken } from '../services/Auth.Services';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../state/atoms/tokenState';

export default function Home() {

  const [events, setEvents] = useState([])

  useEffect(() => {
    const fetchEvents = async () => {
      const response = await getAllEvents();
      setEvents(response.data)
    }
    fetchEvents()
  }, [events])

  return (
    <div className="min-h-[calc(100vh-52px-4rem)]">
      <button onClick={() => handleToken(token)}>TOKEEEEN</button>
      <section className="">
        <TitileWithLines title="What's New?" />
        <div className="flex md:flex-row flex-col justify-evenly">
          {events.content && events.content.length > 0 ? (
            <EventCardSt event={events.content[0]} />
          ) : <h1>Loading...</h1>}
          {events.content && events.content.length > 1 ? (
            <EventCardSt event={events.content[1]} />
          ) : <h1>Loading...</h1>}
        </div>
      </section>
      <section className="">
        <TitileWithLines title="Available now" />
      </section>
    </div>
  );
}

function TitileWithLines({ title }) {
  return (
    <span className="w-full grid grid-cols-6 px-default-lg pt-default-lg md:pb-default-xl pb-default-lg items-center">
      <div className="border h-0 border-primary"></div>
      <h1 className="md:title subtitle text-center md:col-span-2 col-span-3">
        {title}
      </h1>
      <div className="border h-0 border-primary md:col-span-3 col-span-2"></div>
    </span>
  );
}
