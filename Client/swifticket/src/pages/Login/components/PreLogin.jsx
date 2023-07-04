import React, { useRef, useState } from 'react';
import GoogleLogin from './GoogleLogin';
import { googleSignIn } from '../../../services/Auth.Services';
import { useRecoilState, useSetRecoilState } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-hot-toast';
import { guestState } from '../../../state/atoms/guestState';
import { roleState } from '../../../state/atoms/roleState';

const PreLogin = ({ setIsLoginViews }) => {

  const loginRef = useRef(null);
  const navigateTo = useNavigate();
  const [isGuest, setIsGuest] = useRecoilState(guestState);
  const [buttonWidth, setButtonWidth] = useState(0);
  const setToken = useSetRecoilState(tokenState);
  const setRoles = useSetRecoilState(roleState);

  const handleGuest = () => {
    setIsGuest(true);
    sessionStorage.setItem('guest', true);
    navigateTo('/');
  }

  const onGoogleSignIn = async (res) => {
    const { credential } = res;
    const singInResponse = await googleSignIn(credential);
    if (singInResponse == undefined) {
      toast.error("Sorry, try again later...");
      return;
    }

    // console.log(singInResponse.data.token);

    let roles = singInResponse.data.roles;
    localStorage.setItem('roles', JSON.stringify(roles));
    setRoles(roles);

    localStorage.setItem('auth_token', JSON.stringify(singInResponse.data.token));
    setToken(singInResponse.data.token);
  }

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
      <button onClick={handleGuest} className="btn-guest sm:h-0 h-1/2">Continue as guest</button>
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
