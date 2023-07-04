import { useState } from 'react';
import EventInfo from './EventInfo';
import EventTiersAndSponsors from './EventTiersAndSponsors';

export default function CreateEvent() {
  //   const [isInDetails, setIsInDetails] = useState(false);

  return (
    <main className="flex flex-col w-full min-h-full">
      {
        // isInDetails ? (<EventTiersAndSponsors/>) :( <EventInfo />)
      }

      <EventInfo />
    </main>
  );
}
