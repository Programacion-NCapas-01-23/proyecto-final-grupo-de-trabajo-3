import EventInfo from "../components/EventInfo";

export default function EventCard(props) {
  return (
    <div className="flex mx-default-xs max-w-[32rem] m-default-xs">
      <div className="relative md:w-[16rem] w-40">
        <img
          className="object-cover transition-all h-full w-full rounded-l-2xl"
          src={props.event.img}
          alt="event_img"
        />
        <span className="absolute bottom-0 bg-secondary text-center px-3 py-2 rounded-bl-2xl">
          <p className="text-4xl">{props.event.date_time.getDate()}</p>
          <p className="uppercase -mt-2">{props.event.date_time.toLocaleString('en-US', { month: 'short' })}</p>
        </span>
      </div>
      <EventInfo event={props.event}></EventInfo>
    </div>
  );
}
