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
  } finally {
    return response;
  }
};

export const suspendService = async () => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'POST',
      baseURL: BASE_URL,
      url: '/suspend-service',
    });

    if (data) {
      response = data;
    }
  } catch (error) {
    console.log(error);
  } finally {
    return response;
  }
};
