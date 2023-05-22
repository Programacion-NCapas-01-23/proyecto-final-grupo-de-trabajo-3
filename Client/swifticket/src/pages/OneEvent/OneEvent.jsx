import React from 'react';
import Placeholder from '../../assets/Placeholder.png';

const OneEvent = () => {
  return (
    <div className="w-screen h-screen">
      <img
        src={Placeholder}
        alt="Image"
        className="h-1/3 w-full object-cover "
      />
      <div className="flex flex-col h-2/3 w-full">
        <div className="flex items-center gap-4 py-4 px-8 sm:px-16">
          <span className="h-16 w-1 bg-primary"></span>
          <p className="heading-xl">Mando Cosplays</p>
        </div>
        <p className="mx-8 sm:mx-16">
          Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sint qui
          unde non quibusdam ullam magnam expedita eaque eligendi aperiam
          assumenda asperiores labore, aspernatur et explicabo? Tempore soluta
          commodi quidem debitis!
        </p>
        <div className="flex flex-col sm:flex-row py-4 px-8 sm:px-16 w-full">
          <div className="flex flex-col w-full sm:w-1/2">
            <div className="flex">
              <p className="w-full sm:w-1/4 heading-sm text-center">Date</p>
              <div className="w-3/4 hidden sm:block"></div>
            </div>
            <div className="flex flex-col sm:flex-row">
              <div className="flex flex-col w-1/2 sm:w-1/4 justify-center items-center self-center sm:self-end bg-secondary bg-opacity-[0.35] h-5/6">
                <p className="subtitle">19</p>
                <p className="heading-lg">June</p>
              </div>
              <div className="flex flex-col w-full sm:w-3/4 justify-evenly h-[30vh]">
                <div className="flex gap-2 pl-0 sm:pl-8 items-center">
                  <span className="h-[1.35rem] w-1 bg-primary"></span>
                  <p className="heading-md">Richard Vermont Plaza</p>
                </div>
                <div className="flex gap-2 pl-0 sm:pl-8 items-center">
                  <span className="h-[1.35rem] w-1 bg-primary"></span>
                  <p className="heading-md">1:00 P.M</p>
                </div>
                <div className="flex gap-2 pl-0 sm:pl-8 items-center">
                  <span className="h-[1.35rem] w-1 bg-primary"></span>
                  <p className="heading-md">6h</p>
                </div>
              </div>
            </div>
          </div>
          <div className="flex flex-col w-full py-8 sm:w-1/2">
            <div className="flex">
              <div className="flex items-center gap-4">
                <span className="h-[1.15rem] w-1 bg-primary"></span>
                <p className="w-full heading-sm text-center">Tickets</p>
              </div>
              <div className="w-3/4 hidden sm:block"></div>
            </div>
            <div className="flex flex-col pt-0 sm:pt-[5.1vh] w-full min-h-[30vh]">
              <div className="flex flex-col w-full sm:w-3/4 min-h-[83%] bg-secondary bg-opacity-[0.35] py-4 gap-4">
                {/* ticket segment */}
                <div className="flex justify-between items-center w-full px-4 sm:px-8">
                  <p className="heading-sm w-[35%] sm:w-[37.5%]">Super fan</p>
                  <p className="heading-sm w-[30%] sm:w-[37.5%]">$5.00</p>
                  <div className="flex justify-between items-center min-w-[35%] sm:min-w-[25%]">
                    <button className="flex justify-center items-center h-[2rem] w-[2rem] bg-white bg-opacity-[35%] heading-sm rounded-md">
                      -
                    </button>
                    <p>2</p>
                    <button className="flex justify-center items-center h-[2rem] w-[2rem] bg-white bg-opacity-[35%] heading-sm rounded-md">
                      +
                    </button>
                  </div>
                </div>
                <div className="flex justify-between items-center w-full px-4 sm:px-8">
                  <p className="heading-sm w-[35%] sm:w-[37.5%]">Fan</p>
                  <p className="heading-sm w-[30%] sm:w-[37.5%]">$4.99</p>
                  <div className="flex justify-between items-center min-w-[35%] sm:min-w-[25%]">
                    <button className="flex justify-center items-center h-[2rem] w-[2rem] bg-white bg-opacity-[35%] heading-sm rounded-md">
                      -
                    </button>
                    <p>15</p>
                    <button className="flex justify-center items-center h-[2rem] w-[2rem] bg-white bg-opacity-[35%] heading-sm rounded-md">
                      +
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default OneEvent;
