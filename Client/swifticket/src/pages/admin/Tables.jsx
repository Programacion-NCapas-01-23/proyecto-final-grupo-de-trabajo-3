import React from 'react';
import { MdSearch } from 'react-icons/md';
import TableCell from './components/TableCell';

const Tables = () => {
  const registers = [
    { id: 1, name: 'Miguel Acosta', email: 'email@uca.edu.sv' },
    { id: 2, name: 'Diego Rivas', email: 'email@uca.edu.sv' },
    { id: 3, name: 'Carlos Mercado', email: 'email@uca.edu.sv' },
    { id: 4, name: 'Daniel Solis', email: 'email@uca.edu.sv' },
    { id: 5, name: 'Fernando Roque', email: 'email@uca.edu.sv' },
    { id: 6, name: 'Xiomara Carranza', email: 'email@uca.edu.sv' },
    { id: 7, name: 'Douglas Hernandez', email: 'email@uca.edu.sv' },
    { id: 8, name: 'Ernesto Canales', email: 'email@uca.edu.sv' },
    { id: 9, name: 'Maria Campos', email: 'email@uca.edu.sv' },
    { id: 10, name: 'Elva Ginon', email: 'email@uca.edu.sv' },
  ];

  const isFull = registers.length == 10;

  return (
    <div className="flex flex-col h-screen w-screen sm:w-[80vw]">
      <div className="flex flex-col sm:flex-row h-[30vh] sm:h-[20vh] w-full justify-between items-start sm:items-end pl-4 sm:pl-8">
        <div className="flex gap-2 justify-center items-center">
          <span className="w-1 h-16 bg-primary" />
          <p className="title">Users information</p>
        </div>
      </div>
      <div className="flex flex-col h-[65vh] w-full justify-evenly items-center">
        <div className="flex justify-end items-center gap-4 px-8 h-1/6 w-full">
          <input
            type="text"
            placeholder="Search by EMAIL"
            className="h-1/2 w-1/3 text-black rounded-3xl px-4 bg-[#212549]"
          />
          <MdSearch size={30} />
        </div>
        <div className="flex justify-center items-center h-5/6 w-full">
          <table className="w-4/5 h-full">
            <thead className="bg-secondary">
              <tr>
                <th className="w-[10%]">ID</th>
                <th className="w-[25%]">Name</th>
                <th className="w-[25%]">Email</th>
                <th className="w-[10%]">Banned</th>
                <th className="w-[30%]">Role</th>
              </tr>
            </thead>
            <tbody className="bg-[#212549]">
              {/* TODO component */}
              {registers.map((register) => {
                const { id, name, email } = register;
                return <TableCell key={id} id={id} name={name} email={email} />;
              })}
              {isFull ? (
                ''
              ) : (
                <tr>
                  <td className="text-center"></td>
                  <td className="text-center"></td>
                  <td className="text-center"></td>
                  <td className="text-center"></td>
                  <td className="text-center"></td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
      <div className="flex pt-4 pb-8 sm:pt-0 sm:pb-0 h-[25vh] w-full justify-center items-center">
        <button className="bg-primary px-4 py-2 rounded-md">
          Generate Report
        </button>
      </div>
    </div>
  );
};

export default Tables;
