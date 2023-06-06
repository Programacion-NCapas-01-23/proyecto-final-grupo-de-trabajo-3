
export default function EventCardCh(props) {
    let total = 0;
  
    props.event.tiers.forEach((element) => {
      if (element.count !== 0) total += element.price * element.count;
    });
  
    return (
      <section className="flex flex-col">
        <div className="flex mx-default-xs max-w-[32rem] m-default-xs shadow-md shadow-black rounded-2xl">
          <div className="relative h-48 md:w-[16rem] w-40">
            <img
              className="object-cover h-48 md:w-[16rem] w-40 shadow-md rounded-l-2xl"
              src={props.event.img}
              alt="event_img"
            />
            <span className="absolute bottom-0 bg-secondary text-center px-3 py-2 rounded-bl-2xl shadow-md shadow-black">
              <p className="text-4xl">{props.event.date_time.getDate()}</p>
              <p className="uppercase -mt-2">
                {props.event.date_time.toLocaleString("en-US", {
                  month: "short",
                })}
              </p>
            </span>
          </div>
          <div className="relative  md:w-9/12">
            <div className="w-full h-full pl-default pr-default-lg relative py-default overflow-auto rounded-r-2xl bg-secondary bg-opacity-30">
              <h1 className="subtitle mb-default-xs">{props.event.title}</h1>
              <div>
                {props.event.tiers.map((tier, index) => {
                  if (tier.count === 0) return null;
                  return (
                    <div key={index} className="flex justify-between">
                      <p className="md:text-lg px-default-sm">
                        {tier.tier} x {tier.count}
                      </p>
                      <p className="md:text-lg px-default-sm">
                        ${tier.price}
                      </p>
                    </div>
                  );
                })}
              </div>
            </div>
          </div>
        </div>
        <div className="flex justify-between items-center">
          <span className="flex-1 mx-default-sm border w-full h-fit border-secondary" />
          <p className="mr-default-sm md:text-2xl text-lg">
            Total: ${Math.round((total + Number.EPSILON) * 100) / 100}
          </p>
        </div>
      </section>
    );
  }
  