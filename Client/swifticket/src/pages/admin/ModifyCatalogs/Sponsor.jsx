import { MdCameraAlt } from 'react-icons/md';
import FormInput from '../components/FormInput';
import TitileWithLines from '../components/TitleWithLines';
import React, { useState } from 'react';
import { createSponsor } from '../../../services/SponsorServices';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';
import { Toaster, toast } from 'react-hot-toast';

export default function Sponsor() {
  const token = useRecoilValue(tokenState);
  const [sponsorName, setSponsorName] = useState('');
  const [fileRoute, setFileRoute] = useState('');

  const handleSubmit = async () => {
    const formData = new FormData();

    formData.append('name', sponsorName);
    formData.append('image', fileRoute);

    console.log(formData);

    const response = await createSponsor(token, formData);

    if (response.status === 201) {
      toast.success(response.data.message);
      setSponsorName('');
      setFileRoute('');
    } else {
      toast.error('Please verify your info');
    }
  };

  return (
    <section className="w-full">
      <div>
        <Toaster />
      </div>
      <TitileWithLines title="Sponsor" />
      <div className="w-3/5 m-auto flex flex-col">
        <div className="w-1/2 self-center">
          <div className="mb-default-sm">
            <p className="heading-sm">Name</p>
            <input
              className="cursor-pointer bg-opacity-20 bg-white w-full p-default-sm rounded-md"
              type="text"
              value={sponsorName}
              onChange={(e) => setSponsorName(e.target.value)}
            />
          </div>
        </div>
        <input
          type="file"
          className="self-center"
          onChange={(e) => setFileRoute(e.target.files[0])}
        />

        {/* <button className="w-96 h-96 flex self-center items-center justify-center bg-slate-500 bg-opacity-30 rounded-2xl text-8xl hover:text-9xl hover:bg-gray-200 hover:bg-opacity-20 transition-all">
          <MdCameraAlt />
        </button> */}
        <div className="flex justify-evenly mt-default-sm">
          <button className="subaction-button w-fit self-end"> Cancel </button>
          <button
            onClick={handleSubmit}
            className="action-button w-fit self-end"
          >
            {' '}
            Save{' '}
          </button>
        </div>
      </div>
    </section>
  );
}
