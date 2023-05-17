import React from 'react';
import Placeholder from '../assets/Placeholder.png';

const EventMainCard = () => {
  return (
    <div className="flex w-full h-full bg-secondary bg-opacity-30 rounded-[2rem]">
      <div className="w-1/2 h-full">
        <img
          src={Placeholder}
          alt="Event image"
          className="w-full h-full object-cover rounded-l-[2rem]"
        />
      </div>
      <div className="flex flex-col h-full w-1/2 justify-evenly items-start pl-4">
        <p className="heading-xl">Mando Cosplays</p>
        <div className="flex w-full gap-2">
          <span className="h-full w-1 bg-primary" />
          <p className="heading-sm">Los Angeles Azules</p>
          <p></p>
        </div>
        <div className="flex w-full gap-2">
          <span className="h-full w-1 bg-primary" />
          <p className="heading-sm">Richard Vermount Plaza</p>
          <p></p>
        </div>
        <div className="flex w-full gap-2">
          <span className="h-full w-1 bg-primary" />
          <p className="heading-sm">1:00 P.M</p>
          <p></p>
        </div>
      </div>
    </div>
  );
};

export default EventMainCard;
