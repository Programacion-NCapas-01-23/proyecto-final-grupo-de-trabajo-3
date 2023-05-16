import React from 'react';
import { MdArrowForwardIos } from 'react-icons/md';

const UserProfile = () => {
  return (
    <div className="flex flex-col w-full sm:w-3/5 h-[55%] justify-start items-center gap-4">
      <button className="flex justify-between items-center heading-xl w-full">
        Account info
        <MdArrowForwardIos />
      </button>
      <button className="flex justify-between items-center heading-xl w-full">
        Avatars
        <MdArrowForwardIos />
      </button>
      <button className="flex justify-between items-center heading-xl w-full">
        Password
        <MdArrowForwardIos />
      </button>
    </div>
  );
};

export default UserProfile;
