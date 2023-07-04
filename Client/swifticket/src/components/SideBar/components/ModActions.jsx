import { MdGroups3 } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';

export default function ModActions() {
  const navigate = useNavigate();

  const redirectToManageUsers = () => {
    navigate('/moderator');
  };

  return (
    <li className="py-default text-xl">
      <button
        className="flex items-center md:my-default-xs"
        onClick={redirectToManageUsers}
      >
        <span className="mr-default-xs">
          {' '}
          <MdGroups3 size={'2rem'} />{' '}
        </span>
        Manage Users
      </button>
    </li>
  );
}
