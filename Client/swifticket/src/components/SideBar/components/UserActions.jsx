import { MdConfirmationNumber, MdCallReceived } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';

export default function UserActions() {
  const navigate = useNavigate();

  const redirectToOwnedTickets = () => {
    navigate('/user/owned-tickets');
  };

  const redirectToReceiveQR = () => {
    navigate('/receive-qr');
  };

  return (
    <li className="py-default text-xl cursor-pointer">
      <a
        className="flex items-center md:my-default-xs my-2 cursor-pointer"
        onClick={redirectToOwnedTickets}
      >
        <span className="mr-default-xs">
          {' '}
          <MdConfirmationNumber size={'2rem'} />{' '}
        </span>
        My Tickets
      </a>
      <a
        className="flex items-center md:my-default-xs my-2 cursor-pointer"
        onClick={redirectToReceiveQR}
      >
        <span className="mr-default-xs">
          {' '}
          <MdCallReceived size={'2rem'} />{' '}
        </span>
        Receive a QR
      </a>
    </li>
  );
}
