import axios from 'axios';
import uriDataConstructor from './UriDataConstructor';
import BASE from './BASE';

const BASE_URL = `${BASE}/auth`;

// AUTH

export const validateToken = async (token) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      baseURL: BASE_URL,
      url: '/validate-token',
      headers: { Authorization: `Bearer ${token}` },
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

export const validatedAccount = async (code) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      baseURL: BASE_URL,
      url: `/validate-account/${code}`,
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

export const validateAccount = async (email) => {
  let response = undefined;
  const uriDataObject = {
    email: email,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'POST',
      baseURL: BASE_URL,
      url: '/validate-account',
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

export const signIn = async (user, pass) => {
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

export const signUp = async (userName, email, password, avatarId) => {
  let response = undefined;
  const uriDataObject = {
    name: userName,
    email: email,
    password: password,
    avatarId: avatarId,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'POST',
      baseURL: BASE_URL,
      url: '/signup',
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
