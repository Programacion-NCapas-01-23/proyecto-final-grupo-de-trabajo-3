import axios from 'axios';
import BASE from './BASE.js';
import uriDataConstructor from './UriDataConstructor';

const BASE_URL = `${BASE}/organizers`;

// ORGANIZERS

export const getOrganizers = async (page, size = 5) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      baseURL: `${BASE_URL}?page=${page}&size=${size}`,
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

export const createOrganizer = async (token, organizerName) => {
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
        Authorization: `Bearer ${token}`,
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
