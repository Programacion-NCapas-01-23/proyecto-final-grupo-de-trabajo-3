import { useState } from "react";
import EventCardSt from "../components/EventCards/Standard/EventCardSt"
import { MdArrowCircleLeft, MdArrowCircleRight } from "react-icons/md";
import { devEvents } from "./Cards"
import EventCardMi from "../components/EventCards/Minimized/EventCardMi";

export default function Home() {

  return (
    <div className="min-h-[calc(100vh-52px-4rem)]">

      <section className="">
        <TitileWithLines title="What's New?" />
        <div className="flex md:flex-row flex-col justify-evenly">
          <EventCardSt event={devEvents[0]} />
          <EventCardSt event={devEvents[1]} />
        </div>
      </section>
      <section className="">
        <TitileWithLines title="Available now" />
        <div className="">
          <EventList eventsPerPage={4} events={devEvents} />
        </div>
      </section>

    </div>
  )
}


function TitileWithLines({ title }) {
  return (
    <span className="w-full grid grid-cols-6 px-default-lg pt-default-lg md:pb-default-xl pb-default-lg items-center">
      <div className='border h-0 border-primary'></div>
      <h1 className='md:title subtitle text-center md:col-span-2 col-span-3'>{title}</h1>
      <div className='border h-0 border-primary md:col-span-3 col-span-2'></div>
    </span>
  )
}

const EventList = ({ eventsPerPage, events }) => {
  const devEvents = events.concat(
    events,
    events,
    events
  );

  const [currentPage, setCurrentPage] = useState(1);

  const indexOfLastEvent = currentPage * eventsPerPage;
  const indexOfFirstEvent = indexOfLastEvent - eventsPerPage;
  const currentEvents = devEvents.slice(indexOfFirstEvent, indexOfLastEvent);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const totalPages = Math.ceil(devEvents.length / eventsPerPage);

  return (
    <div className="flex flex-row justify-center p-default-xs">
      <div className="flex flex-col items-center">
        <div className="grid grid-cols-2 w-fit md:gap-x-default-2xl">
          {currentEvents.map((event, index) => (
            <EventCardMi key={index} event={event} />
          ))}
        </div>


        <div className="flex">
          {currentPage > 1 && (
            <>
              <button onClick={() => handlePageChange(currentPage - 1)} className="p-1">
                <MdArrowCircleLeft />
              </button>
              <button onClick={() => handlePageChange(1)} className="p-1">
                1 ...
              </button>
            </>
          )}

          <button onClick={() => handlePageChange(currentPage)} className="p-1 text-primary">
            {currentPage}
          </button>

          {currentPage < totalPages && (
            <>
              <button onClick={() => handlePageChange(totalPages)} className="p-1">
                ... {totalPages}
              </button>
              <button onClick={() => handlePageChange(currentPage + 1)} className="p-1">
                <MdArrowCircleRight />
              </button>
            </>
          )}
        </div>

      </div>
    </div>
  );
};