import React from 'react';

const ChangePass = () => {
  return (
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
  );
};

export default ChangePass;
