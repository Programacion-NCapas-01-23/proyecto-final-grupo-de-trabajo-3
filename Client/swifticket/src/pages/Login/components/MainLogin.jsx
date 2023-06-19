import React, { useState } from 'react';
import { login } from '../../../services/User.Services';
import { useRecoilState } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';

const MainLogin = () => {
  const [userName, setUserName] = useState('');
  const [pass, setPass] = useState('');

  const [token, setToken] = useRecoilState(tokenState);

  const loginHandler = async (userName, pass) => {
    const response = await login(userName, pass);

    localStorage.setItem('auth_token', JSON.stringify(response.data.token));
    setToken(response.data.token);
    console.log(response);
  };

  return (
    <div className="container-login flex flex-col justify-between items-center w-full h-full">
      <div></div>
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
      <button className="btn-forgot my-2">Forgot your password?</button>
    </div>
  );
};

export default MainLogin;
