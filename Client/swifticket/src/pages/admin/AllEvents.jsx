import React from 'react'
import { devEvents } from '../Cards'
import EventCardSt from '../../components/EventCards/Standard/EventCardSt'

export default function AllEvents() {

    const allEvents = devEvents.concat(
        devEvents,
        devEvents,
        devEvents
    );

    return (
        <section className="w-full px-default overflow-y-auto">
            <TitileWithLines title="All Events" />
            <div className="flex flex-wrap items-center justify-evenly">
                {allEvents.map((event, index) => (
                    <EventCardSt key={index} event={event} />
                ))}
            </div>
        </section>
    )
}


function TitileWithLines({ title }) {
    return (
        <span className="w-full grid grid-cols-6 px-default-lg pt-default-lg md:pb-default-xl pb-default-lg items-center">
            <div className='border h-0 border-primary'></div>
            <h1 className='md:title subtitle text-center md:col-span-2 col-span-3'>{title}</h1>
            <div className='border h-0 border-primary md:col-span-3 col-span-2'></div>
        </span>
    )
}
