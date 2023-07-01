import React  from 'react';
import { Toaster, toast } from 'react-hot-toast';
import { MdQrCodeScanner } from 'react-icons/md';
import { QrReader } from 'react-qr-reader';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../state/atoms/tokenState';
import { acceptTransferTicket } from '../../services/TicketServices';
import { useState } from 'react';
import { useParams } from 'react-router';

const SendTicket = () => {
  const { ticketId } = useParams();
  const token = useRecoilValue(tokenState);
  const [isCode, setIsCode] = useState(true);

  function validateUUID(uuid) {
    const regex = /^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$/;
    return regex.test(uuid);
  }
  
  const readTicketTransaction =  async (code) => {
    if (!validateUUID(ticketId)) {
      toast.error("No ticket was selected");
      return;
    }
    if (!validateUUID(code)) {
      toast.error("Scan the code again");
      return;
    }

    console.log("Ticket to send: ", ticketId);
    console.log("transferId: ", code);
    let response = await acceptTransferTicket(token, ticketId, code);
    
    // console.log(response);

    let error_message = "An error occurred, please try again later";
    if (response.status == 200)
      toast.success(response.data.message, { duration: 4000 });
    else if (response.status == 401 || response.status == 404 || response.status == 409)
      toast.error(response?.data?.message ?? error_message)
    else
      toast.error(error_message);
  }

  const handleResult = (result, error) => {
    if (!!result) {
      setIsCode(true);
      readTicketTransaction(result?.text)
    }
  }

  const readyToScan = () => setIsCode(false);
  const cancelScan = () => setIsCode(true);

  return (
    <div className="flex flex-col justify-center items-center gap-8 w-full min-h-[calc(100vh-52px-3.5rem)] px-8">
      <div><Toaster /></div>
      <span className="w-full grid grid-cols-10 px-default-lg pb-default-lg items-center">
        <div className="border h-0 border-primary"></div>
        <h1 className="md:title subtitle text-center md:col-span-3 col-span-7">
          Send ticket
        </h1>
        <div className="border h-0 border-primary md:col-span-6 col-span-2"></div>
      </span>
      <div className="flex justify-center items-center w-[40vh] h-[40vh] bg-[#424242] bg-opacity-50 rounded-3xl">
        {/* Scanner */}
        <div className='w-full h-auto'>
          { isCode ? 
          <div className='w-full h-full flex content-center justify-center'>
            <MdQrCodeScanner style={{ fontSize: '20vh' }} /> 
          </div>
          :
          <QrReader
            onResult={handleResult}
            style={{ width: '100%' }}
            />}
        </div>
      </div>
      <p className="heading-md">Scan a QR to begin the transaction.</p>
      <ScannerButtons cancelScan={cancelScan} readyToScan={readyToScan} />
    </div>
  );
};

const ScannerButtons = ({cancelScan, readyToScan}) => {
  return (
    <div className='w-full flex content-center justify-center h-auto'>
      <button className="border-[#144580] border-2 px-8 py-2 rounded-3xl heading-md mx-[1vw]" onClick={cancelScan}>
        Quit
      </button>
      <button className="border-[#144580] border-2 px-8 py-2 rounded-3xl heading-md mx-[1vw]" onClick={readyToScan}>
        Scan
      </button>
    </div>
  );
}

export default SendTicket;
