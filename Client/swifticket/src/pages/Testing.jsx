import EventCard from "../components/EventCards/Minimized/EventCard";

const devEvents = [
  {
    img: "https://i.imgur.com/CqnHHbp.png",
    title: "Mando Cosplays",
    place: "Richard Vermont Plaza",
    date_time: new Date("2023-06-16T13:00:00"),
  },
  {
    img: "https://i.imgur.com/iqs36FG.png",
    title: "I am the DANGER",
    place: "Hell",
    date_time: new Date("2023-10-06T22:00:00"),
  },
  {
    img: "https://i.imgur.com/NC4hnOQ.png",
    title: "PS5 Giveaway",
    place: "Hidden Leaf Village",
    date_time: new Date("2023-10-08T09:00:00"),
  },
  {
    img: "https://i.imgur.com/XdB0Bdd.png",
    title: "Hot Labradoodle Eating Contest",
    place: "El Muelle",
    date_time: new Date("2023-04-30T12:00:00"),
  },
  {
    img: "https://i.imgur.com/BT7VIvh.png",
    title: "Travesti Escote Concert",
    place: "Tilted Towers",
    date_time: new Date("2023-09-06T16:00:00"),
  },
  {
    img: "https://i.imgur.com/piLVV02.png",
    title: "Cheesse and Wine",
    place: "MAGNA VII",
    date_time: new Date("2023-07-21T18:00:00"),
  },
  {
    img: "https://i.imgur.com/aKDtkl0.png",
    title: "React Dev Convention",
    place: "UCA",
    date_time: new Date("2023-08-23T09:00:00"),
  }
];

export default function Testing() {
  return (
    <div className="flex flex-wrap w-full h-screen items-center justify-center">
      {devEvents.map((event, index) => (
        <EventCard key={index} event={event} />
      ))}
    </div>
  );
}
