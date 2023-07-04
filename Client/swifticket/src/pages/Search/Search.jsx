import { useEffect, useState } from 'react'
import { useSearchParams } from 'react-router-dom';
import { getAllEvents } from '../../services/Events.Services';
import EventCardSt from '../../components/EventCards/Standard/EventCardSt';

export default function Search() {

    const [searchParams] = useSearchParams();
    const [events, setEvents] = useState([])


    useEffect(() => {
        const fetchEvents = async () => {
            const response = await getAllEvents(1, searchParams.get('title'));
            setEvents(response.data)
        }
        fetchEvents()
    }, [])

    return (
        <div className='min-h-[calc(100vh-52px-3.5rem)]'>
            <span className="w-full flex px-default-lg pt-default items-center">
                <div className='border sm:w-default-xl w-default-sm  h-0 border-primary'></div>
                <h1 className='md:title sm:w-1/2 w-full subtitle text-center'>Search Results</h1>
                <div className='border sm:w-full w-default-sm h-0 border-primary'></div>
            </span>
            <div className='flex sm:gap-12 gap-4 items-center flex-wrap sm:flex-row flex-col sm:p-default-xl'>
                {events.content && events.content.length > 0 ? (
                    events.content.map((event, index) => (
                        <EventCardSt key={index} event={event} />
                    ))
                ) : <h1> Loading...</h1>
                }
            </div>
        </div>
    )
}
