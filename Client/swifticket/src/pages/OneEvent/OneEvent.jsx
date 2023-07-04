import { useNavigate, useParams } from "react-router-dom";
import Landing from "../../Landing";
import { Toaster, toast } from 'react-hot-toast'
import { useEffect, useState } from "react";
import { MdAddBox, MdIndeterminateCheckBox } from "react-icons/md"
import { getEventById } from "../../services/Events.Services";
import { shoppingCartState } from "../../state/atoms/shoppingCartState";
import { useRecoilState, useRecoilValue } from "recoil";
import { guestState } from "../../state/atoms/guestState";

const OneEvent = () => {
  const [shoppingCart, setShoppingCart] = useRecoilState(shoppingCartState);

  const { eventId } = useParams();
  const navigate = useNavigate();
  const isGuest = useRecoilValue(guestState)

  // Set up state variables
  const [currentEvent, setCurrentEvent] = useState(null);
  const [reducedEvent, setReducedEvent] = useState(null);
  const initialTierCounts = {};

  const [tierCounts, setTierCounts] = useState({});
  const [tierCountsDisplay, setTierCountsDisplay] = useState({});

  // Shopping cart validations function
  const shoppingCartValidations = () => {
    if (isGuest){
      toast.error("You have to log in!", { id: 'shcart' });
      return false;
    }

    if (!oneSelected(tierCounts) || !oneSelected(tierCountsDisplay)) {
      setTierCounts(initialTierCounts); currentEvent.tiers.forEach(tier => { initialTierCounts[tier.id] = 0; }); setTierCountsDisplay(initialTierCounts);

      toast.error("You have to select at least one!", { id: 'shcart' });
      return false;
    }

    if (!checkAvailability(tierCounts, reducedEvent.tiers)) {
      setTierCounts(initialTierCounts); currentEvent.tiers.forEach(tier => { initialTierCounts[tier.id] = 0; }); setTierCountsDisplay(initialTierCounts);

      toast.error("Not enough capacity!", { id: 'shcart' });
      return false;
    }


    setReducedEvent(prev => ({ ...prev, tiers: prev.tiers.map(tier => ({ ...tier, count: (tier.count || 0) + (tierCounts[tier.id] || 0) })) }));
    currentEvent.tiers.forEach(tier => { initialTierCounts[tier.id] = 0; }); setTierCountsDisplay(initialTierCounts);

    return true;
  };

  const handleAddToCart = () => {
    // Validate the shopping cart
    if (!shoppingCartValidations()) return;

    setShoppingCart(prev => {
      // Update the cart items
      const updatedCart = prev.map(item => {
        // Check if the item is the current event
        const isCurrentEvent = item.id === currentEvent.id;

        // Update the tiers for the current event
        const updatedTiers = isCurrentEvent ? item.tiers.map(tier => ({ ...tier, count: (tier.count || 0) + (tierCounts[tier.id] || 0) })) : item.tiers;

        // Return the updated item
        return isCurrentEvent ? {
          ...item,
          tiers: updatedTiers
        } : item;
      });

      // Check if the current event exists in the cart
      const eventExistsInCart = updatedCart.some(item => item.id === currentEvent.id);

      // If the current event is not in the cart, add it with updated tiers
      if (!eventExistsInCart) {
        const updatedEventTiers = currentEvent.tiers.map(tier => ({ ...tier, count: (tier.count || 0) + (tierCounts[tier.id] || 0) }));

        updatedCart.push({
          ...reducedEvent,
          tiers: updatedEventTiers
        });
      }

      return updatedCart;
    });

    // Show success toast
    toast.success("Items added to your cart!");

    // Save the shopping cart to session storage
    sessionStorage.setItem('shoppingCart', JSON.stringify(shoppingCart));

    // Reset tier counts
    setTierCounts(initialTierCounts);
  };


  // Save the shoppingCart to sessionStorage whenever it changes
  useEffect(() => {
    sessionStorage.setItem('shoppingCart', JSON.stringify(shoppingCart));
  }, [shoppingCart]);

  // Fetch the event data from the server based on the eventId
  useEffect(() => {
    const fetchEvent = async () => {
      const response = await getEventById(eventId);

      if (response.status == 404)
        navigate("/event");

      const { available, category, duration, organizer, place, sponsors, state, ...restructuredEvent } = response.data;

      let modifiedTiers
      if (!shoppingCart.find(ev => ev.id === restructuredEvent.id)) {
        modifiedTiers = response.data.tiers.map(({ available, ...tier }) => ({ ...tier, count: 0 }));
      } else {
        modifiedTiers = response.data.tiers.map(({ available, ...tier }) => {
          const cartItem = shoppingCart.find(ev => ev.id === restructuredEvent.id);
          const tierFromCart = cartItem.tiers.find(t => t.id === tier.id);
          const count = tierFromCart ? tierFromCart.count : 0;
          return { ...tier, count: count };
        });
      }


      setReducedEvent({ ...restructuredEvent, tiers: modifiedTiers });
      setCurrentEvent(response.data);

      response.data.tiers.forEach(tier => {
        initialTierCounts[tier.id] = 0;
      });

      setTierCounts(initialTierCounts);
      setTierCountsDisplay(initialTierCounts);
    };

    fetchEvent();
  }, []);

  return (
    currentEvent ? <section className={`min-h-[calc(100vh-52px-3.5rem)] max-h-[calc(100vh-52px-4rem)] overflow-x-hidden overflow-y-auto`}>
      <Toaster position="top-right" />
      <div className="">
        <div className="min-h-[calc(40vh-52px-2rem)] relative bg-cover bg-center" style={{ backgroundImage: `url(${currentEvent?.image})` }}>
          <div className="min-h-[calc(40vh-52px-2rem)] invisible lg:visible absolute backdrop-blur-lg backdrop-brightness-50 w-full" />
          <img className="max-h-[calc(40vh-52px-2rem)] invisible lg:visible shadow-2xl absolute top-0 left-1/2 -translate-x-1/2 border-4 border-black" src={currentEvent.image} alt="" />
          <div className="min-h-[calc(40vh-52px-2rem)] w-full absolute bg-gradient-to-t from-default  md:via-transparent to-transparent"></div>
        </div>
      </div>

      <TitileWithLines title={currentEvent?.title}></TitileWithLines>

      <div className="flex md:flex-row flex-col items-center justify-evenly min-h-[calc(30vh-52px-2rem)] md:px-default-2xl px-default-lg pt-default">
        <div className="flex flex-row justify-evenly items-center gap-12">
          <DateInfo event={currentEvent} />
          <EventInfo event={currentEvent} />
        </div>

        <div className="flex justify-center h-fit">
          <div className="md:w-fit mt-default-sm flex justify-center bg-secondary bg-opacity-30 p-default-sm rounded-lg">
            <div>
              {currentEvent.tiers?.map((tier, index) => {

                const handleDecreaseCount = (tierId) => {
                  if (tierCounts[tierId] > 0) {
                    setTierCounts(prevCounts => ({
                      ...prevCounts,
                      [tierId]: prevCounts[tierId] - 1
                    }));
                    setTierCountsDisplay(prevCounts => ({
                      ...prevCounts,
                      [tierId]: prevCounts[tierId] - 1
                    }));
                  }
                };

                const handleIncreaseCount = (tierId) => {
                  setTierCounts(prevCounts => ({
                    ...prevCounts,
                    [tierId]: prevCounts[tierId] + 1
                  }));
                  setTierCountsDisplay(prevCounts => ({
                    ...prevCounts,
                    [tierId]: prevCounts[tierId] + 1
                  }));
                };
                const tierCount = tierCountsDisplay[tier.id];
                return (

                  <div key={index} className="grid grid-flow-col grid-cols-4 sm:gap-8 p-default-xs md:text-2xl">
                    <p className="px-default-sm col-span-2"> {tier.name} </p>
                    <p className="px-default-sm self-center"> ${tier.price} </p>
                    <div className="flex flex-row items-center">
                      <button onClick={() => { handleDecreaseCount(tier.id) }}>
                        <MdIndeterminateCheckBox size="1.5rem" className="text-lg" />
                      </button>
                      <p className="px-default-sm"> {tierCount} </p>
                      <button onClick={() => { handleIncreaseCount(tier.id) }}>
                        <MdAddBox size="1.5rem" className="text-lg" />
                      </button>
                    </div>
                  </div>
                );
              })}
            </div>
          </div>
        </div>
      </div>


      <div className='flex w-full items-center md:justify-center md:gap-28 justify-evenly mt-default'>
        <button onClick={() => { navigate(-1) }} className='subaction-button'> Cancel </button>
        <button onClick={handleAddToCart} className='action-button'> Add To Cart </button>
      </div>

    </section> :
      <div className="flex min-h-[calc(100vh-52px-3.5rem)] w-screen justify-center items-center">
        Loading...
      </div>
  );
};

