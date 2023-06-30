import React, { useEffect } from 'react';
import { MdWarning } from 'react-icons/md';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';
import { suspendService } from '../../../services/System.Services';

export const ToggleServices = ({ isActive, setIsActive }) => {
  const className = `flex justify-between items-center px-4 rounded-3xl sm:rounded-r-none sm:rounded-l-3xl w-44 h-9 ${
    isActive ? 'bg-red-500' : 'bg-primary'
  }`;
  const token = useRecoilValue(tokenState);

  const handleToggleServices = async () => {
    const response = await suspendService(token);

    if (response.status === 200) {
      setIsActive(!isActive);
    }
  };

  return (
    <button className={className} onClick={() => handleToggleServices()}>
      <MdWarning />
      {isActive ? <p>Stop Services</p> : <p>Start Services</p>}
      <div className=""></div>
    </button>
  );
};
