import axios from 'axios';
import BASE from './BASE';
import uriDataConstructor from './UriDataConstructor';

const endpoint = 'places';
const postHeaderEncoded = {
  'Content-Type': 'application/x-www-form-urlencoded',
};
const getHeader = (token) => {
  return { Authorization: `Bearer ${token}` };
};

export async function getPlaces(page, size = 10) {
  try {
    const response = await axios.get(
      `${BASE}/${endpoint}?page=${page}&size=${size}`
    );
    return response;
  } catch (error) {
    console.log(error);
    return error.response;
  }
}

export async function getPlace(placeID, token) {
  try {
    const response = await axios.get(`${BASE}/${endpoint}/${placeID}`, {
      headers: getHeader(token),
    });
    return response;
  } catch (error) {
    console.log(error);
    return error.response;
  }
}

export async function createPlace(token, _name, _address) {
  try {
    const response = await axios.post(
      `${BASE}/${endpoint}`,
      `${uriDataConstructor({
        name: _name,
        address: _address,
      })}`,
      { headers: { ...postHeaderEncoded, ...getHeader(token) } }
    );
    return response;
  } catch (error) {
    console.log(error);
    return error.response;
  }
}

export async function deletePlace(token, placeID) {
  try {
    const response = await axios.delete(`${BASE}/${endpoint}/${placeID}`, {
      headers: getHeader(token),
    });
    return response;
  } catch (error) {
    console.log(error);
    return error.response;
  }
}
