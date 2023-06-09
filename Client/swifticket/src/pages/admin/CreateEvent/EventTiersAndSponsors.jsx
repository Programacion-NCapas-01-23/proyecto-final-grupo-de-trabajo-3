import React, { useState } from 'react'
import TitileWithLines from './components/TitleWithLines'
import FormInput from './components/FormInput'
import { MdAddBox, MdDelete, MdDeleteSweep } from 'react-icons/md';

export default function EventTiersAndSponsors() {
    const eventSponsors = ["Nike", "Apple", "Coca-Cola", "Google", "Amazon", "Microsoft"];

    return (
        <section className='w-full px-default'>
            <TitileWithLines title="Event Details" />
            <div className="flex flex-col items-center py-default min-h-[calc(100vh-18rem)]">
                <section className='w-1/2'>
                    <Sponsors sponsorsList={eventSponsors} />
                </section>
                <section className='w-1/2'>
                    <Tiers />
                </section>
            </div>
        </section>
    )
}

function Tiers() {
    const [tiers, setTiers] = useState([])

    const [name, setName] = useState("")
    const [capacity, setCapacity] = useState("")
    const [price, setPrice] = useState("")

    const handleAddTier = (tierName, tierCapacity, tierPrice) => {
        if (tierName !== "" && tierCapacity !== "" && tierPrice !== "") {
            const newTier = [tierName, tierCapacity, tierPrice]
            setName("");
            setCapacity("");
            setPrice("");
            setTiers([...tiers, newTier])
        }
    }

    const handleDeleteTier = (index) => {
        const updatedTiers = [...tiers];
        updatedTiers.splice(index, 1);
        setTiers(updatedTiers);
    }

    return (
        <>
            <div className='grid grid-flow-col gap-4'>
                <FormInput label='Name' type='text' value={name} onInputChange={setName} />
                <FormInput label='Capacity' type='number' value={capacity} onInputChange={setCapacity} />
                <FormInput label='Price' type='number' value={price} onInputChange={setPrice} />
                <button onClick={() => { handleAddTier(name, capacity, price) }} className='justify-self-end text-4xl text-primary'>
                    <MdAddBox />
                </button>
            </div>
            <ul className='max-h-52 overflow-auto'>
                {tiers.map((tier, index) => (
                    <li key={index} className='my-default-xs grid grid-flow-col grid-cols-4 gap-4 text-neutral-300'>
                        <div className='col-span-2 p-default bg-neutral-950 rounded-xl '>
                            {tier[0]}
                        </div>
                        <div className='p-default bg-neutral-950 rounded-xl '>
                            {tier[1]}
                        </div>
                        <div className='p-default bg-neutral-950 rounded-xl '>
                            ${tier[2]}
                        </div>
                        <button onClick={() => handleDeleteTier(index)} className='text-4xl text-red-500'>
                            <MdDelete />
                        </button>
                    </li>
                ))}
            </ul>
        </>
    )
}

function Sponsors({ sponsorsList }) {

    const [sponsors, setSponsors] = useState([]);
    const [selectedSponsor, setSelectedSponsor] = useState(sponsorsList[0]);

    const handleAddSponsor = () => {
        if (selectedSponsor !== "" && !sponsors.includes(selectedSponsor)) {
            setSponsors([...sponsors, selectedSponsor]);
            setSelectedSponsor(sponsorsList[0]);
        }
    };


    const handleDeleteSponsor = (index) => {
        const updatedSponsors = [...sponsors];
        updatedSponsors.splice(index, 1);
        setSponsors(updatedSponsors);
    };

    return (
        <>
            <div className="grid grid-flow-col grid-cols-4 gap-4">
                <div className="col-span-4">
                    <FormInput
                        label={"Sponsors"}
                        isDatalist={true}
                        options={sponsorsList}
                        value={selectedSponsor}
                        onInputChange={setSelectedSponsor}
                    />
                </div>
                <button onClick={handleAddSponsor} className="text-4xl text-primary">
                    <MdAddBox />
                </button>
            </div>
            <ul className="max-h-52 overflow-auto">
                {sponsors.map((sponsor, index) => (
                    <li key={index} className="my-default-xs grid grid-flow-col grid-cols-4 gap-4">
                        <div className="col-span-4 p-default bg-neutral-950 rounded-xl text-neutral-300">
                            {sponsor}
                        </div>
                        <button onClick={() => handleDeleteSponsor(index)} className="text-4xl text-red-500">
                            <MdDelete />
                        </button>
                    </li>
                ))}
            </ul>
        </>
    );
}
