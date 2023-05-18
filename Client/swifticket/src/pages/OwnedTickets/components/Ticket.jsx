import React, { useEffect, useState } from 'react';
import Placeholder from '../../../assets/Placeholder.png';
import QRPlaceholder from '../../../assets/QRPlaceholder.png';
import LoadingQRPlaceholder from '../../../assets/LoadingQRPlaceholder.png';

const Ticket = () => {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    setTimeout(() => setIsLoading(false), 2000);
  }, []);

  return (
    <div className="flex justify-center items-center w-3/5 h-4/6 bg-white rounded-[2rem]">
      {isLoading ? (
        <span className="w-1/3 h-full bg-neutral-400 rounded-l-[2rem]" />
      ) : (
        <img
          src={Placeholder}
          alt="Ticket image"
          className="w-1/3 h-full object-cover rounded-l-[2rem]"
        />
      )}
      <div className="flex flex-col justify-center items-center w-1/3 h-full">
        {isLoading ? (
          <img src={LoadingQRPlaceholder} alt="QR" className="w-3/4" />
        ) : (
          <img
            src={QRPlaceholder}
            alt="QR"
            className="border-[0.5rem] border-neutral-400 w-3/4 rounded-[0.5rem]"
          />
        )}
        <p className="text-black heading-sm">
          {isLoading ? '...' : '484-awd2u233'}
        </p>
      </div>
      <div className="flex flex-col justify-evenly items-start w-1/3 h-full">
        <div className="flex flex-col">
          <p className="text-secondary">Tier</p>
          <p className="heading-lg text-black">
            {isLoading ? '...' : 'Super Fan'}
          </p>
        </div>
        <div className="flex flex-col">
          <p className="text-secondary">Owner</p>
          <p className="heading-lg text-black">
            {isLoading ? '...' : 'Fudge Madrazo'}
          </p>
        </div>
        <div className="flex flex-col">
          <p className="text-secondary">Date Purchased</p>
          <p className="heading-lg text-black">
            {isLoading ? '...' : 'Monday 15 Aug'}
          </p>
        </div>
      </div>
    </div>
  );
};

export default Ticket;
