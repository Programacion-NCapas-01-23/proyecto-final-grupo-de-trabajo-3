import { Fragment, useEffect, useState } from 'react';
import { Dialog, Transition } from '@headlessui/react';
import { useNavigate } from 'react-router-dom';
import * as Roles from './components/Roles';
import AdminActions from './components/AdminActions';
import UserActions from './components/UserActions';
import ModActions from './components/ModActions';
import CollabActions from './components/CollabActions';
import { MdAccountCircle, MdClose, MdLogout, MdPerson } from 'react-icons/md';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { tokenState } from '../../state/atoms/tokenState';
import { validateToken } from '../../services/Auth.Services';
import { guestState } from '../../state/atoms/guestState';

export default function SideBar(props) {
  const navigate = useNavigate();
  const setToken = useSetRecoilState(tokenState);
  const [user, setUser] = useState(Roles.nullUser);
  const [userRoles, setUserRoles] = useState([{}]);
  const token = useRecoilValue(tokenState);
  const [isGuest, setIsGuest] = useRecoilState(guestState);

  const handleLogOut = () => {
    setToken(null);
    localStorage.removeItem('auth_token');
    localStorage.removeItem('roles');
  };

  useEffect(() => {
    const fetchUser = async (token) => {
      const response = await validateToken(token);
      if (response != undefined) {
        setUser(response.data);
        setUserRoles(response.data.roles);
      } else setIsGuest(true);
    };
    fetchUser(token);
  }, []);

  const redirectUser = () => {
    if (isGuest) {
      setIsGuest(false);
      navigate('/login');
    } else {
      navigate('user');
    }
  };

  return (
    <Transition show={props.open} as={Fragment}>
      <Dialog as="div" className="relative z-10" onClose={props.setOpen}>
        {/* ----------- BACKGROUND BLUR --------------- */}
        <Transition.Child
          as={Fragment}
          enter="ease-in-out duration-500"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="ease-in-out duration-500"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <div className="fixed inset-0 bg-black bg-opacity-50 transition-all" />
        </Transition.Child>
        {/* ----------- BACKGROUND BLUR --------------- */}

        {/* ----------- SIDE BAR COMPONENT --------------- */}
        <div className="fixed inset-0 overflow-hidden">
          <div className="absolute inset-0 overflow-hidden">
            <div className="pointer-events-none fixed inset-y-0 left-0 flex max-w-full">
              <Transition.Child
                as={Fragment}
                enter="transform transition ease-in-out duration-500 sm:duration-700"
                enterFrom="-translate-x-full"
                enterTo="translate-x-0"
                leave="transform transition ease-in-out duration-500 sm:duration-700"
                leaveFrom="translate-x-0"
                leaveTo="-translate-x-full"
              >
                <Dialog.Panel className="pointer-events-auto w-screen max-w-md mr-default">
                  <div className="flex h-full flex-col overflow-y-auto bg-black shadow-xl">
                    <div className="overflow-y-auto px-4 py-4 sm:px-6">
                      <Dialog.Title>
                        <p className="subtitle">
                          Swifticket
                          <button
                            onClick={() => props.setOpen(false)}
                            className="absolute right-0 px-default"
                          >
                            <MdClose />
                          </button>
                        </p>

                        <div className="flex flex-col items-center md:my-default-sm my-default-xs">
                          <MdAccountCircle size={'12rem'} />
                          <button className="heading-lg">
                            {' '}
                            {isGuest ? 'Guest' : user.name}
                          </button>
                        </div>
                      </Dialog.Title>
                    </div>

                    <div className="px-default-lg flex-1 overflow-y-auto">
                      <ul className="divide-y-2 divide-gray-200">
                        <li className="py-default text-xl">
                          <button
                            className="flex items-center"
                            onClick={redirectUser}
                          >
                            <span className="mr-default-xs">
                              {' '}
                              <MdPerson size={'2rem'} />{' '}
                            </span>
                            {isGuest ? 'Log In' : 'My Profile'}
                          </button>
                        </li>

                        {/* Renderización condicional de los componentes */}
                        {userRoles.some((role) => role.id === 2) && (
                          <UserActions />
                        )}
                        {userRoles.some((role) => role.id === 4) && (
                          <CollabActions />
                        )}
                        {userRoles.some((role) => role.id === 3) && (
                          <ModActions />
                        )}
                        {userRoles.some((role) => role.id == 1) && (
                          <AdminActions />
                        )}
                        {userRoles.some((role) => role.id === 5) && (
                          <>
                            <UserActions />
                            <CollabActions />
                            <ModActions />
                            <AdminActions />
                          </>
                        )}
                      </ul>
                    </div>

                    {!isGuest && (
                      <div className="m-auto px-4 py-6 sm:px-6">
                        <button className="flex items-center">
                          <MdLogout size={'2rem'} />
                          <a
                            href="/login"
                            className="heading-sm px-default"
                            onClick={() => handleLogOut()}
                          >
                            {' '}
                            Log Out{' '}
                          </a>
                        </button>
                      </div>
                    )}
                  </div>
                </Dialog.Panel>
              </Transition.Child>
            </div>
          </div>
        </div>
        {/* ----------- SIDE BAR COMPONENT --------------- */}
      </Dialog>
    </Transition>
  );
}
