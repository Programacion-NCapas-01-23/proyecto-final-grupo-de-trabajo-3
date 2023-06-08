import React from 'react';
import SideBar from './components/SideBar';
import Charts from './Charts';
import Tables from './Tables';
import Catalogs from './Catalogs';

const Admin = () => {
  return (
    <div className="flex h-screen w-screen">
      <SideBar />
      {/* <Charts /> */}
      {/* <Tables /> */}
      <Catalogs />
    </div>
  );
};

export default Admin;
