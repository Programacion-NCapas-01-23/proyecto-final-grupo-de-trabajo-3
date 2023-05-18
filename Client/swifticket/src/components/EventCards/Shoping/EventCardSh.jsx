
export default function EventCardSh(props) {
  const event = props.event;

  return (
    <div className="flex flex-row w-full items-center m-default-xs">
      <p className="p-default">{props.count}x</p>
      <div className="border-l-2 border-primary h-32 mr-default-sm"/>
      <span className="bg-secondary p-1 rounded-sm bg-opacity-30 w-32">
        <img className="object-cover h-24" src={event.img} alt="" />
      </span>
      <p className="ml-default-xs"> {event.title}</p>
    </div>
  )
}
