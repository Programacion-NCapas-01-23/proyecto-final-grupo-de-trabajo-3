import React from 'react';

const ForgotPass = () => {
  return (
    <div className="container-login flex flex-col justify-around items-center w-full h-full">
      <div className="flex flex-col justify-evenly items-center h-2/5 w-full">
        <p className="text-lg text-white w-4/5">
          Lost your password?
          <span style={{ color: '#9c9b9c' }} className="text-base">
            {' '}
            Enter your email to receive a code and reset your password
          </span>
        </p>
        <input
          type="email"
          className="input-recovery-email h-10 px-4 flex justify-center items-center w-4/5"
          placeholder="Email"
        />
      </div>
      <div className="flex flex-col justify-evenly items-center h-1/3 w-full">
        <button className="btn-recovery h-10 px-4 flex justify-center items-center w-4/5">
          Send code
        </button>
        <button className="btn-cancel my-2">Cancel</button>
      </div>
    </div>
  );
};

export default ForgotPass;
