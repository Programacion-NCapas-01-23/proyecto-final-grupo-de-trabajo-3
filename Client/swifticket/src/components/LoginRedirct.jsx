import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { roleState } from '../state/atoms/roleState';

export default function LoginRedirct() {
  const roles = useRecoilValue(roleState);
  const navigate = useNavigate();

  useEffect(() => {
    let isAdmin = false;
    for (let i = 0; i < roles?.length; i++) {
      if (roles[i].id == 1 || roles[i].id == 5) {
        isAdmin = true;
        break;
      }
    }

    console.log('User roles: ', roles);
    console.log('Redirecting to admin: ', isAdmin);
    isAdmin ? navigate('/admin') : navigate('/');
  }, []);

  return <></>;
}
