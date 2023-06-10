import React from 'react';
import Placeholder from '../../../assets/Placeholder.png';
import LoadingQRPlaceholder from '../../../assets/LoadingQRPlaceholder.png';

const Ticket = () => {
  return (
    <div className="flex justify-center items-center w-3/5 h-4/6 bg-white rounded-[2rem]">
      <span className="w-1/3 h-full rounded-l-[2rem] bg-neutral-400" />
      <div className="flex flex-col justify-center items-center w-1/3 h-full">
        <img src={LoadingQRPlaceholder} alt="QR" className="w-3/4" />
        <p className="text-black heading-sm">...</p>
      </div>
      <div className="flex flex-col justify-evenly items-start w-1/3 h-full">
        <div className="flex flex-col">
          <p className="text-secondary">Tier</p>
          <p className="heading-lg text-black">...</p>
        </div>
        <div className="flex flex-col">
          <p className="text-secondary">Owner</p>
          <p className="heading-lg text-black">...</p>
        </div>
        <div className="flex flex-col">
          <p className="text-secondary">Date Purchased</p>
          <p className="heading-lg text-black">...</p>
        </div>
      </div>
    </div>
  );
};

export default Ticket;
