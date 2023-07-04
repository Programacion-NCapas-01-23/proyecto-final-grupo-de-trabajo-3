import React, { useEffect, useState } from 'react';
import { MdSearch } from 'react-icons/md';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../state/atoms/tokenState';
import {
  assignRole,
  getAllUsers,
  removeRole,
  toggleStatus,
} from '../../services/User.Services';
import { Pagination } from '../../components/Pagination';

const Tables = () => {
  const token = useRecoilValue(tokenState);
  const [registers, setRegisters] = useState([]);
  const [limit, setLimit] = useState(999);
  const [page, setPage] = useState(0);
  const isFull = registers.length == 10;

  const handleUsers = async () => {
    const response = await getAllUsers(token, page);
    console.log(response);

    if (response.status === 200) {
      setRegisters(response.data.content);
      setLimit(response.data.totalPages);
    }
  };

  const handleUserState = async (userId, status) => {
    const response = await toggleStatus(token, userId, status);

    if (response.status === 200) {
      console.log(response.data);
    }
  };

  const handleAssignRoles = async (userId, role) => {
    const response = await assignRole(token, userId, role);

    if (response.status === 200) {
      console.log(response.data);
    }
  };

  const handleRemoveRoles = async (userId, role) => {
    const response = await removeRole(token, userId, role);

    if (response.status === 200) {
      console.log(response.data);
    }
  };

  useEffect(() => {
    handleUsers();
    console.log(registers);
  }, [page]);

  return (
    <div className="flex flex-col h-screen w-screen sm:w-[80vw]">
      <div className="flex flex-col sm:flex-row h-[30vh] sm:h-[20vh] w-full justify-between items-start sm:items-end pl-4 sm:pl-8">
        <div className="flex gap-2 justify-center items-center">
          <span className="w-1 h-16 bg-primary" />
          <p className="title">Users Information</p>
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
              {registers.length !== 0
                ? registers.map((register, index) => {
                    const { id, name, email, state, roles } = register;
                    let tempRoles = [
                      roles.find((role) => role.id === 1) ? true : false,
                      roles.find((role) => role.id === 2) ? true : false,
                      roles.find((role) => role.id === 3) ? true : false,
                      roles.find((role) => role.id === 4) ? true : false,
                    ];

                    return (
                      <tr
                        key={id}
                        className="border-b-2 border-primary h-[10%]"
                      >
                        <td className="text-center">{index + 1}</td>
                        <td className="text-center">{name}</td>
                        <td className="text-center">{email}</td>
                        <td className="text-center">
                          <input
                            type="checkbox"
                            name="banned"
                            id=""
                            defaultChecked={state.id === 2}
                            onChange={() => {
                              state.id === 2
                                ? handleUserState(email, 1)
                                : handleUserState(email, 2);
                            }}
                          />
                        </td>
                        <td className="text-center">
                          <div className="flex justify-evenly">
                            <div className="flex gap-1">
                              <input
                                type="checkbox"
                                name="Admin"
                                id=""
                                defaultChecked={tempRoles[0]}
                                onChange={() => {
                                  tempRoles[0]
                                    ? handleRemoveRoles(email, 1)
                                    : handleAssignRoles(email, 1);

                                  tempRoles[0] = !tempRoles[0];
                                }}
                              />
                              <p>Admin</p>
                            </div>
                            <div className="flex gap-1">
                              <input
                                type="checkbox"
                                name="User"
                                id=""
                                defaultChecked={tempRoles[1]}
                                onChange={() => {
                                  tempRoles[1]
                                    ? handleRemoveRoles(email, 2)
                                    : handleAssignRoles(email, 2);

                                  tempRoles[1] = !tempRoles[1];
                                }}
                              />
                              <p>User</p>
                            </div>
                            <div className="flex gap-1">
                              <input
                                type="checkbox"
                                name="Collab"
                                id=""
                                defaultChecked={tempRoles[2]}
                                onChange={() => {
                                  tempRoles[2]
                                    ? handleRemoveRoles(email, 3)
                                    : handleAssignRoles(email, 3);

                                  tempRoles[2] = !tempRoles[2];
                                }}
                              />
                              <p>Mod</p>
                            </div>
                            <div className="flex gap-1">
                              <input
                                type="checkbox"
                                name="Mod"
                                id=""
                                defaultChecked={tempRoles[3]}
                                onChange={() => {
                                  tempRoles[3]
                                    ? handleRemoveRoles(email, 4)
                                    : handleAssignRoles(email, 4);

                                  tempRoles[3] = !tempRoles[3];
                                }}
                              />
                              <p>Collab</p>
                            </div>
                          </div>
                        </td>
                      </tr>
                    );
                  })
                : ''}
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

      <div className="flex flex-col pt-4 pb-8 sm:pt-0 sm:pb-0 h-[25vh] w-full gap-4 justify-center items-center">
        <Pagination page={page} setPage={setPage} limit={limit} />
        <button className="bg-primary px-4 py-2 rounded-md">
          Generate Report
        </button>
      </div>
    </div>
  );
};

export default Tables;
