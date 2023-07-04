import LinnedText from "../../../components/EventCards/Standard/components/LinnedText";

export default function TicketInfo({ event, tier}) {
    const date_time = new Date(event?.dateTime)

    return (
    <div className="relative pl-default-lg pr-1 md:pr-default-lg">
        <span className="w-full h-full z-0 right-0 absolute  opacity-30 rounded-r-2xl"/>
        <div className="w-full relative py-default max-h-48 overflow-auto">
        <h1 className="subtitle">{event?.title ?? "Event"}</h1>
        <div>
            <LinnedText text={tier?.name ?? "Place"} />
            <LinnedText text={date_time?.toLocaleString('en-US', { hour: 'numeric', minute: '2-digit', hour12: true })} />
        </div>
        </div>
    </div>
    );
}
