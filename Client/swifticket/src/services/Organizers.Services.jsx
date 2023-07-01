import axios from 'axios';
import BASE from './BASE.js';
import uriDataConstructor from './UriDataConstructor';

const BASE_URL = `${BASE}/organizers`;

// ORGANIZERS

export const getOrganizers = async (page) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      baseURL: `${BASE_URL}?page=${page}&size=5`,
    });

    if (data) {
      response = data;
    }
  } catch (error) {
    response = error.response;
    console.log(error);
  } finally {
    return response;
  }
};

export const createOrganizer = async (organizerName) => {
  let response = undefined;
  const uriDataObject = {
    name: organizerName,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'POST',
      baseURL: BASE_URL,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      data: `${body}`,
    });

    if (data) {
      response = data;
    }
  } catch (error) {
    response = error.response;
    console.log(error);
  } finally {
    return response;
  }
};

export const updateOrganizer = async (organizerId, organizerName) => {
  let response = undefined;
  const uriDataObject = {
    name: organizerName,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'PUT',
      baseURL: BASE_URL,
      url: `/${organizerId}`,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      data: `${body}`,
    });

    if (data) {
      response = data;
    }
  } catch (error) {
    response = error.response;
    console.log(error);
  } finally {
    return response;
  }
};

export const deleteOrganizer = async (organizerId) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'DELETE',
      baseURL: BASE_URL,
      url: `/${organizerId}`,
    });

    if (data) {
      response = data;
    }
  } catch (error) {
    response = error.response;
    console.log(error);
  } finally {
    return response;
  }
};
