import React from 'react';

const Catalogs = () => {
  const data1 = [
    { id: 1, value: 'Cantante generico' },
    { id: 2, value: 'Cantante generico' },
    { id: 3, value: 'Cantante generico' },
    { id: 4, value: 'Cantante generico' },
    { id: 5, value: 'Cantante generico' },
  ];
  const data2 = [
    { id: 1, value: 'Cantante generico' },
    { id: 2, value: 'Cantante generico' },
    { id: 3, value: 'Cantante generico' },
    { id: 4, value: 'Cantante generico' },
    { id: 5, value: 'Cantante generico' },
  ];
  const data3 = [
    { id: 1, place: 'Text', address: 'Text' },
    { id: 2, place: 'Text', address: 'Text' },
    { id: 3, place: 'Text', address: 'Text' },
    { id: 4, place: 'Text', address: 'Text' },
    { id: 5, place: 'Text', address: 'Text' },
    { id: 1, place: 'Text', address: 'Text' },
    { id: 2, place: 'Text', address: 'Text' },
    { id: 3, place: 'Text', address: 'Text' },
    { id: 4, place: 'Text', address: 'Text' },
    { id: 5, place: 'Text', address: 'Text' },
  ];

  return (
    <div className="flex flex-col h-screen w-screen sm:w-[80vw]">
      <div className="flex flex-col sm:flex-row h-[30vh] sm:h-[20vh] w-full justify-between items-start sm:items-end pl-4 sm:pl-8">
        <div className="flex gap-2 justify-center items-center">
          <span className="w-1 h-16 bg-primary" />
          <p className="title">Users information</p>
        </div>
      </div>
      <div className="flex h-[65vh] w-full">
        <div className="flex flex-col justify-between items-center w-1/3 h-full">
          <table className="w-[90%] h-2/5">
            <thead className="bg-secondary">
              <tr>
                <th>Organizer</th>
              </tr>
            </thead>
            <tbody className="bg-[#212549]">
              {data1.map((data) => {
                const { id, value } = data;

                return (
                  <tr key={id} className="border-b-2 border-primary h-[20%]">
                    <td className="text-center">{value}</td>
                  </tr>
                );
              })}
              {data1.length == 5 ? (
                ''
              ) : (
                <tr>
                  <td className="text-center"></td>
                </tr>
              )}
            </tbody>
          </table>
          <table className="w-[90%] h-2/5">
            <thead className="bg-secondary">
              <tr>
                <th>Sponsor</th>
              </tr>
            </thead>
            <tbody className="bg-[#212549]">
              {data2.map((data) => {
                const { id, value } = data;

                return (
                  <tr key={id} className="border-b-2 border-primary h-[20%]">
                    <td className="text-center">{value}</td>
                  </tr>
                );
              })}
              {data2.length == 5 ? (
                ''
              ) : (
                <tr>
                  <td className="text-center"></td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
        <div className="flex w-2/3 h-full justify-center items-center">
          <table className="w-[90%] h-full">
            <thead className="bg-secondary">
              <tr>
                <th className="w-1/3">Place</th>
                <th className="w-2/3">Address</th>
              </tr>
            </thead>
            <tbody className="bg-[#212549]">
              {data3.map((data) => {
                const { id, place, address } = data;

                return (
                  <tr key={id} className="border-b-2 border-primary h-[10%]">
                    <td className="text-center">{place}</td>
                    <td className="text-center">{address}</td>
                  </tr>
                );
              })}
              {data3.length == 10 ? (
                ''
              ) : (
                <tr>
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

export default Catalogs;
