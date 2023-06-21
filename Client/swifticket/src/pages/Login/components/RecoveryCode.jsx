import React from 'react';

const RecoveryCode = ({ setIsLoginViews }) => {
  return (
    <div className="container-login flex flex-col justify-around items-center w-full h-full">
      <div className="flex flex-col justify-start sm:justify-evenly items-center gap-y-6 sm:gap-y-0 h-full sm:h-2/5 w-full">
        <p className="text-lg text-white w-4/5">
          Enter the code that was sent to{' '}
          <span style={{ color: '#e6a410' }}>******20@uca.edu.sv</span>
        </p>
        <input
          type="text"
          className="input-recovery-email h-10 px-4 flex justify-center items-center w-4/5"
          placeholder="Code"
        />
      </div>
      <div className="flex flex-col justify-evenly items-center h-1/3 w-full">
        <button
          className="btn-recovery h-10 px-4 flex justify-center items-center w-4/5"
          onClick={() => setIsLoginViews([true, false, false, false])}
        >
          Verify
        </button>
        <button className="btn-cancel my-2">Resend code</button>
      </div>
    </div>
  );
};

export default RecoveryCode;
