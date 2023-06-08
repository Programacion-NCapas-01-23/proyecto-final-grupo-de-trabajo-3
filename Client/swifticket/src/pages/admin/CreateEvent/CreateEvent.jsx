import EventInfo from "./EventInfo"
import EventTiersAndSponsors from "./EventTiersAndSponsors"

export default function CreateEvent() {
    
    return (
        <main className= "flex flex-col w-full min-h-full">
            <EventTiersAndSponsors/>
            <div className='flex w-full items-center md:gap-28 justify-evenly'>
                <button className='subaction-button'> Cancel </button>
                <button className='action-button'> Continue </button>
            </div>
        </main>
    )
}
