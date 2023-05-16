import React from 'react';
import loginFooter from '../../assets/loginFooter.svg';
import userPlaceholder from '../../assets/userPlaceholder.png';

const ChangePass = () => {
  return (
    <>
      <div className="flex justify-center items-center w-screen h-screen">
        <div className="flex flex-col justify-evenly items-center h-full sm:h-3/4 w-11/12 md:w-2/5 bg-transparent sm:bg-secondary sm:bg-opacity-50 rounded-[2rem]">
          <div className="flex flex-col justify-center items-center h-2/6 sm:h-[45%]">
            <img src={userPlaceholder} alt="User" style={{ height: '20vh' }} />
            <p className="subtitle">Chori Zon</p>
          </div>
          <div className="flex flex-col w-full sm:w-3/5 h-[55%] justify-start">
            <p className="subtitle">Change password</p>
            <div className="flex flex-col gap-4">
              <div>
                <p className="heading-md">New Password</p>
                <input
                  type="password"
                  placeholder="Password"
                  className="input-password h-10 px-4 flex justify-center items-center w-full"
                />
              </div>
              <div>
                <p className="heading-md">Repeat Password</p>
                <input
                  type="password"
                  placeholder="Password"
                  className="input-password h-10 px-4 flex justify-center items-center w-full"
                />
              </div>
              <div className="flex justify-center items-center wfull">
                <button className="btn-login h-10 px-4 flex justify-center items-center w-3/5">
                  Confirm
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <img src={loginFooter} alt="Footer" className="footer-login" />
    </>
  );
};

export default ChangePass;
