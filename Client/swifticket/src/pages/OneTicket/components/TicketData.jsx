import Placeholder from '../../../assets/Placeholder.png';
import QRPlaceholder from '../../../assets/QRPlaceholder.png';
import LoadingQRPlaceholder from '../../../assets/LoadingQRPlaceholder.png';
import QRCode from 'react-qr-code';
import { useState } from 'react';
import { generateTicketCode } from '../../../services/TicketServices';
import { Toaster, toast } from 'react-hot-toast';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';
import { useNavigate } from 'react-router';

export default function TicketData({ isLoading, ticket }) {
  const navigate = useNavigate();
  const [code, setCode] = useState("");
  const token = useRecoilValue(tokenState);
  let date_time = new Date(ticket?.createdAt);

  const sendTicket = () => {
    if (ticket?.used) {
      toast.error("ticket already used");
      return;
    }
    navigate(`/send-ticket-qr/${ticket.id}`);
  }

  const requestTicketCode = async () => {
      let error_message = "Ocurrio un error, intentalo nuevamente más tarde";
      if (ticket?.id == null) return;
      let response = await generateTicketCode(token, ticket.id);
      console.log("Solicitando codigo para, " +  ticket.id);
      console.log(response);

      if (response.status == 201)
        setCode(response.data.code);
      else if (response.status == 401 || response.status == 404 || response.status == 409)
        toast.error(response?.data?.message ?? error_message)
      else
        toast.error(error_message);
  }
  
  return (
    <div className="flex flex-col sm:flex-row justify-center items-center w-11/12 sm:w-3/5 h-5/6 sm:h-4/6 bg-white rounded-[2rem] pb-4 sm:pb-0">
      <div><Toaster /></div>
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
        { (code == "") ?
          <div className='text-white -mt-5 sm:m-0  sm:flex-col sm:gap-8 sm:p-default-lg flex w-full justify-evenly pb-default'>
            <button onClick={sendTicket} className='bg-secondary shadow-xl px-4 py-2 rounded-2xl heading-sm mx-[1vw]'>{isLoading ? '...' :'Enviar ticket'}</button>
            <button onClick={requestTicketCode} className='bg-secondary shadow-xl px-4 py-2 rounded-2xl heading-sm mx-[1vw]'>{isLoading ? '...' :'Generar QR'}</button>
          </div>
          :
        <div className='-mt-28 sm:m-0 p-2 bg-white sm:mb-2 mb-default-xs shadow-xl rounded'>
          <QRCode
            title="ticket"
            value={code}
            bgColor="#FFFFFF"
            fgcolor="#000000"
            size={200}
          />
        </div>
      }
        <p className="text-black heading-sm">
          {/* '484-awd2u233' */}
          {isLoading ? '...' : ticket?.id.split("-")[0]}
        </p>
      </div>
      <div className="flex flex-col justify-evenly items-start w-full pb-4 sm:w-1/3 h-2/5 sm:h-full ml-16 sm:ml-0">
        <div className="flex flex-col gap-2">
          <p className="text-secondary">Event</p>
          <p className="heading-lg tracking-tighter leading-7 text-black">
            {isLoading ? '...' : ticket.event.title}
          </p>
        </div>
        <div className="flex flex-col gap-2">
          <p className="text-secondary">Tier</p>
          <p className="heading-lg tracking-tighter leading-7 text-black">
            {isLoading ? '...' : ticket.tier.name}
          </p>
        </div>
        <div className="flex flex-col gap-2">
          <p className="text-secondary">Date Purchased</p>
          <p className="heading-lg tracking-tighter leading-7 text-black">
            {isLoading ? '...' : date_time.toDateString()}
          </p>
        </div>
      </div>
    </div>
  );
};
