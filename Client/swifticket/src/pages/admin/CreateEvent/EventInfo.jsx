import { MdCameraAlt } from 'react-icons/md';
import TitileWithLines from '../components/TitleWithLines';
import FormInput from '../components/FormInput';
import { useState } from 'react';
import { getOrganizers } from '../../../services/Organizers.Services';
import { useEffect } from 'react';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../../state/atoms/tokenState';
import { getPlaces } from '../../../services/PlaceServices';
import { createEvent } from '../../../services/Events.Services';
import { Toaster, toast } from 'react-hot-toast';

export default function EventInfo() {
  const token = useRecoilValue(tokenState);
  const [title, setTitle] = useState('');
  const [category, setCategory] = useState('');
  const [organizer, setOrganizer] = useState('');
  const [date, setDate] = useState('');
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [place, setPlace] = useState('');
  const eventCategories = [
    { id: 1, name: 'Music' },
    { id: 2, name: 'Sports' },
    { id: 3, name: 'Arts and Culture' },
    { id: 4, name: 'Business and Networking' },
    { id: 5, name: 'Community and Social' },
  ];
  const [eventImage, setEventImage] = useState('');
  const [eventOrganizers, setEventOrganizers] = useState([]);
  const [eventPlaces, setEventPlaces] = useState([]);

  const handleOrganizers = async () => {
    const response = await getOrganizers(0, 50);

    if (response.status === 200) {
      setEventOrganizers(response.data.content);
    }
  };

  const handlePlace = async () => {
    const response = await getPlaces(0, 50);

    if (response.status === 200) {
      setEventPlaces(response.data.content);
    }
  };

  const handleCreateEvent = async () => {
    const tempStart = new Date();
    const tempEnd = new Date();

    const tempDate = `${date.split('-')[2]}/${date.split('-')[1]}/${
      date.split('-')[0]
    }`;

    tempStart.setHours(parseInt(startTime.split(':')[0]));
    tempStart.setMinutes(parseInt(startTime.split(':')[1]));
    tempEnd.setHours(parseInt(endTime.split(':')[0]));
    tempEnd.setMinutes(parseInt(endTime.split(':')[1]));

    const miliDuration = tempEnd.getTime() - tempStart.getTime();

    const duration = Math.floor(miliDuration / 1000 / 60);

    const formData = new FormData();

    formData.append('title', title);
    formData.append('duration', duration);
    formData.append('dateTime', tempDate);
    formData.append('image', eventImage);
    formData.append('placeId', place);
    formData.append('categoryId', category);
    formData.append('organizerId', organizer);

    console.log('token: ', token);
    console.log('title: ', title);
    console.log('date: ', tempDate);
    console.log('duration: ', duration);
    console.log('image: ', eventImage);
    console.log('placeId: ', place);
    console.log('categoryId: ', category);
    console.log('organizerId: ', organizer);

    const response = await createEvent(token, formData);

    if (response.status === 201) {
      toast.success(response.data.message);
    } else {
      toast.error('Something went wrong');
    }
  };

  useEffect(() => {
    handleOrganizers();
    handlePlace();
  }, []);

  return (
    <>
      <section className="w-full px-default overflow-y-auto">
        <div>
          <Toaster />
        </div>
        <TitileWithLines title="Create Event" />
        <div className="grid px-default-2xl py-default grid-flow-col grid-cols-2 gap-default-xl ">
          <div>
            <FormInput label="Title" value={title} onInputChange={setTitle} />
            <div className="mb-default-sm">
              <p className="heading-sm">Organizer</p>
              <select
                className="bg-opacity-20 cursor-pointer bg-white w-full p-default-sm rounded-md"
                value={organizer}
                onChange={(e) => setOrganizer(e.target.value)}
              >
                <option className="text-black" defaultValue={0}></option>
                {eventOrganizers.map((organizer) => {
                  const { id, name } = organizer;

                  return (
                    <option className="text-black" key={id} value={id}>
                      {name}
                    </option>
                  );
                })}
              </select>
            </div>
            <div className="mb-default-sm">
              <p className="heading-sm">Category</p>
              <select
                className="bg-opacity-20 cursor-pointer bg-white w-full p-default-sm rounded-md"
                value={category}
                onChange={(e) => setCategory(e.target.value)}
              >
                <option className="text-black" defaultValue={0}></option>
                {eventCategories.map((category) => {
                  const { id, name } = category;

                  return (
                    <option className="text-black" key={id} value={id}>
                      {name}
                    </option>
                  );
                })}
              </select>
            </div>
            <FormInput
              label="Date"
              type="date"
              value={date}
              onInputChange={setDate}
            />
            <FormInput
              label="Start time"
              type="time"
              value={startTime}
              onInputChange={setStartTime}
            />
            <FormInput
              label="End time"
              type="time"
              value={endTime}
              onInputChange={setEndTime}
            />
          </div>
          <div>
            <div className="mb-default-sm">
              <p className="heading-sm">Place</p>
              <select
                className="bg-opacity-20 cursor-pointer bg-white w-full p-default-sm rounded-md"
                value={place}
                onChange={(e) => setPlace(e.target.value)}
              >
                <option className="text-black" defaultValue={0}></option>
                {eventPlaces.map((place) => {
                  const { id, name } = place;

                  return (
                    <option className="text-black" key={id} value={id}>
                      {name}
                    </option>
                  );
                })}
              </select>
            </div>

            <input
              type="file"
              className="self-center"
              onChange={(e) => setEventImage(e.target.files[0])}
            />
            {/* <button className="w-full h-4/5 flex items-center justify-center bg-slate-500 bg-opacity-30 rounded-2xl text-8xl hover:text-9xl hover:bg-gray-200 hover:bg-opacity-20 transition-all">
            <MdCameraAlt />
          </button> */}
          </div>
        </div>
      </section>
      <div className="flex w-full items-center md:gap-28 justify-evenly">
        <button className="action-button" onClick={() => handleCreateEvent()}>
          Create event
        </button>
      </div>
    </>
  );
}
