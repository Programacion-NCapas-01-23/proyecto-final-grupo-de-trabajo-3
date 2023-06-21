import React, { useState } from 'react';
import { login } from '../../../services/Auth.Services';
import { useSetRecoilState } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';
import { useNavigate } from 'react-router-dom';
import LoginAlert from '../../../components/Alerts/LoginAlert';

const MainLogin = ({ setIsLoginViews }) => {
  const [userName, setUserName] = useState('');
  let [hasErrors, setHasErrors] = useState(false);
  const [pass, setPass] = useState('');
  const navigateTo = useNavigate();

  const [isAdmin, setIsAdmin] = useState(false);

  const setToken = useSetRecoilState(tokenState);

  const loginHandler = async (userName, pass) => {
    const response = await login(userName, pass);

    if (response === undefined || response.status !== 200) {
      setHasErrors(true);
    }

    if (response.status === 200) {
      localStorage.setItem('auth_token', JSON.stringify(response.data.token));
      setToken(response.data.token);
      isAdmin ? navigateTo('/admin') : navigateTo('/');
    }

    console.log(response);
  };

  return (
    <div className="container-login flex flex-col justify-between items-center w-full h-full">
      <LoginAlert hasErrors={hasErrors} setHasErrors={setHasErrors} />
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
