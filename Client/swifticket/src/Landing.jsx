import React from 'react';
import errorImage from './assets/errorImage.jpg';

const Landing = () => {
  return (
    <div className="w-screen min-h-[calc(100vh-52px-3.5rem)] flex justify-center items-center">
      <img src={errorImage} alt="Error" />
    </div>
  );
};

export default Landing;
