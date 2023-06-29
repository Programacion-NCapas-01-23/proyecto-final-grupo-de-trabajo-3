import Placeholder from '../../../assets/Placeholder.png';
import QRPlaceholder from '../../../assets/QRPlaceholder.png';
import LoadingQRPlaceholder from '../../../assets/LoadingQRPlaceholder.png';

export default function TicketData({ isLoading, ticket }) {
  let date_time = new Date(ticket?.createdAt);
  
  return (
    <div className="flex flex-col sm:flex-row justify-center items-center w-11/12 sm:w-3/5 h-5/6 sm:h-4/6 bg-white rounded-[2rem] pb-4 sm:pb-0">
      {isLoading ? (
        <span className="w-full sm:w-1/3 h-[30%] sm:h-full bg-neutral-400 sm:rounded-tl-[2rem] sm:rounded-bl-[2rem] sm:rounded-br-none sm:rounded-tr-none rounded-tl-[2rem] rounded-bl-none rounded-br-none rounded-tr-[2rem]" />
      ) : (
        <img
          src={ticket.event.image}
          alt="Ticket image"
          className="w-full sm:w-1/3 h-[30%] sm:h-full object-cover sm:rounded-tl-[2rem] sm:rounded-bl-[2rem] sm:rounded-br-none sm:rounded-tr-none rounded-tl-[2rem] rounded-bl-none rounded-br-none rounded-tr-[2rem]"
        />
      )}
      <div
        className="flex flex-col justify-center items-center w-full sm:w-1/3 h-[30%] sm:h-full"
        style={{ display: 'flex' }}
      >
        {isLoading ? (
          <img
            src={LoadingQRPlaceholder}
            alt="QR"
            className="w-1/2 sm:w-3/4 mt-[-6rem] sm:mt-0"
          />
        ) : (
          <img
            src={QRPlaceholder}
            alt="QR"
            className="border-[0.5rem] border-neutral-400 w-1/2 sm:w-3/4 rounded-[0.5rem] mt-[-6rem] sm:mt-0"
          />
        )}
        <p className="text-black heading-sm">
          {/* '484-awd2u233' */}
          {isLoading ? '...' : ticket?.id.split("-")[0]}
        </p>
      </div>
      <div className="flex flex-col justify-evenly items-start w-full sm:w-1/3 h-2/5 sm:h-full ml-16 sm:ml-0">
        <div className="flex flex-col">
          <p className="text-secondary">Event</p>
          <p className="heading-lg text-black">
            {isLoading ? '...' : ticket.event.title}
          </p>
        </div>
        <div className="flex flex-col">
          <p className="text-secondary">Tier</p>
          <p className="heading-lg text-black">
            {isLoading ? '...' : ticket.tier.name}
          </p>
        </div>
        <div className="flex flex-col">
          <p className="text-secondary">Date Purchased</p>
          <p className="heading-lg text-black">
            {isLoading ? '...' : date_time.toDateString()}
          </p>
        </div>
      </div>
    </div>
  );
};
