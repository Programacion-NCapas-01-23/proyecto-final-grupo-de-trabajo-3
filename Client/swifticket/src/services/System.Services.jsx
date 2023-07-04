import axios from 'axios';
import BASE from './BASE.js';

const BASE_URL = `${BASE}/system`;

// SYSTEM

export const getSystemState = async () => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      baseURL: BASE_URL,
      url: '/system-state',
    });

    if (data) {
      response = data;
    }
  } catch (error) {
    console.log(error);
    response = error.response;
  } finally {
    return response;
  }
};

export const suspendService = async (token) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'POST',
      baseURL: BASE_URL,
      url: '/suspend-service',
      headers: { Authorization: `Bearer ${token}` },
    });

    if (data) {
      response = data;
    }
  } catch (error) {
    console.log(error);
    response = error.response;
  } finally {
    return response;
  }
};
