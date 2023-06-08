import { MdCameraAlt } from "react-icons/md"
import TitileWithLines from "./components/TitleWithLines";
import FormInput from "./components/FormInput";

export default function EventInfo() {

    const eventCategories = ["Music", "Sports", "Arts and Culture", "Business and Networking", "Community and Social"];
    const eventOrganizers = ["George Washington", "Abraham Lincoln", "Franklin D. Roosevelt", "Thomas Jefferson"];
    const eventPlaces = ["Boardwalk", "Park Place", "Reading Railroad", "Baltic Avenue", "Electric Company", "Water Works"];


    return (
        <section className="w-full px-default overflow-y-auto">
            <TitileWithLines title="Create Event" />
            <div className="grid px-default-2xl py-default grid-flow-col grid-cols-2 gap-default-xl ">
                <div>
                    <FormInput label="Title" />
                    <FormInput label="Category" isDatalist={true} options={eventCategories} />
                    <FormInput label="Organizer" isDatalist={true} options={eventOrganizers} />
                    <FormInput label="Date" type="date" />
                    <FormInput label="Start time" type="time" />
                    <FormInput label="End time" type="time" />
                </div>
                <div>
                    <FormInput label="Place" isDatalist={true} options={eventPlaces} />
                    <button className='w-full h-4/5 flex items-center justify-center bg-slate-500 bg-opacity-30 rounded-2xl text-8xl hover:text-9xl hover:bg-gray-200 hover:bg-opacity-20 transition-all'>
                        <MdCameraAlt />
                    </button>
                </div>
            </div>
        </section>
    )
}






