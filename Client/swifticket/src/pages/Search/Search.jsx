import { useEffect, useState } from 'react'
import { useSearchParams } from 'react-router-dom';
import { getAllEvents } from '../../services/Events.Services';
import EventCardMi from '../../components/EventCards/Minimized/EventCardMi';

export default function Search() {

    const [searchParams] = useSearchParams();
    console.log(searchParams.get('eventName'));
    const [events, setEvents] = useState([])


    useEffect(() => {
        const fetchEvents = async () => {
            const response = await getAllEvents(searchParams.get('eventName'));
            setEvents(response.data)
        }
        fetchEvents()
    }, [])


    return (
        <div className='min-h-[calc(100vh-52px-3.5rem)]'>
            <div className='grid grid-cols-2 sm:grid-cols-3 items-center'>
                {events.content && events.content.length > 0 ? (
                    events.content.map((event, index) => (
                        <EventCardMi key={index} event={event} />
                    ))
                ) : <h1> Loading...</h1>
                }
            </div>
        </div>
    )
}
