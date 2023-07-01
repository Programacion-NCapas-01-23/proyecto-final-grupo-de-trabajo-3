
export default function EventCardSh({ event }) {

  const tiersTotal = event.tiers.reduce((acc, curr) => acc + curr.count, 0);

  return (
    <div className="flex flex-row w-full items-center m-default-xs">
      <p className="p-default">{tiersTotal} x </p>
      <div className="border-l-2 border-primary h-32 mr-default-sm" />
      <img className="object-center max-h-24 min-h-24 border-4 border-secondary border-opacity-50" src={event.image} alt="" />
      <p className="ml-default-xs"> {event.title}</p>
    </div>
  )
}
