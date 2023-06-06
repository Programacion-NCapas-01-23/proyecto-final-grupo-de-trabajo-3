import React from 'react';
import BarChart from './components/BarChart';
import LineChart from './components/LineChart';

const Charts = () => {
  return (
    <div>
      <div className=" flex h-auto sm:h-screen w-screen">
        <div className="hidden sm:flex h-screen w-[20vw] bg-[#212549] text-white">
          <p>NAV</p>
        </div>
        <div className="flex flex-col h-screen w-screen sm:w-[80vw]">
          <div className="flex h-[15vh] sm:h-[20vh] w-full justify-start items-end pl-4 sm:pl-8">
            <div className="flex gap-2 justify-center items-center">
              <span className="w-1 h-16 bg-primary" />
              <p className="title">Dashboard</p>
            </div>
          </div>
          {/* TODO : Fix height */}
          <div className="flex flex-wrap sm:flex-nowrap h-[45vh] sm:h-[20vh] py-4 w-full justify-evenly items-center">
            <div className="flex flex-col justify-center items-center h-2/5 sm:h-5/6 min-w-[40%] sm:min-w-[12%] max-w-[50%] sm:max-w-[25%] bg-[#212549] rounded-md">
              <p>Users quantity</p>
              <p className="subtitle">450</p>
            </div>
            <div className="flex flex-col justify-center items-center h-2/5 sm:h-5/6 min-w-[40%] sm:min-w-[12%] max-w-[50%] sm:max-w-[25%] bg-[#212549] rounded-md">
              <p>Events held</p>
              <p className="subtitle">28</p>
            </div>
            <div className="flex flex-col justify-center items-center h-2/5 sm:h-5/6 min-w-[40%] sm:min-w-[12%] max-w-[50%] sm:max-w-[25%] bg-[#212549] rounded-md">
              <p>Tickets sold</p>
              <p className="subtitle">420</p>
            </div>
            <div className="flex flex-col justify-center items-center h-2/5 sm:h-5/6 min-w-[40%] sm:min-w-[18%] max-w-[50%] sm:max-w-[25%] bg-[#212549] rounded-md">
              <p>Event attendance percentage</p>
              <div className="flex justify-evenly w-full">
                <div className="flex flex-col">
                  <p className="subtitle">40%</p>
                  <p>Groups</p>
                </div>
                <div className="flex flex-col">
                  <p className="subtitle">/</p>
                  <p></p>
                </div>
                <div className="flex flex-col">
                  <p className="subtitle">60%</p>
                  <p>Individuals</p>
                </div>
              </div>
            </div>
          </div>
          <div className="flex flex-col sm:flex-row gap-4 py-4 sm:gap-0 h-[65vh] sm:h-[35vh] w-full justify-evenly items-center">
            <div className="flex justify-center items-center h-[30vh] sm:h-[30vh] w-4/5 sm:w-[35vw] bg-white">
              <BarChart />
            </div>
            <div className="flex justify-center items-center h-[30vh] w-4/5 sm:w-[35vw] bg-white">
              <LineChart />
            </div>
          </div>
          <div className="flex h-[25vh] w-full justify-center items-center">
            <button className="bg-primary px-4 py-2 rounded-md">
              Generate Report
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Charts;
