import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';
import { getSystemState } from '../../../services/System.Services';
import { getEventStats } from '../../../services/StatsServices';
import { getEventById } from '../../../services/Events.Services';
import Widget from '../components/Widget';
import BarChart from '../components/BarChart';
import LineChart from '../components/LineChart';

export default function OneEventStats() {
  const token = useRecoilValue(tokenState);
  const [isActive, setIsActive] = useState(false);
  const [widgets, setWidgets] = useState(null);
  const [barsGraphValues, SetBarsGraphValues] = useState({
    ticketsSoldByTiers: [],
    tiers: [],
  });
  const [lineGraphValues, SetLineGraphValues] = useState({
    attendanceValues: [],
    attendanceHours: [],
  });
  const [event, setEvent] = useState(null);
  const { eventId } = useParams();

  const handleSystemState = async () => {
    const response = await getSystemState();
    if (response.status === 200)
      response.data.status === 1 ? setIsActive(true) : setIsActive(false);
  };

  const handleWidgets = async () => {
    const response = await getEventStats(token, eventId);
    const eventResponse = await getEventById(eventId);
    setEvent(eventResponse.data);
    console.log(response.data);

    if (response.status === 200) {
      setWidgets([
        {
          id: 1,
          label: 'Attendants',
          value: response.data.attendants,
          isNumber: false,
        },
        {
          id: 4,
          label: 'Tickets Sold',
          value: response.data.ticketsSold,
          isNumber: false,
        },
        {
          id: 2,
          label: 'Attendants (%)',
          value: `${response.data.attendantsVsTicketsSold}%`,
          isNumber: false,
        },
        {
          id: 5,
          label: 'Tickets Sold (%)',
          value: `${response.data.soldVsAvailable}%`,
          isNumber: false,
        },
        {
          id: 3,
          label: 'Capacity',
          value: response.data.capacity,
          isNumber: false,
        },
        {
          id: 6,
          label: 'Group Vs Single',
          value: response.data.attendanceSingleVsGroup,
          isNumber: true,
        },
      ]);
      SetBarsGraphValues({
        ticketsSoldByTiers: response.data.ticketsSoldByTier,
        tiers: response.data.tiers,
      });
      SetLineGraphValues({
        attendanceValues: response.data.attendanceValues,
        attendanceHours: response.data.attendanceHours,
      });
    } else {
      setWidgets([]);
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
          <p className="title"> {event?.title && `${event.title}`} </p>
        </div>
        <div className="flex justify-center items-center w-full sm:w-auto sm:h-full"></div>
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

      <div className="flex flex-col sm:flex-row gap-4 py-4 sm:gap-0 h-[65vh] sm:h-[35vh] w-full justify-evenly items-center">
        <div className="flex justify-center items-center h-[30vh] sm:h-[30vh] w-4/5 sm:w-[35vw] bg-white">
          <BarChart
            dataSet={barsGraphValues.ticketsSoldByTiers}
            titles={barsGraphValues.tiers}
          />
        </div>
        <div className="flex justify-center items-center h-[30vh] w-4/5 sm:w-[35vw] bg-white">
          <LineChart
            dataSet={lineGraphValues.attendanceValues}
            titles={lineGraphValues.attendanceHours}
          />
        </div>
      </div>

      <div className="flex pt-4 pb-8 sm:pt-0 sm:pb-0 sm:h-[25vh] w-full justify-center items-center">
        <button className="bg-primary px-4 py-2 rounded-md">
          Generate Report
        </button>
      </div>
    </div>
  );
}
