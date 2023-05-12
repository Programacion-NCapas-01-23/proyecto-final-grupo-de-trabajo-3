import React from 'react';
import googleLogo from '../../../assets/googleLogo.svg';

const PreLogin = () => {
  return (
    <div className="container-login flex justify-center items-center w-full  h-full">
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
