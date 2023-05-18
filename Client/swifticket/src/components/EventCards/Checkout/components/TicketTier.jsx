export default function TicketTier(props) {
  const tier = props.tier
  return (
    <div className="flex justify-between">
      <p className="md:text-lg pl-default-sm">{tier.tier} x {tier.count}</p>
      <p className="md:text-lg pl-default-sm">${tier.price}</p>
    </div>
  )
}
