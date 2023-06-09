import { devEvents } from "../Cards";
import { useParams } from "react-router-dom";
import Landing from "../../Landing";
import { useState } from "react";
import { MdAddBox, MdIndeterminateCheckBox } from "react-icons/md"

const OneEvent = () => {
  const { eventId } = useParams();
  console.log(eventId);
  const currentEvent = devEvents[eventId];

  if (currentEvent != undefined)
    return (
      <section className={`min-h-[calc(100vh-52px-3.5rem)] max-h-[calc(100vh-52px-4rem)] overflow-x-hidden overflow-y-auto`}>

        <div className="">
          <div className="min-h-[calc(40vh-52px-2rem)] relative bg-cover bg-top" style={{ backgroundImage: `url(${currentEvent.img})` }}>
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
                <EventTiers event={currentEvent} />
              </div>
            </div>
          </div>
        

        <div className='flex w-full items-center md:justify-center md:gap-28 justify-evenly mt-default'>
          <button className='subaction-button'> Cancel </button>
          <button className='action-button'> Add To Cart </button>
        </div>

      </section>
    );
  else return <Landing />;
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
  return (
    <div className="text-center">
      <div className="w-fit px-default py-default rounded-lg bg-secondary bg-opacity-30">
        <p className="text-6xl">{event.date_time.getDate()}</p>
        <p className="uppercase -mt-2">{event.date_time.toLocaleString('en-US', { month: 'short' })}</p>
      </div>
    </div>
  )
}

function EventInfo({ event }) {
  return (
    <div>
      <LinnedText text={event.place} />
      <LinnedText text={`${event.date_time.toLocaleString('en-US', { hour: 'numeric', minute: '2-digit', hour12: true })} - 9:00 PM`} />
    </div>
  )
}

function LinnedText({ text }) {
  return (
    <div className="md:subtitle border-l-2 border-primary pl-default-sm my-default-xs">{text}</div>
  )
}

function EventTiers({ event }) {

  return (
    <div>
      {event.tiers.map((tier, index) => {
        const [tierCount, setTierCount] = useState(0);

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
            <p className="px-default-sm"> {tier.tier} </p>
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
  )
}


export default OneEvent;


