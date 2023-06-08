import React from 'react';
import SideBar from './components/SideBar';

import { Outlet } from 'react-router-dom';

const Admin = () => {
  return (
    <div className="flex h-screen w-screen">
      <SideBar />
      <Outlet />
    </div>
  );
};

export default Admin;
