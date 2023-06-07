import React from 'react';
import userPlaceholder from '../../../assets/userPlaceholder.png';
import { MdComputer, MdTableRows, MdLogout } from 'react-icons/md';

const SideBar = () => {
  return (
    <div className="hidden sm:flex flex-col h-screen w-[20vw] bg-[#212549]">
      <div className="flex flex-col h-1/4 w-full">
        <div className="flex justify-center items-center h-1/2 w-full">
          <img src={userPlaceholder} alt="User" className="h-1/2" />
          <p className="p-default">AdminGod</p>
        </div>
        <div className="flex flex-col justify-evenly items-center h-1/2 w-full">
          <p className="heading-lg">STATS</p>
          <div className="flex w-full">
            <button className="flex items-center pl-16 gap-4 bg-primary w-11/12 p-2 rounded-r-3xl">
              <MdComputer size={25} />
              Dashboard
            </button>
          </div>
        </div>
      </div>
      <div className="flex flex-col h-3/4 w-full">
        <div className="flex flex-col justify-evenly items-center h-1/3 w-full">
          <p className="heading-lg">STATS</p>
          <div className="flex w-full">
            <button className="flex items-center pl-16 gap-4 bg-[#00052E] w-11/12 p-2 rounded-r-3xl">
              <MdTableRows size={25} />
              Create event
            </button>
          </div>
          <div className="flex w-full">
            <button className="flex items-center pl-16 gap-4 bg-[#00052E] w-11/12 p-2 rounded-r-3xl">
              <MdTableRows size={25} />
              All events
            </button>
          </div>
        </div>
        <div className="flex flex-col justify-evenly items-center h-1/3 w-full">
          <p className="heading-lg">TABLES</p>
          <div className="flex w-full">
            <button className="flex items-center pl-16 gap-4 bg-[#00052E] w-11/12 p-2 rounded-r-3xl">
              <MdTableRows size={25} />
              Users
            </button>
          </div>
          <div className="flex w-full">
            <button className="flex items-center pl-16 gap-4 bg-[#00052E] w-11/12 p-2 rounded-r-3xl">
              <MdTableRows size={25} />
              Catalogs
            </button>
          </div>
        </div>
        <div className="flex flex-col justify-evenly items-center h-1/3 w-full">
          <p className="heading-lg">ACTIONS</p>
          <div className="flex w-full">
            <button className="flex items-center pl-16 gap-4 bg-[#00052E] w-11/12 p-2 rounded-r-3xl">
              <MdLogout size={25} />
              User Mode
            </button>
          </div>
          <div className="flex w-full">
            <button className="flex items-center pl-16 gap-4 bg-[#00052E] w-11/12 p-2 rounded-r-3xl">
              <MdLogout size={25} />
              Log Out
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SideBar;
