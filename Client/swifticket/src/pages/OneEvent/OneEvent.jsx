import { useNavigate, useParams } from "react-router-dom";
import Landing from "../../Landing";
import { Toaster, toast } from 'react-hot-toast'
import { useEffect, useState } from "react";
import { MdAddBox, MdIndeterminateCheckBox } from "react-icons/md"
import { getEventById } from "../../services/Events.Services";
import { shoppingCartState } from "../../state/atoms/shoppingCartState";
import { useRecoilState } from "recoil";

const OneEvent = () => {
  const [shoppingCart, setShoooingCart] = useRecoilState(shoppingCartState);
  
  const { eventId } = useParams();
  const navigate = useNavigate();
  const [currentEvent, setCurrentEvent] = useState();
  const [tierCount, setTierCount] = useState(0);


  const handleAddToCart = () => {
    
    console.log(currentEvent.tiers);

  }
  

  useEffect(() => {
    const fetchEvent = async () => {
      const response = await getEventById(eventId);
      const tiers = response.data.tiers;
      const restructuredTiers = tiers.map(tier => ({ ...tier, count: 0 }));

      const restructuredEvent = { ...response.data, tiers: restructuredTiers };
      setCurrentEvent(restructuredEvent);
    };

    fetchEvent();
  }, []);


  if (!currentEvent)
    return <div>Loading...</div>;

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
        <div className="flex flex-row justify-evenly items-center gap-12">
          <DateInfo event={currentEvent} />
          <EventInfo event={currentEvent} />
        </div>

        <div className="flex justify-center h-fit">
          <div className="md:w-fit mt-default-sm flex justify-center bg-secondary bg-opacity-30 p-default-sm rounded-lg">
            <div>
              {currentEvent.tiers.map((tier, index) => {
                const handleDecreaseCount = () => {
                  if (tierCount > 0) {
                    setTierCount(tierCount - 1);
                  }
                };
                const handleIncreaseCount = () => {
                  setTierCount(tierCount + 1);
                };
                return (
                  <div key={index} className="grid grid-flow-col grid-cols-3 gap-8 p-default-xs md:text-2xl">
                    <p className="px-default-sm"> {tier.name} </p>
                    <p className="px-default-sm"> ${tier.price} </p>
                    <div className="flex flex-row items-center">
                      <MdIndeterminateCheckBox onClick={handleDecreaseCount} />
                      <p className="px-default-sm"> {tierCount} </p>
                      <MdAddBox onClick={handleIncreaseCount} />
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
    <span className="w-full grid grid-cols-8 px-default-lg pt-default items-center">
      <div className='border h-0 border-primary'></div>
      <h1 className='md:title subtitle text-center md:col-span-2 col-span-5'>{title}</h1>
      <div className='border h-0 border-primary md:col-span-5 col-span-2'></div>
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
      <LinnedText text={`${date_time.toLocaleString('en-US', { hour: 'numeric', minute: '2-digit', hour12: true })} - 9:00 PM`} />
    </div>
  )
}

function LinnedText({ text }) {
  return (
    <div className="md:subtitle border-l-2 border-primary pl-default-sm my-default-xs">{text}</div>
  )
}


export default OneEvent;