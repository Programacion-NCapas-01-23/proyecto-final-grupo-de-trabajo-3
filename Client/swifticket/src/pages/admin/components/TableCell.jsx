import React from 'react';

const TableCell = ({ id, name, email }) => {
  return (
    <tr className=" border-b-2 border-primary h-[10%]">
      <td className="text-center">{id}</td>
      <td className="text-center">{name}</td>
      <td className="text-center">{email}</td>
      <td className="text-center">
        <input type="checkbox" name="banned" id="" />
      </td>
      <td className="text-center">
        <div className="flex justify-evenly">
          <div className="flex gap-1">
            <input type="checkbox" name="Admin" id="" />
            <p>Admin</p>
          </div>
          <div className="flex gap-1">
            <input type="checkbox" name="User" id="" />
            <p>User</p>
          </div>
          <div className="flex gap-1">
            <input type="checkbox" name="Collab" id="" />
            <p>Collab</p>
          </div>
          <div className="flex gap-1">
            <input type="checkbox" name="Mod" id="" />
            <p>Mod</p>
          </div>
        </div>
      </td>
    </tr>
  );
};

export default TableCell;
