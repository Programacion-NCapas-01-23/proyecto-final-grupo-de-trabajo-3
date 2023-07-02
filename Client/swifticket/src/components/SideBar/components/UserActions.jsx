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
    <li className="py-default text-xl">
      <button
        className="flex items-center md:my-default-xs"
        onClick={redirectToOwnedTickets}
      >
        <span className="mr-default-xs">
          {' '}
          <MdConfirmationNumber size={'2rem'} />{' '}
        </span>
        My Tickets
      </button>
      <button
        className="flex items-center md:my-default-xs"
        onClick={redirectToReceiveQR}
      >
        <span className="mr-default-xs">
          {' '}
          <MdCallReceived size={'2rem'} />{' '}
        </span>
        Receive a QR
      </button>
    </li>
  );
}
