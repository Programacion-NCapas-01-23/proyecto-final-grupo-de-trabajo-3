import React, { useState } from 'react';
import { devEvents } from '../Cards';
import EventCardSt from '../../components/EventCards/Standard/EventCardSt';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../state/atoms/tokenState';
import { getAllEvents } from '../../services/Events.Services';
import { useEffect } from 'react';
import { Pagination } from '../../components/Pagination';

export default function AllEvents() {
  const [page, setPage] = useState(0);
  const [maxPage, setMaxPage] = useState('');
  const [events, setEvents] = useState([]);

  const handleEvents = async () => {
    const response = await getAllEvents(page);

    if (response.status === 200) {
      setMaxPage(response.data.totalPages);
      setEvents(response.data.content);
      console.log(response.data);
    }
  };

  useEffect(() => {
    handleEvents();
  }, [page]);

  return (
    <section className="w-full px-default overflow-y-auto">
      <TitileWithLines title="All Events" />
      <div className="flex flex-wrap items-center justify-evenly">
        {events.map((event, index) => (
          <EventCardSt key={index} event={event} />
        ))}
      </div>
      <div className="flex w-full my-4 justify-center items-center">
        <Pagination page={page} setPage={setPage} limit={maxPage} />
      </div>
    </section>
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
