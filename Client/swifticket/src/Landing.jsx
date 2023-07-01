import React from 'react';
import errorImage from './assets/errorImage.jpg';
import { useNavigate } from 'react-router-dom';

const Landing = () => {
  const navigate = useNavigate()
  return (
    <div className="w-screen min-h-[calc(100vh-52px-3.5rem)] flex flex-col justify-center items-center">
      <img src={errorImage} alt="Error" />
      <button onClick={() => {navigate("/")}} className='action-button mt-default'> Home </button>
    </div>
  );
};

export default Landing;
