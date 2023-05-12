import React from 'react';
import './Login.css';
import swifticketLogo from '../../assets/swifticketLogo.png';
import PreLogin from './preLogin/PreLogin';
import MainLogin from './mainLogin/MainLogin';

const Login = () => {
  return (
    <div className="bg-login w-screen h-screen flex justify-center items-center">
      {/* Main container */}
      <div className="flex justify-center items-center w-3/5 h-3/5">
        {/* Left container */}
        <div className="container-logo flex justify-center items-center w-1/2 h-full">
          <img src={swifticketLogo} alt="logo" className="img-logo" />
        </div>
        {/* Right container */}
        <div className="flex flex-col justify-center items-center w-1/2 h-full">
          {/* <PreLogin /> */}
          {/* <MainLogin /> */}
        </div>
      </div>
    </div>
  );
};

export default Login;
