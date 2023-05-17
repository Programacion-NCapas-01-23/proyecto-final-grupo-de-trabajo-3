import EventCard from "../components/EventCards/Minimized/EventCard";

const devEvents = [
  {
    img: "https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/styles/hc_1440x810/public/media/image/2023/02/mandalorian-2964862.jpg?itok=ZMtIO9yv",
    title: "Mando Cosplays",
    place: "Richard Vermont Plaza",
    date_time: new Date("2023-06-16T13:00:00"),
  },
  {
    img: "https://blogthinkbig.com/wp-content/uploads/sites/4/2020/07/Android-Basics-Kotlin.jpg?fit=1500%2C1000",
    title: "Kotlin Dev Convention",
    place: "UCA",
    date_time: new Date("2023-08-23T09:00:00"),
  },
];

export default function Testing() {
  return (
    <div className="flex flex-col w-full h-screen items-center justify-center">
      <EventCard count={1} event={devEvents[0]}/>
      <EventCard count={2} event={devEvents[1]}/>
    </div>
  );
}
