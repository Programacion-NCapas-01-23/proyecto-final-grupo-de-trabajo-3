import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';
import { getSystemState } from '../../../services/System.Services';
import { getEventStats } from '../../../services/StatsServices';
import { getEventById } from '../../../services/Events.Services';

export default function OneEventStats() {
    const token = useRecoilValue(tokenState);
    const [isActive, setIsActive] = useState(false);
    const [widgets, setWidgets] = useState(null);
    const [event, setEvent] = useState(null)
    const { eventId } = useParams();

    const handleSystemState = async () => {
        const response = await getSystemState();
        if (response.status === 200)
            response.data.status === 1 ? setIsActive(true) : setIsActive(false);
    };

    const handleWidgets = async () => {
        const response = await getEventStats(token, eventId);
        const eventResponse = await getEventById(eventId);
        setEvent(eventResponse.data)
        console.log(response.data);

        if (response.status === 200) {
            
            setWidgets([
                { id: 1, label: 'Users quantity', value: response.data.users },
                { id: 2, label: 'Events held', value: response.data.events },
                { id: 3, label: 'Tickets sold', value: response.data.ticketsSold },
                {
                    id: 4,
                    label: 'Event attendance percentage',
                    value: response.data.attendanceSingleVsGroup,
                },
            ]);
        } else {
            setWidgets([ ]);
        }
    };

    useEffect(() => {
        handleSystemState();
        handleWidgets();
    }, []);

    return (
        <div className="flex flex-col h-screen w-screen sm:w-[80vw]">
            <div className="flex flex-col sm:flex-row h-[30vh] sm:h-[20vh] w-full justify-between items-start sm:items-end pl-4 sm:pl-8">
                <div className="flex gap-2 justify-center items-center">
                    <span className="w-1 h-16 bg-primary" />
                    <p className="title"> {event?.title && `${event.title}`} </p>
                </div>
                <div className="flex justify-center items-center w-full sm:w-auto sm:h-full">
                </div>
            </div>
            <div className="flex flex-wrap sm:flex-nowrap h-[40vh] sm:h-[20vh] w-full justify-evenly items-center">
                
            </div>
            
            <div className="flex pt-4 pb-8 sm:pt-0 sm:pb-0 sm:h-[25vh] w-full justify-center items-center">
                <button className="bg-primary px-4 py-2 rounded-md">
                    Generate Report
                </button>
            </div>
        </div>
    );
}
