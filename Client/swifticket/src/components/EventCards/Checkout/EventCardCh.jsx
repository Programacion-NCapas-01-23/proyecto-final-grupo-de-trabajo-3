export default function EventCardCh(props) {
  let total = 0;

  props.event.tiers.forEach((element) => {
    if (element.count !== 0) total += element.price * element.count;
  });

  return (
    <section className="flex flex-col p-default">
      {/* CARD DIV */}
      <div className="flex flex-row pb-default">
        {/* IMAGE AND DATE DIV */}
        <div className="relative">
          <img
            className="object-cover h-48 md:w-[16rem] w-40 shadow-md rounded-l-2xl"
            src={props.event.img}
            alt="event_img"
          />
          <span className="absolute bottom-0 bg-secondary text-center px-3 py-2 rounded-bl-2xl shadow-md shadow-black">
            <p className="text-4xl">{props.event.date_time.getDate()}</p>
            <p className="uppercase -mt-2"> {props.event.date_time.toLocaleString("en-US", {month: "short"})} </p>
          </span>
        </div>
        {/*END OF IMAGE AND DATE DIV */}

        {/* EVENT INFO DIV */}
        <div className="w-full min-h-full pl-default pr-default-lg relative py-default overflow-auto rounded-r-2xl shadow-lg bg-secondary bg-opacity-30">
          <h1 className="subtitle mb-default-xs">{props.event.title}</h1>
          <div>
            {props.event.tiers.map((tier, index) => {
              if (tier.count === 0) return null;
              return (
                <div key={index} className="flex justify-between">
                  <p className="md:text-lg px-default-sm">
                    {" "}
                    {tier.tier} x {tier.count}{" "}
                  </p>
                  <p className="md:text-lg px-default-sm"> ${tier.price} </p>
                </div>
              );
            })}
          </div>
        </div>
        {/* END OF EVENT INFO DIV */}
      </div>
      {/* END OF CARD DIV */}

      {/* TOTAL DIV */}
      <div className="flex justify-between items-center">
        <span className="flex-1 mx-default-sm border w-full h-fit border-secondary" />
        <p className="mr-default-sm md:text-2xl text-lg"> Total: ${Math.round((total + Number.EPSILON) * 100) / 100} </p>
      </div>
      {/* END OF TOTAL DIV */}

    </section>
  );
}