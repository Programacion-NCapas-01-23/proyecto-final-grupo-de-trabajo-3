import { MdCameraAlt } from "react-icons/md"

export default function CreateEvent() {

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
            <div className='flex w-full items-center md:justify-center md:gap-28 justify-evenly'>
                <button className='subaction-button'> Adios </button>
                <button className='action-button'> Hola </button>
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

function FormInput({ label, type, isDatalist, options }) {
    if (type === undefined) type = "text";

    if (!isDatalist) {
        return (
            <div className="mb-default-sm">
                <p className="heading-sm">{label}</p>
                <input
                    className="cursor-pointer bg-opacity-20 bg-white w-full p-default-sm rounded-md"
                    type={type}
                />
            </div>
        );
    } else {
        return (
            <div className="mb-default-sm">
                <p className="heading-sm">{label}</p>
                <select className="bg-opacity-20 cursor-pointer bg-white w-full p-default-sm rounded-md">
                    {options.map((option) => (
                        <option className='text-black' key={option} value={option}>
                            {option}
                        </option>
                    ))}
                </select>
            </div>
        );
    }
}


