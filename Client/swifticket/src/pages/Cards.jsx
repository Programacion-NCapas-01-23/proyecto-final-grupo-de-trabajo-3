import EventCardSt from "../components/EventCards/Standard/EventCardSt";
import EventCardCh from "../components/EventCards/Checkout/EventCardCh";
import EventCardMi from "../components/EventCards/Minimized/EventCardMi";
import EventCardSh from "../components/EventCards/Shoping/EventCardSh";

export const devEvents = [
  {
    id: 0,
    img: "https://i.imgur.com/CqnHHbp.png",
    title: "Mando Cosplays",
    place: "Richard Vermont Plaza",
    date_time: new Date("2023-06-16T13:00:00"),
    tiers: [
      {
        tier: "Super",
        price: 19.99,
        count: 5
      },
      {
        tier: "Diesel",
        price: 9.99,
        count: 0
      },
      {
        tier: "Regular",
        price: 4.99,
        count: 0
      },

    ]
  },
  {
    id: 1,
    img: "https://i.imgur.com/iqs36FG.png",
    title: "I am the DANGER",
    place: "Hell",
    date_time: new Date("2023-10-06T22:00:00"),
    tiers: [
      {
        tier: "Super",
        price: 19.99,
        count: 0
      },
      {
        tier: "Diesel",
        price: 9.99,
        count: 2
      },
      {
        tier: "Regular",
        price: 4.99,
        count: 3
      },

    ]
  },
  {
    id: 2,
    img: "https://i.imgur.com/NC4hnOQ.png",
    title: "PS5 Giveaway",
    place: "Hidden Leaf Village",
    date_time: new Date("2023-10-08T09:00:00"),
    tiers: [
      {
        tier: "Super",
        price: 19.99,
        count: 4
      },
      {
        tier: "Diesel",
        price: 9.99,
        count: 7
      },
      {
        tier: "Regular",
        price: 4.99,
        count: 1
      },

    ]
  },
  {
    id: 3,
    img: "https://i.imgur.com/XdB0Bdd.png",
    title: "Hot Labradoodle Eating Contest",
    place: "El Muelle",
    date_time: new Date("2023-04-30T12:00:00"),
    tiers: [
      {
        tier: "Super",
        price: 19.99,
        count: 0
      },
      {
        tier: "Diesel",
        price: 9.99,
        count: 0
      },
      {
        tier: "Regular",
        price: 4.99,
        count: 1
      },

    ]
  },
  {
    id: 4,
    img: "https://i.imgur.com/BT7VIvh.png",
    title: "Travesti Escote Concert",
    place: "Tilted Towers",
    date_time: new Date("2023-09-06T16:00:00"),
    tiers: [
      {
        tier: "Super",
        price: 19.99,
        count: 0
      },
      {
        tier: "Diesel",
        price: 9.99,
        count: 4
      },
      {
        tier: "Regular",
        price: 4.99,
        count: 12
      },

    ]
  },
  {
    id: 5,
    img: "https://i.imgur.com/piLVV02.png",
    title: "Cheesse and Wine",
    place: "MAGNA VII",
    date_time: new Date("2023-07-21T18:00:00"),
    tiers: [
      {
        tier: "Super",
        price: 19.99,
        count: 3
      },
      {
        tier: "Diesel",
        price: 9.99,
        count: 0
      },
      {
        tier: "Regular",
        price: 4.99,
        count: 1
      },

    ]
  },
  {
    id: 6,
    img: "https://i.imgur.com/aKDtkl0.png",
    title: "React Dev Convention",
    place: "UCA",
    date_time: new Date("2023-08-23T09:00:00"),
    tiers: [
      {
        tier: "Super",
        price: 19.99,
        count: 2
      },
      {
        tier: "Diesel",
        price: 9.99,
        count: 1
      },
      {
        tier: "Regular",
        price: 4.99,
        count: 0
      },

    ],
  }
];

export default function Cards() {
  return (
    <>
      <div className="flex flex-wrap w-full h-screen items-center justify-center">
        {devEvents.map((event, index) => (
          <EventCardSt key={index} event={event} />
        ))}
      </div>
      <hr />
      <div className="flex flex-wrap w-full h-screen items-center justify-center">
        {devEvents.map((event, index) => (
          <EventCardCh key={index} event={event} />
        ))}
      </div>
      <hr />
      <div className="flex flex-wrap w-full h-screen items-center justify-center">
        {devEvents.map((event, index) => (
          <EventCardMi key={index} event={event} />
        ))}
      </div>
      <hr />
      <div className="flex flex-wrap overflow-auto w-full h-screen items-center justify-center">
        {devEvents.map((event, index) => (
          <EventCardSh key={index} event={event} />
        ))}
      </div>
      <hr />
    </>
  );
}
