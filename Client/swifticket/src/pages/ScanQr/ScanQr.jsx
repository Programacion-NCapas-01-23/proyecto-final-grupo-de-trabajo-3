import React from 'react';
import { MdCameraAlt } from 'react-icons/md';

const ScanQr = () => {
  return (
    <div className="flex flex-col justify-center items-center gap-8 w-full min-h-[calc(100vh-52px-3.5rem)] px-8">
      <span className="w-full grid grid-cols-10 px-default-lg pb-default-lg items-center">
        <div className="border h-0 border-primary"></div>
        <h1 className="md:title subtitle text-center md:col-span-3 col-span-7">
          Send ticket
        </h1>
        <div className="border h-0 border-primary md:col-span-6 col-span-2"></div>
      </span>
      <div className="flex justify-center items-center w-[40vh] h-[40vh] bg-[#424242] bg-opacity-50 rounded-3xl">
        <MdCameraAlt size={50} />
      </div>
      <p className="heading-md">Scan a QR to begin the transaction.</p>
      <button className="border-[#144580] border-2 px-8 py-2 rounded-3xl heading-md">
        Cancel
      </button>
    </div>
  );
};

export default ScanQr;
