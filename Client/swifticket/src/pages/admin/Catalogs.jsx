import React, { useEffect, useState } from 'react';
import { getOrganizers } from '../../services/Organizers.Services';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../state/atoms/tokenState';
import { getAllSponsors } from '../../services/SponsorServices';
import { getPlaces } from '../../services/PlaceServices';
import { Pagination } from '../../components/Pagination';

const Catalogs = () => {
  const token = useRecoilValue(tokenState);
  const [organizers, setOrganizers] = useState([]);
  const [sponsors, setSponsors] = useState([]);
  const [places, setPlaces] = useState([]);
  const [limitOrganizers, setLimitOrganizers] = useState(999);
  const [pageOrganizers, setPageOrgalizers] = useState(0);
  const [limitSponsors, setLimitSponsors] = useState(999);
  const [pageSponsors, setPageSponsors] = useState(0);
  const [limitPlaces, setLimitPlaces] = useState(999);
  const [pagePlaces, setPagePlaces] = useState(0);

  const handleOrganizers = async () => {
    const response = await getOrganizers(pageOrganizers);

    if (response.status === 200) {
      setOrganizers(response.data.content);
      setLimitOrganizers(response.data.totalPages);
    }
  };

  const handleSponsors = async () => {
    const response = await getAllSponsors(pageSponsors);

    if (response.status === 200) {
      setSponsors(response.data.content);
      setLimitSponsors(response.data.totalPages);
    }
  };

  const handlePlaces = async () => {
    const response = await getPlaces(pagePlaces);

    if (response.status === 200) {
      setPlaces(response.data.content);
      setLimitPlaces(response.data.totalPages);
    }
  };

  useEffect(() => {
    handleOrganizers();
  }, [pageOrganizers]);

  useEffect(() => {
    handleSponsors();
  }, [pageSponsors]);

  useEffect(() => {
    handlePlaces();
  }, [pagePlaces]);

  return (
    <div className="flex flex-col h-screen w-screen sm:w-[80vw]">
      <div className="flex flex-col sm:flex-row h-[30vh] sm:h-[20vh] w-full justify-between items-start sm:items-end pl-4 sm:pl-8">
        <div className="flex gap-2 justify-center items-center">
          <span className="w-1 h-16 bg-primary" />
          <p className="title">Catalogs Information</p>
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
              {organizers.map((data) => {
                const { id, name } = data;

                return (
                  <tr key={id} className="border-b-2 border-primary h-[20%]">
                    <td className="text-center">{name}</td>
                  </tr>
                );
              })}
              {organizers.length == 5 ? (
                <tr>
                  <td style={{ whiteSpace: 'pre-wrap' }}></td>
                </tr>
              ) : (
                <tr key={0}>
                  <td className="text-center"></td>
                </tr>
              )}
            </tbody>
          </table>
          <Pagination
            page={pageOrganizers}
            setPage={setPageOrgalizers}
            limit={limitOrganizers}
          />
          <table className="w-[90%] h-2/5">
            <thead className="bg-secondary">
              <tr>
                <th>Sponsor</th>
              </tr>
            </thead>
            <tbody className="bg-[#212549]">
              {sponsors.map((data) => {
                const { id, name } = data;

                return (
                  <tr key={id} className="border-b-2 border-primary h-[20%]">
                    <td className="text-center">{name}</td>
                  </tr>
                );
              })}
              {sponsors.length == 5 ? (
                <tr>
                  <td style={{ whiteSpace: 'pre-wrap' }}></td>
                </tr>
              ) : (
                <tr key={0}>
                  <td className="text-center"></td>
                </tr>
              )}
            </tbody>
          </table>
          <Pagination
            page={pageSponsors}
            setPage={setPageSponsors}
            limit={limitSponsors}
          />
        </div>
        <div className="flex flex-col gap-4 w-2/3 h-full justify-center items-center">
          <table className="w-[90%] h-full">
            <thead className="bg-secondary">
              <tr>
                <th className="w-1/3">Place</th>
                <th className="w-2/3">Address</th>
              </tr>
            </thead>
            <tbody className="bg-[#212549]">
              {places.map((data) => {
                const { id, name, address } = data;

                return (
                  <tr key={id} className="border-b-2 border-primary h-[10%]">
                    <td className="text-center">{name}</td>
                    <td className="text-center">{address}</td>
                  </tr>
                );
              })}
              {places.length == 10 ? (
                <tr>
                  <td style={{ whiteSpace: 'pre-wrap' }}></td>
                  <td style={{ whiteSpace: 'pre-wrap' }}></td>
                </tr>
              ) : (
                <tr key={0}>
                  <td className="text-center"></td>
                  <td className="text-center"></td>
                </tr>
              )}
            </tbody>
          </table>
          <Pagination
            page={pagePlaces}
            setPage={setPagePlaces}
            limit={limitPlaces}
          />
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
