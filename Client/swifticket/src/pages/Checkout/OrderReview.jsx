import React from 'react'
import EventCardCh from '../../components/EventCards/Checkout/EventCardCh';

export default function OrderReview() {

  const devEvents = [
    {
      img: "https://i.imgur.com/CqnHHbp.png",
      title: "Mando Cosplays",
      place: "Richard Vermont Plaza",
      date_time: new Date("2023-06-16T13:00:00"),
      tiers: [
        {
          tier: "Super",
          price: 19.99,
          count: 1
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
      img: "https://i.imgur.com/piLVV02.png",
      title: "Cheesse and Wine",
      place: "MAGNA VII",
      date_time: new Date("2023-07-21T18:00:00"),
      tiers: [
        {
          tier: "Super",
          price: 19.99,
          count: 2
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
    }
  ];


  const getTotalTiersBought = () => {
    let total = 0;

    devEvents.forEach((event) => {
      event.tiers.forEach((tier) => {
        total += tier.price * tier.count
      });
    });

    return total;
  };

  return (
    <div className='flex justify-center'>
      <div className='md:w-1/2'>
        <ul role="list">
          {devEvents.map((event, index) => (
            <li key={index}>
              <EventCardCh count={index + 1} event={event} />
            </li>
          ))}
        </ul>
        <p className="md:heading-xl heading-md py-default text-right pr-default-lg"> Total to Pay: ${Math.round((getTotalTiersBought() + Number.EPSILON) * 100) / 100}</p>
      </div>
    </div>
  )
}
