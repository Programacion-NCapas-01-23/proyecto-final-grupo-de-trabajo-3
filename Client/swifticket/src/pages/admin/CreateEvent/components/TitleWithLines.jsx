export default function TitileWithLines({ title }) {
    return (
        <span className="w-full grid grid-cols-6 px-default-lg pt-default-lg md:pb-default-xl pb-default-lg items-center">
            <div className='border h-0 border-primary'></div>
            <h1 className='md:title subtitle text-center md:col-span-2 col-span-3'>{title}</h1>
            <div className='border h-0 border-primary md:col-span-3 col-span-2'></div>
        </span>
    )
}
