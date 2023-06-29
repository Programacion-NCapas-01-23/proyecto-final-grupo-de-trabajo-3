import React, { useEffect, useState } from 'react';
import { devEvents } from '../Cards';
import EventCardSt from '../../components/EventCards/Standard/EventCardSt';
import { getTicketByUser } from '../../services/TicketServices';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../state/atoms/tokenState';
import TicketCard from './components/TicketCard';

const OwnedTickets = () => {

  // const owned = devEvents.slice(4,7)
  const [tickets, setTickets] = useState([]);
  const token = useRecoilValue(tokenState);

  const getUserTickets = async () => {
    let response = await getTicketByUser(token);
    if (response.status == 200)
      setTickets(response.data.content);
  }

  useEffect(() => {
      getUserTickets(token);
  }, []);
    
  return (
    <div className="min-h-[calc(100vh-52px-4rem)]">
      <TitileWithLines title="Owned tickets"/>
      <div className='flex flex-wrap w-full justify-evenly overflow-auto'>
      {tickets?.map((ticket, index) => (
            <TicketCard key={index} ticket={ticket} />
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