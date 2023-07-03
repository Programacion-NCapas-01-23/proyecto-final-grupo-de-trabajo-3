import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function EventCardMi(props) {
  
  const navigate = useNavigate();

  const [imageError, setImageError] = useState(false);

  const handleImageError = () => {
    setImageError(true);
  };

  const redirectUser = () => {
    navigate(`/event/${props.event.id}`);
  };

  const event = props.event

  return (
    <div className="text-center m-auto md:w-48 w-36 cursor-pointer" onClick={redirectUser}>
      <div className="p-2 bg-secondary bg-opacity-30 rounded-md">
        {imageError ?
          <img className="object-cover md:w-48 md:h-32 w-36 h-20" src={`/assets/robot.jpg`}  alt="" />
          :
          <img className="object-cover md:w-48 md:h-32 w-36 h-20" src={event.image} alt="" onError={handleImageError} />
        }
      </div>
      <p>{event.title}</p>
    </div>
  )
}
