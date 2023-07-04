import { MdKeyboardArrowLeft, MdKeyboardArrowRight } from 'react-icons/md';
import OrganizerAndPlace from './OrganizerAndPlace';
import Sponsor from './Sponsor';
import { useState } from 'react';

export default function ModifyCatalogs() {
  const [addSponsor, setAddSponsor] = useState(false);

  const toggleSponsor = () => {
    setAddSponsor(!addSponsor);
  };

  return (
    <main className="w-full flex">
      <MdKeyboardArrowLeft
        onClick={toggleSponsor}
        className="text-9xl self-center cursor-pointer"
      />
      {addSponsor ? <Sponsor /> : <OrganizerAndPlace />}
      <MdKeyboardArrowRight
        onClick={toggleSponsor}
        className="text-9xl self-center cursor-pointer"
      />
    </main>
  );
}
