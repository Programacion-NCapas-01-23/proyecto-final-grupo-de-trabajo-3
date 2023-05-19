import TicketTier from "./TicketTier";

export default function EventInfo(props) {
  const eventTiers = props.event.tiers

  return (
    <div className="relative pl-default pr-default-lg">
      <span className="w-full h-full z-0 right-0 absolute bg-secondary opacity-30 rounded-r-2xl" />
      <div className="w-full relative py-default max-h-48 overflow-auto">
        <h1 className="subtitle mb-default-xs">{props.event.title}</h1>
        <div>
          {eventTiers.map((tier, index) => {
            if (tier.count == 0)
              return
            return (<TicketTier key={index} tier={tier} />)
          }
          )}
        </div>
      </div>
    </div>
  );
}
