import React, { useEffect, useState } from 'react';
import TitileWithLines from '../components/TitleWithLines';
import FormInput from '../components/FormInput';
import { MdAddBox, MdDelete, MdDeleteSweep } from 'react-icons/md';
import { getAllSponsors } from '../../../services/SponsorServices';
import {
  assignSponsor,
  getEventById,
  removeSponsor,
} from '../../../services/Events.Services';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';

export default function EventTiersAndSponsors({ setIsEdit, eventId }) {
  const token = useRecoilValue(tokenState);
  const [eventSponsors, setEventSponsors] = useState([]);

  const handleGetSponsor = async () => {
    const response = await getAllSponsors(0, 50);

    if (response.status === 200) {
      console.log(response.data.content);
      setEventSponsors(response.data.content);
    }
  };
  // const eventSponsors = [
  //   'Nike',
  //   'Apple',
  //   'Coca-Cola',
  //   'Google',
  //   'Amazon',
  //   'Microsoft',
  // ];

  useEffect(() => {
    handleGetSponsor();
  }, []);

  return (
    <section className="w-full px-default">
      <TitileWithLines title="Event Details" />
      <div className="flex flex-col items-center py-default min-h-[calc(100vh-18rem)]">
        <section className="w-1/2">
          <Sponsors
            sponsorsList={eventSponsors}
            eventId={eventId}
            token={token}
          />
        </section>
        <section className="w-1/2">
          <Tiers />
        </section>
        <button
          className="action-button"
          onClick={() => {
            setIsEdit(false);
          }}
        >
          Cancel
        </button>
      </div>
    </section>
  );
}

function Tiers() {
  const [tiers, setTiers] = useState([]);

  const [name, setName] = useState('');
  const [capacity, setCapacity] = useState('');
  const [price, setPrice] = useState('');

  const handleAddTier = (tierName, tierCapacity, tierPrice) => {
    if (tierName !== '' && tierCapacity !== '' && tierPrice !== '') {
      const newTier = [tierName, tierCapacity, tierPrice];
      setName('');
      setCapacity('');
      setPrice('');
      setTiers([...tiers, newTier]);
    }
  };

  const handleDeleteTier = (index) => {
    const updatedTiers = [...tiers];
    updatedTiers.splice(index, 1);
    setTiers(updatedTiers);
  };

  return (
    <>
      <div className="grid grid-flow-col gap-4">
        <FormInput
          label="Name"
          type="text"
          value={name}
          onInputChange={setName}
        />
        <FormInput
          label="Capacity"
          type="number"
          value={capacity}
          onInputChange={setCapacity}
        />
        <FormInput
          label="Price"
          type="number"
          value={price}
          onInputChange={setPrice}
        />
        <button
          onClick={() => {
            handleAddTier(name, capacity, price);
          }}
          className="justify-self-end text-4xl text-primary"
        >
          <MdAddBox />
        </button>
      </div>
      <ul className="max-h-52 overflow-auto">
        {tiers.map((tier, index) => (
          <li
            key={index}
            className="my-default-xs grid grid-flow-col grid-cols-4 gap-4 text-neutral-300"
          >
            <div className="col-span-2 p-default bg-neutral-950 rounded-xl ">
              {tier[0]}
            </div>
            <div className="p-default bg-neutral-950 rounded-xl ">
              {tier[1]}
            </div>
            <div className="p-default bg-neutral-950 rounded-xl ">
              ${tier[2]}
            </div>
            <button
              onClick={() => handleDeleteTier(index)}
              className="text-4xl text-red-500"
            >
              <MdDelete />
            </button>
          </li>
        ))}
      </ul>
    </>
  );
}

function Sponsors({ sponsorsList, eventId, token }) {
  const [sponsors, setSponsors] = useState([]);
  const [selectedSponsor, setSelectedSponsor] = useState({});
  const [sponsorId, setSponsorId] = useState(0);

  const handleGetSponsor = async () => {
    const response = await getEventById(eventId);

    if (response.status === 200) {
      setSponsors(response.data.sponsors);
    }
  };

  const handleAddSponsor = async () => {
    if (selectedSponsor !== '' && !sponsors.includes(selectedSponsor)) {
      const response = await assignSponsor(eventId, selectedSponsor, token);

      if (response.status === 200) {
        setSponsors([...sponsors, { id: sponsorId, name: selectedSponsor }]);
        setSelectedSponsor({});
      }
    }
  };

  const handleDeleteSponsor = async (index) => {
    const response = await removeSponsor(eventId, index, token);

    if (response.status === 200) {
      const updatedSponsors = [...sponsors];
      updatedSponsors.splice(updatedSponsors.index, 1);
      setSponsors(updatedSponsors);
    }
  };

  useEffect(() => {
    handleGetSponsor();
  }, []);

  return (
    <>
      <div className="grid grid-flow-col grid-cols-4 gap-4">
        <div className="col-span-4">
          <div className="mb-default-sm">
            <p className="heading-sm">Sponsors</p>
            <select
              className="bg-opacity-20 cursor-pointer bg-white w-full p-default-sm rounded-md"
              value={sponsorId}
              onChange={(e) => {
                setSelectedSponsor(
                  e.target.options[e.target.selectedIndex].text
                );
                setSponsorId(e.target.value);
              }}
            >
              <option className="text-black" defaultValue={{}}></option>
              {sponsorsList.map((sponsor) => {
                const { id, name } = sponsor;

                return (
                  <option className="text-black" value={id}>
                    {name}
                  </option>
                );
              })}
            </select>
          </div>
        </div>
        <button onClick={handleAddSponsor} className="text-4xl text-primary">
          <MdAddBox />
        </button>
      </div>
      <ul className="max-h-52 overflow-auto">
        {sponsors.map((sponsor) => {
          const { id, name } = sponsor;

          return (
            <li
              key={id}
              className="my-default-xs grid grid-flow-col grid-cols-4 gap-4"
            >
              <div className="col-span-4 p-default bg-neutral-950 rounded-xl text-neutral-300">
                {name}
              </div>
              <button
                onClick={() => handleDeleteSponsor(id)}
                className="text-4xl text-red-500"
              >
                <MdDelete />
              </button>
            </li>
          );
        })}
      </ul>
    </>
  );
}
