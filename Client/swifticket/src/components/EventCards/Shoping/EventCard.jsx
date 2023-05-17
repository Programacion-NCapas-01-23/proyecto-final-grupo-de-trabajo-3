
export default function EventCard(props) {
  return (
    <div className="flex flex-row w-full max items-center">
      <p className="p-default">{props.count}x</p>
      <div className="border border-primary h-full mr-default-sm"/>
      <span className="bg-secondary p-1 rounded-sm bg-opacity-30 w-32">
        <img className="object-cover h-24" src={props.event.img} alt="" />
      </span>
    </div>
  )
}
