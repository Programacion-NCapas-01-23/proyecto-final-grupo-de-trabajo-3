import React, { useEffect, useState } from 'react';
import { devEvents } from '../Cards';
import EventCardSt from '../../components/EventCards/Standard/EventCardSt';
import { getTicketByUser } from '../../services/TicketServices';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../state/atoms/tokenState';
import TicketCard from './components/TicketCard';

const OwnedTickets = () => {

  // const owned = devEvents.slice(4,7)
  const token = useRecoilValue(tokenState);
  const [tickets, setTickets] = useState([]);
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

  const getUserTickets = async (page) => {
    console.log("Getting tickets... page: ", page);
    let response = await getTicketByUser(token, page);

    console.log(response);
    if (response.status == 200) {
      setTickets(response.data.content);
      setLimit(response.data.totalPages);
    }
  }

  useEffect(() => {
      getUserTickets(page);
  }, [page]);
    
  return (
    <div className="min-h-[calc(100vh-52px-3.5rem)]">
      <TitileWithLines title="Owned tickets"/>
      <div className='flex flex-wrap w-full justify-evenly overflow-auto'>
      {tickets?.map((ticket, index) => (
        <TicketCard key={index} ticket={ticket} />
      ))}

      <Pagination page={page} limit={limit} handlePreviousPage={handlePreviousPage} handleNextPage={handleNextPage} />
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