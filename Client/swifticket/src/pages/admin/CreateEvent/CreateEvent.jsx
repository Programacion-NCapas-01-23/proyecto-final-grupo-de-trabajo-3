import { useState } from "react"
import EventInfo from "./EventInfo"
import EventTiersAndSponsors from "./EventTiersAndSponsors"

export default function CreateEvent() {

    const [isInDetails, setIsInDetails] = useState(false)
    
    return (
        <main className= "flex flex-col w-full min-h-full">
            {
                isInDetails ? (<EventTiersAndSponsors/>) :( <EventInfo />)
            }
            <div className='flex w-full items-center md:gap-28 justify-evenly'>
                <button onClick={() => {setIsInDetails(false)}} className='subaction-button'> {isInDetails ? "Go back" : "Cancel"} </button>
                <button onClick={() => {setIsInDetails(true)}} className='action-button'> {isInDetails ? "Create Event" : "Continue"} </button>
            </div>
        </main>
    )
}
