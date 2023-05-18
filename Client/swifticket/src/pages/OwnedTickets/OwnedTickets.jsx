import React from 'react';
import Ticket from './components/Ticket';

const OwnedTickets = () => {
  return (
    <div className="flex flex-col w-screen h-screen">
      <div className="flex items-center gap-4 h-[20vh] ml-8 sm:ml-16">
        <span className="h-16 w-1 bg-primary"></span>
        <p className="heading-xl sm:title">Mando Cosplays</p>
      </div>
      <div className="flex justify-center items-start sm:items-center h-[80vh]">
        <Ticket />
      </div>
    </div>
  );
};

export default OwnedTickets;