function TitileWithLines({ title }) {
  return (
    <span className="w-full flex px-default-lg pt-default items-center">
      <div className='border sm:w-default-xl w-default-sm  h-0 border-primary'></div>
      <h1 className='md:title sm:w-1/2 w-full subtitle text-center'>{title}</h1>
      <div className='border sm:w-full w-default-sm h-0 border-primary'></div>
    </span>
  )
}

function DateInfo({ event }) {
  const date_time = new Date(event?.dateTime)

  return (
    <div className="text-center">
      <div className="w-fit px-default py-default rounded-lg bg-secondary bg-opacity-30">
        <p className="text-6xl">{date_time.getDate()}</p>
        <p className="uppercase -mt-2">{date_time.toLocaleString('en-US', { month: 'short' })}</p>
      </div>
    </div>
  )
}

function EventInfo({ event }) {
  const date_time = new Date(event?.dateTime)
  return (
    <div>
      <LinnedText text={event?.place?.name} />
      <p className="text-sm sm:text-xl leading-3 -mt-2 font-light tracking-tighter"> {event?.place?.address} </p>
      <LinnedText text={`${date_time.toLocaleString('en-US', { hour: 'numeric', minute: '2-digit', hour12: true })}`} />
    </div>
  )
}

function LinnedText({ text }) {
  return (
    <div className="md:subtitle border-l-2 border-primary pl-default-sm my-default-xs">{text}</div>
  )
}

function oneSelected(tiersSelected) {
  if (Object.values(tiersSelected).reduce((acc, curr) => acc + curr, 0) == 0)
    return false
  else
    return true
}

function checkAvailability(tickets, tiers) {
  for (let i = 0; i < tiers.length; i++) {
    const tier = tiers[i];
    const count = tickets[tier.id] + tier.count;
    const remainingCapacity = tier.capacity - tier.ticketsSold;
    if (count > remainingCapacity) {
      return false;
    }
  }
  return true;
};

export default OneEvent;