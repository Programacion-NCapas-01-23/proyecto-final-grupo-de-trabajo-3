import { useNavigate, useParams } from "react-router-dom";
import Landing from "../../Landing";
import { Toaster, toast } from 'react-hot-toast'
import { useEffect, useState } from "react";
import { MdAddBox, MdIndeterminateCheckBox } from "react-icons/md"
import { getEventById } from "../../services/Events.Services";
import { shoppingCartState } from "../../state/atoms/shoppingCartState";
import { useRecoilState } from "recoil";

const OneEvent = () => {
  const [shoppingCart, setShoppingCart] = useRecoilState(shoppingCartState);

  const { eventId } = useParams();
  const navigate = useNavigate();
  const [currentEvent, setCurrentEvent] = useState(null);
  const initialTierCounts = {};

  const [tierCounts, setTierCounts] = useState({});
  const [tierCountsDisplay, setTierCountsDisplay] = useState({});


  const shoppingCartValidations = () => {
    // RESET VISUALS SELECTED
    currentEvent.tiers.forEach(tier => {
      initialTierCounts[tier.id] = 0;
    });
    setTierCountsDisplay(initialTierCounts)

    // IF NONE SELECTED
    if (!oneSelected(tierCounts) || !oneSelected(tierCountsDisplay)) {
      setTierCounts(initialTierCounts)
      updateEvent()
      toast.error("You have to select at least one!", { id: 'shcart' })
      return false
    }

    if (!checkAvailability(tierCounts, currentEvent.tiers)) {
      setTierCounts(initialTierCounts)
      toast.error("Not enough capacity!", { id: 'shcart' })
      return false
    }
    return true
  }

  const handleAddToCart = () => {
    if (shoppingCartValidations()) {
      toast.success("Items added to your cart!", { duration: 2500 })
      setTierCounts(initialTierCounts)
      updateEvent()
    }
  }

  const updateEvent = () => {
    setCurrentEvent(prev => ({
      ...prev,
      tiers: prev.tiers.map(tier => ({
        ...tier,
        count: (tier.count || 0) + (tierCounts[tier.id] || 0)
      }))
    }));
  };


  useEffect(() => {
    const fetchEvent = async () => {
      const response = await getEventById(eventId);
      const tiers = response.data.tiers;
      const restructuredTiers = tiers.map(tier => ({ ...tier, count: 0 }));

      const restructuredEvent = { ...response.data, tiers: restructuredTiers };
      setCurrentEvent(restructuredEvent);

      restructuredTiers.forEach(tier => {
        initialTierCounts[tier.id] = 0;
      });
      setTierCounts(initialTierCounts);
      setTierCountsDisplay(initialTierCounts)
    };

    fetchEvent();

  }, []);


  if (!currentEvent)
    return <Landing />;

  return (
    <section className={`min-h-[calc(100vh-52px-3.5rem)] max-h-[calc(100vh-52px-4rem)] overflow-x-hidden overflow-y-auto`}>
      <Toaster position="top-right" />
      <div className="">
        <div className="min-h-[calc(40vh-52px-2rem)] relative bg-cover bg-center" style={{ backgroundImage: `url(${currentEvent.image})` }}>
          <div className="min-h-[calc(40vh-52px-2rem)] invisible lg:visible absolute backdrop-blur-lg backdrop-brightness-50 w-full" />
          <img className="max-h-[calc(40vh-52px-2rem)] invisible lg:visible shadow-2xl absolute top-0 left-1/2 -translate-x-1/2 border-4 border-black" src={currentEvent.image} alt="" />
          <div className="min-h-[calc(40vh-52px-2rem)] w-full absolute bg-gradient-to-t from-default  md:via-transparent to-transparent"></div>
        </div>
      </div>

      <TitileWithLines title={currentEvent.title}></TitileWithLines>


      <div className="flex md:flex-row flex-col items-center justify-evenly min-h-[calc(30vh-52px-2rem)] md:px-default-2xl px-default-lg pt-default">
        <button onClick={() => { console.log({ Event: currentEvent }); }}> LOG </button>
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

    </section>
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
  const date_time = new Date(event.dateTime)

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
  const date_time = new Date(event.dateTime)
  return (
    <div>
      <LinnedText text={event.place.name} />
      <p className="text-sm sm:text-xl leading-3 -mt-2 font-light tracking-tighter"> {event.place.address} </p>
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

function checkAvailability(tierCounts, tiers) {
  for (let i = 0; i < tiers.length; i++) {
    const tier = tiers[i];
    const count = tierCounts[tier.id] + tier.count;
    const remainingCapacity = tier.capacity - tier.ticketsSold;
    if (count > remainingCapacity) {
      return false;
    }
  }
  return true;
};

function addItemsToCart(count, event) {
  let updatedEventTiers = event.tiers;

  for (let i = 0; i < event.tiers.length; i++) {

  }
}

export default OneEvent;