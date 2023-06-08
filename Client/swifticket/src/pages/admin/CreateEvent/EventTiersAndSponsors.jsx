import React from 'react'
import TitileWithLines from './components/TitleWithLines'
import FormInput from './components/FormInput'

export default function EventTiersAndSponsors() {
    const eventSponsors = [ "Nike", "Apple", "Coca-Cola", "Google", "Amazon", "Microsoft" ];

    return (
        <section className='w-full px-default'>
            <TitileWithLines title="Tiers and Sponsors" />
            <div className="flex flex-col items-center py-default min-h-[calc(100vh-18rem)]">
                <section className='w-1/2'>
                    <FormInput label={"Sponsors"} isDatalist={true} options={eventSponsors} />
                </section>
                <section className='w-1/2'>
                    <TiersInput/>
                </section>
            </div>
        </section>
    )
}

function TiersInput() {
    return(
        <div className='grid grid-flow-col gap-4'>
            <FormInput  label={"Name"} />
            <FormInput label={"Capacity"} type={"number"}/>
            <FormInput label={"Price"} type={"number"}/>
        </div>
    )
}
