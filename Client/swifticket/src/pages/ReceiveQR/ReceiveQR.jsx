import React, { useState } from 'react'
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../state/atoms/tokenState';
import { startTransferTicket } from '../../services/TicketServices';
import { Toaster, toast } from 'react-hot-toast';
import QRCode from 'react-qr-code';
import { MdQrCode } from 'react-icons/md';

export default function ReceiveQR() {
  const [code, setCode] = useState("");
  const token = useRecoilValue(tokenState);

  const receiveTicket = async () => {
    let response = await startTransferTicket(token);
    if (response.status == 200)
      setCode(response.data.code)
    else
      toast.error("An error occurred, please try again later");
  }

  return (
    <div className='min-h-[calc(100vh-52px-3.5rem)]'>
      <div><Toaster /></div>
      <TitileWithLines title={"Receive ticket"} />

      <main className='flex flex-col justify-center items-center h-[72vh]'>
        <DisplayQR code={code} />
        <Description />

        <div className='w-full flex content-center justify-center h-auto mt-[4vh]'>
          <button className="border-[#144580] border-2 px-8 py-2 rounded-3xl heading-md mx-[1vw]" onClick={receiveTicket}>
            Generate code
          </button>
        </div>
      </main>
    </div>
  )
}

function TitileWithLines({ title }) {
  return (
    <span className="w-full grid grid-cols-10 px-default-lg pb-default-lg items-center">
      <div className='border h-0 border-primary'></div>
      <h1 className='md:title subtitle text-center md:col-span-3 col-span-7'>{title}</h1>
      <div className='border h-0 border-primary md:col-span-6 col-span-2'></div>
    </span>
  )
}

function DisplayQR({code}) {
  return (
    <div>
      {/* <img className='v-[35vw] h-[35vh] object-contain' src={QRPlaceholder}></img> */}
      { (code == null || code == "") ?
        <MdQrCode style={{ fontSize: '20vh' }} />
        :
        <div className='bg-white p-[2vh] h-auto w-auto'>
          <QRCode
          title="code"
          value={code}
          bgColor="#FFFFFF"
          fgcolor="#000000"
          size={220}
          />
        </div>}
    </div>
  )
}

function Description() {
  return (
    <div className='w-[90vw] sm:w-[20vw] mt-[4vh]'>
      <p className='heading-md'>Tell the owner of the ticket to scan this QR to begin the transaction</p>
    </div>
  )
}