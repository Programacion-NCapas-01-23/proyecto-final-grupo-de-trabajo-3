import axios from 'axios';
import uriDataConstructor from './UriDataConstructor';

const BASE_URL = 'http://localhost:8080/auth';

export const login = async (user, pass) => {
  let response = undefined;
  const uriDataObject = {
    email: user,
    password: pass,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'POST',
      baseURL: BASE_URL,
      url: '/signin',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      data: `${body}`,
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

export const googleSignIn = async (token) => {
  let response = undefined;
  const uriDataObject = {
    idToken: token,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'POST',
      baseURL: BASE_URL,
      url: '/google/signin',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      data: `${body}`,
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
