import axios from 'axios';
import uriDataConstructor from './UriDataConstructor';
import BASE from './BASE';

const BASE_URL = `${BASE}/users`;

// EVENTS

export const assignUserToEvent = async (userId, eventId) => {
  let response = undefined;
  const uriDataObject = {
    userId: userId,
    eventId: eventId,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'POST',
      baseURL: BASE_URL,
      url: '/assign-to-event',
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

export const removeUserFromEvent = async (userId, eventId) => {
  let response = undefined;
  const uriDataObject = {
    userId: userId,
    eventId: eventId,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'DELETE',
      baseURL: BASE_URL,
      url: '/assign-to-event',
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

// ROLES

export const removeRole = async (id, role) => {
  let response = undefined;
  const uriDataObject = {
    userId: id,
    role: role,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'DELETE',
      baseURL: BASE_URL,
      url: '/role',
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

export const assignRole = async (id, role) => {
  let response = undefined;
  const uriDataObject = {
    userId: id,
    role: role,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'POST',
      baseURL: BASE_URL,
      url: '/role',
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

// USER

export const getAllUsers = async () => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      baseURL: BASE_URL,
      url: `/`,
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

export const getOneUser = async (id) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      baseURL: BASE_URL,
      url: `/${id}`,
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

export const updateUser = async (name, avatar) => {
  let response = undefined;
  const uriDataObject = {
    name: name,
    avatar: avatar,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'PUT',
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
    console.log(error);
  } finally {
    return response;
  }
};

export const changePass = async (token, pass, newPass) => {
  let response = undefined;
  const uriDataObject = {
    password: pass,
    newPassword: newPass,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'PATCH',
      baseURL: BASE_URL,
      url: '/change-password',
      headers: {
        Authorization: `Bearer ${token}`,
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

export const toggleStatus = async (userId, state) => {
  let response = undefined;
  const uriDataObject = {
    userId: userId,
    state: state,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'PATCH',
      baseURL: BASE_URL,
      url: '/toggle-status',
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
