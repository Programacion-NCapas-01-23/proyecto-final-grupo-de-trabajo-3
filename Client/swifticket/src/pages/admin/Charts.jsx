import React from 'react';
import BarChart from './components/BarChart';
import LineChart from './components/LineChart';
import { MdWarning } from 'react-icons/md';
import Widget from './components/Widget';

const Charts = () => {
  const widgets = [
    { id: 1, label: 'Users quantity', value: 450 },
    { id: 2, label: 'Events held', value: 28 },
    { id: 3, label: 'Tickets sold', value: 420 },
    { id: 4, label: 'Event attendance percentage', value: [40, 60] },
  ];

  return (
    <div>
      <div className=" flex h-auto sm:h-screen w-screen">
        <div className="hidden sm:flex h-screen w-[20vw] bg-[#212549] text-white">
          <p>NAV</p>
        </div>
        <div className="flex flex-col h-screen w-screen sm:w-[80vw]">
          <div className="flex flex-col sm:flex-row h-[30vh] sm:h-[20vh] w-full justify-between items-start sm:items-end pl-4 sm:pl-8">
            <div className="flex gap-2 justify-center items-center">
              <span className="w-1 h-16 bg-primary" />
              <p className="title">Dashboard</p>
            </div>
            <div className="flex justify-center items-center w-full sm:w-auto sm:h-full">
              <button className="flex justify-between items-center px-4 rounded-3xl sm:rounded-r-none sm:rounded-l-3xl w-44 h-9 bg-primary">
                <MdWarning />
                <p>Start Services</p>
                <div className=""></div>
              </button>
            </div>
          </div>
          {/* TODO : Fix height */}
          <div className="flex flex-wrap sm:flex-nowrap h-[40vh] sm:h-[20vh] w-full justify-evenly items-center">
            {widgets.map((widget) => {
              const { id, label, value } = widget;

              return <Widget key={id} label={label} value={value} />;
            })}
          </div>
          <div className="flex flex-col sm:flex-row gap-4 py-4 sm:gap-0 h-[65vh] sm:h-[35vh] w-full justify-evenly items-center">
            <div className="flex justify-center items-center h-[30vh] sm:h-[30vh] w-4/5 sm:w-[35vw] bg-white">
              <BarChart />
            </div>
            <div className="flex justify-center items-center h-[30vh] w-4/5 sm:w-[35vw] bg-white">
              <LineChart />
            </div>
          </div>
          <div className="flex pt-4 pb-8 sm:pt-0 sm:pb-0 sm:h-[25vh] w-full justify-center items-center">
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
