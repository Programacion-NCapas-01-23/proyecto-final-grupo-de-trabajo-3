import React from 'react';
import { MdArrowForwardIos } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';

const UserProfile = () => {

  const navigate = useNavigate()

  return (
    <div className="flex flex-col w-full sm:w-3/5 h-[55%] justify-start items-center gap-4">
      <button onClick={() => {navigate("/user/my-account")}} className="flex justify-between items-center heading-xl w-full">
        Account info
        <MdArrowForwardIos />
      </button>
      <button onClick={() => {navigate("/user/change-avatar")}} className="flex justify-between items-center heading-xl w-full">
        Avatars
        <MdArrowForwardIos />
      </button>
      <button onClick={() => {navigate("/user/change-password")}} className="flex justify-between items-center heading-xl w-full">
        Password
        <MdArrowForwardIos />
      </button>
      <button onClick={() => {navigate("/user/owned-tickets")}} className="flex justify-between items-center heading-xl w-full">
        My Tickets
        <MdArrowForwardIos />
      </button>
    </div>
  );
};

export default UserProfile;
