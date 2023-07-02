
export default function EventCardSh({ event }) {

  const tiersTotal = event.tiers.reduce((acc, curr) => acc + curr.count, 0);

  return (
    <div className="flex flex-row w-full items-center m-default-xs">
      <p className="sm:p-default">{tiersTotal}x </p>
      <div className="border-l-2 ml-4 border-primary h-24 mr-4" />
      <div className="w-1/3">
        <img className="object-center object-scale-down border-4 border-secondary border-opacity-50" src={event.image} alt="" />
      </div>
      <p className="ml-default-xs"> {event.title}</p>
    </div>
  )
}
