import EventInfo from "../components/EventInfo";

export default function EventCard(props) {
  return (
    <div className="flex mx-default-xs max-w-[32rem] ">
      <div className="relative md:w-[16rem] w-40">
        <img
          className="object-cover transition-all h-full w-full rounded-l-2xl"
          src={props.event.img}
          alt="event_img"
        />
      </div>
        <EventInfo event={props.event}></EventInfo>
    </div>
  );
}
