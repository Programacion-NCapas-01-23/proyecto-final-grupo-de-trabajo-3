import React from 'react';
import { MdCameraAlt } from 'react-icons/md';

const ScanQr = () => {
  return (
    <div className="flex flex-col justify-center items-center gap-8 w-full min-h-[calc(100vh-52px-3.5rem)] px-8">
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
