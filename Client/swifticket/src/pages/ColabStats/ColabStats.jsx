import React, { useEffect, useState } from 'react';
import Widget from '../admin/components/Widget';
import { getEventAttendance } from '../../services/StatsServices';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../state/atoms/tokenState';
import { useParams } from 'react-router-dom';

const ColabStats = () => {
  const token = useRecoilValue(tokenState);
  const [widgets, setWidgets] = useState(null);
  const { eventId } = useParams();

  const handleWidgets = async () => {
    const response = await getEventAttendance(token, eventId);

    if (response.status === 200) {
      console.log(response);
      setWidgets(response.data);
    }
  };

  useEffect(() => {
    handleWidgets();
  }, []);

  return (
    <div className="flex justify-center items-center flex-col h-screen w-screen">
      <div className="flex flex-wrap sm:flex-nowrap h-[40vh] sm:h-[20vh] w-full justify-evenly items-center">
        {widgets
          ? Object.keys(widgets).map((key) => {
              return <Widget key={key} label={key} value={widgets[key]} />;
            })
          : ''}
      </div>
    </div>
  );
};

export default ColabStats;
