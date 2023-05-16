import React from 'react';
import loginFooter from '../../assets/loginFooter.svg';
import userPlaceholder from '../../assets/userPlaceholder.png';
import ChangePass from './components/ChangePass';
import UserProfile from './components/UserProfile';

const User = () => {
  return (
    <>
      <div className="flex justify-center items-center w-screen h-screen">
        <div className="flex flex-col justify-evenly items-center h-full sm:h-3/4 w-11/12 md:w-2/5 bg-transparent sm:bg-secondary sm:bg-opacity-50 rounded-[2rem]">
          <div className="flex flex-col justify-center items-center h-2/6 sm:h-[45%]">
            <img src={userPlaceholder} alt="User" style={{ height: '20vh' }} />
            <p className="subtitle">Chori Zon</p>
          </div>
          {/* <ChangePass /> */}
          <UserProfile />
        </div>
      </div>
      <img src={loginFooter} alt="Footer" className="footer-login" />
    </>
  );
};

export default User;
