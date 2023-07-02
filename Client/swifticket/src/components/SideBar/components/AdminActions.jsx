import { MdAdminPanelSettings } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';

export default function AdminActions() {
  const navigate = useNavigate();

  const redirectUser = (path) => {
    navigate(`/${path}`);
  };

  return (
    <li className="py-default text-xl">
      <button
        className="flex items-center md:my-default-xs"
        onClick={() => {
          redirectUser('admin');
        }}
      >
        <span className="mr-default-xs">
          {' '}
          <MdAdminPanelSettings size={'2rem'} />{' '}
        </span>
        Go Admin
      </button>
    </li>
  );
}
