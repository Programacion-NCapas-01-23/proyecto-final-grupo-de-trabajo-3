import React from 'react';

const MainLogin = () => {
  return (
    <div className="container-login flex flex-col justify-between items-center w-full h-full">
      <div></div>
      <div className="flex flex-col justify-evenly items-center h-3/5 w-full">
        <div className="flex flex-col justify-evenly items-center w-full h-1/2">
          <input
            type="text"
            className="input-username h-10 px-4 flex justify-center items-center w-4/5"
            placeholder="Username"
          />
          <input
            type="text"
            className="input-password h-10 px-4 flex justify-center items-center w-4/5"
            placeholder="Password"
          />
        </div>

        <button className="btn-login h-10 px-4 flex justify-center items-center w-4/5">
          Login
        </button>
      </div>
      <button className="btn-forgot my-2">Forgot your password?</button>
    </div>
  );
};

export default MainLogin;
