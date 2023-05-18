
export default function EventCard(props) {
  const event = props.event

  return (
    <div className="text-center m-default-xs md:w-48 w-36">
      <div className="p-2 bg-secondary bg-opacity-30 rounded-md">
        <img className="object-cover md:w-48 md:h-32 w-36 h-20" src={event.img} alt="" />
      </div>
      <p>{event.title}</p>
    </div>
  )
}
