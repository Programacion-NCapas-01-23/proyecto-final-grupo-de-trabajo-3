import TicketInfo from "./TicketInfo";
import {useNavigate} from "react-router-dom" 

export default function TicketCard({ticket}) {

  const navigate = useNavigate();
  const date_time = new Date(ticket.createdAt)

  const redirectUser = () => {
    navigate(`/user/tickets/${ticket.id}`);
  };

  return (
    <a className="cursor-pointer" onClick={redirectUser}>
      <div className="flex mx-default-xs max-h-48 max-w-[32rem] md:min-w-[32rem] bg-secondary bg-opacity-30 m-default-xs shadow-md shadow-black rounded-2xl transition-all hover:shadow-neutral-800 hover:scale-105">
        <div className="relative md:w-[16rem] w-40">
          <img
            className="h-full w-full object-cover rounded-l-2xl"
            src={ticket?.event?.image ?? "https://res.cloudinary.com/dypsbiypw/image/upload/v1688016265/file_advaly.jpg"}
            alt="event_img"
          />
          <span className="absolute bottom-0 bg-secondary text-center px-3 py-2 rounded-bl-2xl shadow-md shadow-black">
            <p className="text-4xl"> {date_time.getDate()} </p>
            <p className="uppercase -mt-2">{date_time.toLocaleString('en-US', { month: 'short' })}</p>
          </span>
        </div>
        <TicketInfo event={ticket?.event} tier={ticket?.tier} />
      </div>
    </a>
  );
}
