import { useState } from "react";
import EventInfo from "./components/EventInfo";
import {useNavigate} from "react-router-dom" 

export default function EventCardSt({event}) {

  const navigate = useNavigate();
  const date_time = new Date(event.dateTime)

  const [imageError, setImageError] = useState(false);

  const handleImageError = () => {
    setImageError(true);
  };

  const redirectUser = () => {
    navigate(`/event/${event.id}`);
  };

  return (
    <a className="cursor-pointer" onClick={redirectUser}>
      <div className="flex mx-default-xs max-h-48 max-w-[32rem] md:min-w-[32rem] bg-secondary bg-opacity-30 m-default-xs shadow-md shadow-black rounded-2xl transition-all hover:shadow-neutral-800 hover:scale-105">
        <div className="relative md:w-[16rem] w-40">
          {imageError ? (
            <img
              className="h-full w-full object-cover rounded-l-2xl"
              src={`/assets/robot.jpg`} 
              alt="event_img"
            />
            ) : (
            <img
              className="h-full w-full object-cover rounded-l-2xl"
              src={event.image}
              alt="event_img"
              onError={handleImageError}
            />
          )}
          <span className="absolute bottom-0 bg-secondary text-center px-3 py-2 rounded-bl-2xl shadow-md shadow-black">
            <p className="text-4xl"> {date_time.getDate()} </p>
            <p className="uppercase -mt-2">{date_time.toLocaleString('en-US', { month: 'short' })}</p>
          </span>
        </div>
        <EventInfo event={event} date={date_time}></EventInfo>
      </div>
    </a>
  );
}
