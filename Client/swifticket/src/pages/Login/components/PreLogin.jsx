import React, { useEffect, useRef, useState } from 'react';
import googleLogo from '../../../assets/googleLogo.svg';
import GoogleLogin from './GoogleLogin';
import { googleSignIn } from '../../../services/Auth.Services';
import { useSetRecoilState } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';
import { useNavigate } from 'react-router-dom';

const PreLogin = ({ setIsLoginViews }) => {
  const loginRef = useRef(null);
  const [buttonWidth, setButtonWidth] = useState(0);
  const setToken = useSetRecoilState(tokenState);
  const [isAdmin, setIsAdmin] = useState(false);
  const navigateTo = useNavigate();

  const onGoogleSignIn = async (res) => {
    const { credential } = res;
    localStorage.setItem('auth_token', JSON.stringify(credential));
    setToken(googleSignIn(credential));
    isAdmin ? navigateTo('/admin') : navigateTo('/');
  };

  console.log(buttonWidth);
  return (
    <div className="container-login flex flex-col justify-center items-center w-full h-full">
      <div className="flex flex-row justify-center gap-2 items-center gap-y-6 sm:gap-y-0 h-full sm:h-2/5 w-full">
        <button
          ref={loginRef}
          className="btn-login h-10 px-4 flex justify-center items-center w-3/5"
          onClick={() => setIsLoginViews([false, true, false, false])}
        >
          Login
        </button>
        <GoogleLogin onGoogleSignIn={onGoogleSignIn} />

        {/* <button className="btn-google h-10 flex justify-between items-center w-4/5">
          <img src={googleLogo} alt="google" className="img-google" />
          Continue with Google
          <div className=""></div>
        </button> */}
      </div>
      <button className="btn-guest">Continue as guest</button>
    </div>
  );
};

export default PreLogin;

{
  /* <div className="container-login flex justify-center items-center w-full  h-full">
  <div className="container-login-btn flex flex-col justify-evenly items-center h-2/5 w-full">
    <button className="btn-login h-10 px-4 flex justify-center items-center w-4/5">
      Login
    </button>
    <button className="btn-google h-10 px-4 flex justify-center items-center w-4/5">
      <img src={googleLogo} alt="google" className="img-google" />
      Continue with Google
    </button>
    <button className="btn-guest">Continue as guest</button>
  </div>
</div> */
}
