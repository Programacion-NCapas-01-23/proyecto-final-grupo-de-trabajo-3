import React from 'react'
import EventCardCh from '../../components/EventCards/Checkout/EventCardCh';
import { shoppingCartState } from '../../state/atoms/shoppingCartState';
import { useRecoilState } from 'recoil';

export default function OrderReview() {

  const [shoppingCart, setShoppingCart] = useRecoilState(shoppingCartState);


  const getTotalTiersBought = () => {
    let total = 0;

    shoppingCart.forEach((event) => {
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
          {shoppingCart.map((event, index) => (
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
