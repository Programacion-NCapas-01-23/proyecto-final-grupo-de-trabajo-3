import React from 'react';

const Widget = ({ label, value, isNumber }) => {
  return (
    <>
      {!isNumber ? (
        <div className="flex flex-col justify-center items-center my-2 sm:my-0 h-[16vh] sm:h-5/6 min-w-[40%] sm:min-w-[12%] max-w-[50%] sm:max-w-[25%] bg-[#212549] rounded-md">
          <p>{label}</p>
          <p className="subtitle">
            {typeof value == 'number' ? value.toFixed(1) : value}
          </p>
        </div>
      ) : (
        <div className="flex flex-col justify-center items-center my-2 sm:my-0 h-[16vh] sm:h-5/6 min-w-[40%] sm:min-w-[18%] max-w-[50%] sm:max-w-[25%] bg-[#212549] rounded-md">
          <p>{label}</p>
          <div className="flex justify-evenly w-full">
            <div className="flex flex-col">
              <p className="subtitle">
                {typeof value[0] == 'number' ? value[0].toFixed(1) : value[0]}%
              </p>
              <p>Groups</p>
            </div>
            <div className="flex flex-col">
              <p className="subtitle">/</p>
              <p></p>
            </div>
            <div className="flex flex-col">
              <p className="subtitle">
                {typeof value[0] == 'number' ? value[1].toFixed(1) : value[1]}%
              </p>
              <p>Individuals</p>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Widget;
