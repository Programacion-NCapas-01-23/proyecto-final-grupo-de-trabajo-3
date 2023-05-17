import EventInfo from "../components/EventInfo";

export default function EventCard() {
  return (
    <div className="flex">
      <div className="relative border-lime-400 border overflow-hidden">
        <img
          className="min-h-full rounded-l-2xl"
          src="https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/styles/hc_1440x810/public/media/image/2023/02/mandalorian-2964862.jpg?itok=ZMtIO9yv"
          alt="event_img"
        />
      </div>
        <EventInfo></EventInfo>
    </div>
  );
}
