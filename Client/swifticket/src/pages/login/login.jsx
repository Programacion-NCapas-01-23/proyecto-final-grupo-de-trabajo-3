import React from 'react';
import './Login.css';
import swifticketLogo from '../../assets/swifticketLogo.png';
import loginFooter from '../../assets/loginFooter.svg';
import PreLogin from './components/PreLogin';
import MainLogin from './components/MainLogin';
import ForgotPass from './components/ForgotPass';
import RecoveryCode from './components/RecoveryCode';

const Login = () => {
  return (
    <>
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
            <ForgotPass />
            {/* <RecoveryCode /> */}
          </div>
        </div>
      </div>
      <img src={loginFooter} alt="Footer" className="footer-login" />
    </>
  );
};

export default Login;
