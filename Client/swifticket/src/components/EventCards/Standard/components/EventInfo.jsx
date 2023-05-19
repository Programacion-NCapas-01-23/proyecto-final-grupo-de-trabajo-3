import LinnedText from "./LinnedText";

export default function EventInfo(props) {
  const dateTime = props.event.date_time
  const eventTitle = props.event.title
  const eventLocation = props.event.place

  return (
    <div className="relative pl-default-lg pr-1 md:pr-default-lg">
      <span className="w-full h-full z-0 right-0 absolute bg-secondary opacity-30 rounded-r-2xl"/>
      <div className="w-full relative py-default max-h-48 overflow-auto">
        <h1 className="subtitle">{eventTitle}</h1>
        <div>
          <LinnedText text={eventLocation} />
          <LinnedText text={dateTime.toLocaleString('en-US', { hour: 'numeric', minute: '2-digit', hour12: true })} />
        </div>
      </div>
    </div>
  );
}
