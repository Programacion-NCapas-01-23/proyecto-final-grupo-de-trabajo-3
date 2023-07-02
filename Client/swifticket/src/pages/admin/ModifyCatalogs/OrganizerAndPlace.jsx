import React, { useState } from 'react';
import TitileWithLines from '../components/TitleWithLines';
import { createPlace } from '../../../services/PlaceServices';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';
import { createOrganizer } from '../../../services/Organizers.Services';
import { Toaster, toast } from 'react-hot-toast';

const OrganizerAndPlace = () => {
  const [placeName, setPlaceName] = useState('');
  const [placeAddress, setPlaceAddress] = useState('');
  const [organizerName, setOrganizerName] = useState('');
  const token = useRecoilValue(tokenState);

  const handlePlace = async () => {
    const response = await createPlace(token, placeName, placeAddress);

    if (response.status === 201) {
      toast.success(response.data.message);
      setPlaceName('');
      setPlaceAddress('');
    } else {
      Object.keys(response.data).map((key) =>
        response.data[key].map((response) => toast.error(response))
      );
    }
  };

  const handleOrganizer = async () => {
    const response = await createOrganizer(token, organizerName);

    if (response.status === 201) {
      console.log(response);
      toast.success(response.data.message);
      setOrganizerName('');
    } else {
      Object.keys(response.data).map((key) =>
        response.data[key].map((response) => toast.error(response))
      );
    }
  };

  return (
    <section className="w-full">
      <div>
        <Toaster />
      </div>
      <TitileWithLines title="Place" />
      <section className="w-3/5 m-auto grid grid-cols-5">
        <div className="col-span-3">
          <div className="mb-default-sm">
            <p className="heading-sm">Name</p>
            <input
              className="cursor-pointer bg-opacity-20 bg-white w-full p-default-sm rounded-md"
              type="text"
              value={placeName}
              onChange={(e) => setPlaceName(e.target.value)}
            />
          </div>
        </div>
        <div className="col-span-3">
          <div className="mb-default-sm">
            <p className="heading-sm">Address</p>
            <input
              className="cursor-pointer bg-opacity-20 bg-white w-full p-default-sm rounded-md"
              type="text"
              value={placeAddress}
              onChange={(e) => setPlaceAddress(e.target.value)}
            />
          </div>
        </div>
        <button
          className="action-button h-fit self-center col-start-5"
          onClick={() => handlePlace()}
        >
          ADD
        </button>
      </section>
      <TitileWithLines title="Organizer" />
      <section className="w-3/5 m-auto grid grid-cols-5">
        <div className="col-span-3">
          <div className="mb-default-sm">
            <p className="heading-sm">Name</p>
            <input
              className="cursor-pointer bg-opacity-20 bg-white w-full p-default-sm rounded-md"
              type="text"
              value={organizerName}
              onChange={(e) => setOrganizerName(e.target.value)}
            />
          </div>
        </div>
        <button
          className="action-button h-fit self-center col-start-5"
          onClick={() => handleOrganizer()}
        >
          ADD
        </button>
      </section>
    </section>
  );
};

export default OrganizerAndPlace;
