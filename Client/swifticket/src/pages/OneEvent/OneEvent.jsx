import React from "react";
import { devEvents } from "../Cards";
import { useParams } from "react-router-dom";
import Landing from "../../Landing";

const OneEvent = () => {
  const { eventId } = useParams();
  console.log(eventId);
  const currentEvent = devEvents[eventId];
  currentEvent.img =
    "https://www.bigbearmountainresort.com/-/media/big-bear-mountain-resort-media/hero-images-tinted/tinted-2018-08-16_ss_jm_durkinfamily_greentrail_-7.jpg?rev=5589d635c6e9416eb50cc4d887d01ffa?h=1350&w=2400&hash=85E56DECD3A25A02C888B7D00F49E1FA";
  if (currentEvent != undefined)
    return (
      <section className={`min-h-[calc(100vh-52px-4rem)] max-h-[calc(100vh-52px-4rem)] overflow-scroll`}>
        
        <div className="">
          <div className="min-h-[calc(40vh-52px-2rem)] relative bg-center" style={{backgroundImage: `url(${currentEvent.img})`}}>
          <div className="min-h-[calc(40vh-52px-2rem)] w-full absolute bg-gradient-to-t from-default to-transparent"></div>
          </div>
        </div>

        <TitileWithLines title={currentEvent.title}></TitileWithLines>
        
        <div className="border border-red-500 min-h-[calc(50vh-52px-2rem)] md:px-default-2xl px-default-lg py-default-xl">
          <div className="border border-sky-400 ">
            Hoa
          </div>
        </div>
      </section>
    );
  else return <Landing />;
};

function TitileWithLines({ title }) {
  return (
    <span className="w-full grid grid-cols-8 px-default-lg pt-default-lg items-center">
      <div className='border h-0 border-primary'></div>
      <h1 className='md:title subtitle text-center md:col-span-2 col-span-5'>{title}</h1>
      <div className='border h-0 border-primary md:col-span-5 col-span-2'></div>
    </span>
  )
}

export default OneEvent;


