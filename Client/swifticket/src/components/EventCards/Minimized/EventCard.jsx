
export default function EventCard(props) {
  const event = props.event

  return (
    <div>
      <span>
        <img src={event.img} alt="" />
      </span>
      <p>{event.title}</p>
    </div>
  )
}
