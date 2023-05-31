import React from 'react';
import errorImage from './assets/errorImage.jpg';

const Landing = () => {
  return (
    <div className="w-screen h-screen flex justify-center items-center">
      <img src={errorImage} alt="Error" />
    </div>
  );
};

export default Landing;
