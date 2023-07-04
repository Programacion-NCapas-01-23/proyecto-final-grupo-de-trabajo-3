import LinnedText from "./LinnedText";

export default function EventInfo({ event, date }) {
  const eventTitle = event.title

  return (
    <div className="relative pl-default-lg pr-1 md:pr-default-lg">
      <span className="w-full h-full z-0 right-0 absolute  opacity-30 rounded-r-2xl"/>
      <div className="w-full relative py-default max-h-48 overflow-auto">
        <h1 className="subtitle">{eventTitle}</h1>
        <div>
          <LinnedText text={event.place.name} />
          <LinnedText text={date.toLocaleString('en-US', { hour: 'numeric', minute: '2-digit', hour12: true })} />
        </div>
      </div>
    </div>
  );
}
