import React, { useState } from 'react';
import { signIn } from '../../../services/Auth.Services';
import { useSetRecoilState } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';
import { roleState } from '../../../state/atoms/roleState';

const MainLogin = ({ setIsLoginViews }) => {
  const [userName, setUserName] = useState('');
  const [pass, setPass] = useState('');

  const setToken = useSetRecoilState(tokenState);
  const setRoles = useSetRecoilState(roleState);

  const loginHandler = async (userName, pass) => {
    const response = await signIn(userName, pass);

    if (response.status === 200) {
      let roles = response.data.roles;

      localStorage.setItem('roles', JSON.stringify(roles));
      setRoles(roles);
      localStorage.setItem('auth_token', JSON.stringify(response.data.token));
      setToken(response.data.token);
    }
  };

  return (
    <div className="container-login flex flex-col justify-between items-center w-full h-full">
      <div className="flex flex-col justify-center sm:justify-evenly items-center gap-y-6 sm:gap-y-0 h-full sm:h-3/5 w-full">
        <div className="flex flex-col justify-evenly items-center w-full h-1/2">
          <input
            type="text"
            className="input-username h-10 px-4 flex justify-center items-center w-4/5"
            placeholder="Username"
            value={userName}
            onChange={(e) => {
              setUserName(e.target.value);
            }}
          />
          <input
            type="password"
            className="input-password h-10 px-4 flex justify-center items-center w-4/5"
            placeholder="Password"
            value={pass}
            onChange={(e) => {
              setPass(e.target.value);
            }}
          />
        </div>

        <button
          className="btn-login h-10 px-4 flex justify-center items-center w-4/5"
          onClick={() => loginHandler(userName, pass)}
        >
          Login
        </button>
      </div>
      <button
        className="btn-forgot my-2"
        onClick={() => setIsLoginViews([false, false, true, false])}
      >
        Forgot your password?
      </button>
    </div>
  );
};

export default MainLogin;
