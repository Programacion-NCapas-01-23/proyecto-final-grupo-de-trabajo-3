import React, { useEffect, useState } from 'react';
import BarChart from './components/BarChart';
import LineChart from './components/LineChart';
import Widget from './components/Widget';
import { ToggleServices } from './components/ToggleServices';
import { getGeneralStats } from '../../services/StatsServices';
import { tokenState } from '../../state/atoms/tokenState';
import { useRecoilValue } from 'recoil';
import { getSystemState } from '../../services/System.Services';

const Charts = () => {
  const token = useRecoilValue(tokenState);
  const [isActive, setIsActive] = useState(false);
  const [widgets, setWidgets] = useState(null);

  const handleSystemState = async () => {
    const response = await getSystemState();

    console.log(response);

    if (response.status === 200) {
      response.data.status === 1 ? setIsActive(true) : setIsActive(false);
    }
  };

  const handleWidgets = async () => {
    const response = await getGeneralStats(token);

    if (response.status === 200) {
      setWidgets([
        { id: 1, label: 'Users quantity', value: response.data.users },
        { id: 2, label: 'Events held', value: response.data.events },
        { id: 3, label: 'Tickets sold', value: response.data.ticketsSold },
        {
          id: 4,
          label: 'Event attendance percentage',
          value: response.data.attendanceSingleVsGroup,
          isNumber: true,
        },
      ]);
    } else {
      setWidgets([
        { id: 1, label: 'Users quantity', value: 0 },
        { id: 2, label: 'Events held', value: 0 },
        { id: 3, label: 'Tickets sold', value: 0 },
        {
          id: 4,
          label: 'Event attendance percentage',
          value: [0, 0],
          isNumber: true,
        },
      ]);
    }
  };

  useEffect(() => {
    handleSystemState();
    handleWidgets();
  }, []);

  return (
    <div className="flex flex-col h-screen w-screen sm:w-[80vw]">
      <div className="flex flex-col sm:flex-row h-[30vh] sm:h-[20vh] w-full justify-between items-start sm:items-end pl-4 sm:pl-8">
        <div className="flex gap-2 justify-center items-center">
          <span className="w-1 h-16 bg-primary" />
          <p className="title">Dashboard</p>
        </div>
        <div className="flex justify-center items-center w-full sm:w-auto sm:h-full">
          <ToggleServices isActive={isActive} setIsActive={setIsActive} />
        </div>
      </div>
      <div className="flex flex-wrap sm:flex-nowrap h-[40vh] sm:h-[20vh] w-full justify-evenly items-center">
        {widgets
          ? widgets.map((widget) => {
              const { id, label, value, isNumber } = widget;

              return (
                <Widget
                  key={id}
                  label={label}
                  value={value}
                  isNumber={isNumber}
                />
              );
            })
          : ''}
      </div>
      <div className="flex pt-4 pb-8 sm:pt-0 sm:pb-0 sm:h-[25vh] w-full justify-center items-center">
        <button className="bg-primary px-4 py-2 rounded-md">
          Generate Report
        </button>
      </div>
    </div>
  );
};

export default Charts;
