import axios from 'axios';
import BASE from './BASE.js';

const BASE_URL = `${BASE}/roles`;

// ROLES

export const validateToken = async (token) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      baseURL: BASE_URL,
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      data: {},
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
