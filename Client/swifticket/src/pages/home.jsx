import { useEffect, useState } from 'react';
import EventCardSt from '../components/EventCards/Standard/EventCardSt';
import EventCardMi from '../components/EventCards/Minimized/EventCardMi';
import { getAllEvents } from '../services/Events.Services';
import { validateToken } from '../services/Auth.Services';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../state/atoms/tokenState';

export default function Home() {

  const [newEvents, setNewEvents] = useState([]);
  const [events, setEvents] = useState([]);
  const [page, setPage] = useState(1);
  const [limit, setLimit] = useState(999);

  const handlePreviousPage = () => {
    if (page <= 1) return;
    setPage(page => page - 1);
  }

  const handleNextPage = () => {
    if (page >= limit) return;
    setPage(page => page + 1);
  }

  const fetchNewEvents = async () => {
    const response = await getAllEvents();
    setNewEvents(response.data);
  }

  const fetchEvents = async (page) => {
    const response = await getAllEvents(page);
    // console.log(response);
    setEvents(response.data);
    setLimit(response.data.totalPages);
  }

  useEffect(() => {
    fetchNewEvents();
  }, [])

  useEffect(() => {
    fetchEvents(page);
  }, [page])

  return (
    <div className="min-h-[calc(100vh-52px-3.5rem)]">
      <section className="">
        <TitileWithLines title="What's Next?" />
        <div className="flex md:flex-row flex-col justify-evenly">
          {newEvents.content && newEvents.content.length > 0 ? (
            <EventCardSt event={newEvents.content[0]} />
          ) : <h1>Loading...</h1>}
          {newEvents.content && newEvents.content.length > 1 ? (
            <EventCardSt event={newEvents.content[1]} />
          ) : <h1>Loading...</h1>}
        </div>
      </section>
      <section className="">
        <TitileWithLines title="Available now" />
        <div className='grid grid-cols-2 sm:grid-cols-3 items-center'>
          {events.content && events.content.length > 0 ? (
            events.content.map((event, index) => (
              <EventCardMi key={index} event={event} />
            ))
          ) : <h1> Loading...</h1>
          }
        </div>
        <Pagination page={page} limit={limit} handlePreviousPage={handlePreviousPage} handleNextPage={handleNextPage} />
      </section>
    </div>
  );
}

function TitileWithLines({ title }) {
  return (
    <span className="w-full grid grid-cols-6 px-default-lg pt-default-lg md:pb-default-xl pb-default-lg items-center">
      <div className="border h-0 border-primary"></div>
      <h1 className="md:title text-5xl text-center md:col-span-2 col-span-3">
        {title}
      </h1>
      <div className="border h-0 border-primary md:col-span-3 col-span-2"></div>
    </span>
  );
}

function Pagination({page, limit, handlePreviousPage, handleNextPage}) {
  return (
    <div className="flex flex-wrap w-full justify-center mt-[2vh]">
      {/* next page */}
      { (page > 1) ? 
        <a href="#" className="inline-flex items-center px-4 py-2 text-sm font-medium text-gray-500 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
        onClick={handlePreviousPage}>
          Previous
        </a>
        : "" }
      {/* Current page */}
      <a href="#" className="inline-flex items-center px-4 py-2 ml-3 text-sm font-medium text-gray-500 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
        { page }
      </a>
      {/* previous page */}
      { (page < limit) ? 
        <a href="#" className="inline-flex items-center px-4 py-2 ml-3 text-sm font-medium text-gray-500 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
        onClick={handleNextPage}>
          Next
        </a>
        : "" }
    </div>
  );
}