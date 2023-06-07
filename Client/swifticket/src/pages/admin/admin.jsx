import React from 'react';
import SideBar from './components/SideBar';
import Charts from './Charts';
import Tables from './Tables';

const Admin = () => {
  return (
    <div className="flex h-screen w-screen">
      <SideBar />
      {/* <Charts /> */}
      <Tables />
    </div>
  );
};

export default Admin;
