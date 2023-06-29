import axios from 'axios';
import BASE from './BASE.js';
import uriDataConstructor from './UriDataConstructor';

const BASE_URL = `${BASE}/events`;

// SPONSORS

export const assignSponsor = async (eventId, sponsorName) => {
  let response = undefined;
  const uriDataObject = {
    eventId: eventId,
    sponsorName: sponsorName,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'POST',
      baseURL: BASE_URL,
      url: '/sponsors',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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

export const removeSponsor = async (eventId, sponsorId) => {
  let response = undefined;
  const uriDataObject = {
    eventId: eventId,
    sponsorId: sponsorId,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'DELETE',
      baseURL: BASE_URL,
      url: '/sponsors',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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

// TIERS

export const getEventTier = async (eventId) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      baseURL: BASE_URL,
      url: `/${eventId}/tiers`,
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

export const createEventTier = async (eventId, tierName, capacity, price) => {
  let response = undefined;
  const uriDataObject = {
    eventId: eventId,
    name: tierName,
    capacity: capacity,
    price: price,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'POST',
      baseURL: BASE_URL,
      url: '/tiers',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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

export const updateEventTier = async (eventId, tierName, capacity, price) => {
  let response = undefined;
  const uriDataObject = {
    name: tierName,
    capacity: capacity,
    price: price,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'PUT',
      baseURL: BASE_URL,
      url: `/tiers/${eventId}`,
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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

export const deleteEventTier = async (tierId) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'DELETE',
      baseURL: BASE_URL,
      url: `/tiers/${tierId}`,
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

// EVENTS

export const getAllEvents = async () => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      url: "http://ec2-3-223-145-52.compute-1.amazonaws.com/events",
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

export const getEventById = async (eventId) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      baseURL: BASE_URL,
      url: `/${eventId}`,
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

export const getEventByCategory = async (category) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      baseURL: BASE_URL,
      url: `/category/${category}`,
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

export const getEventByStatus = async (status) => {
  let response = undefined;

  try {
    const data = await axios({
      method: 'GET',
      baseURL: BASE_URL,
      url: `/state/${status}`,
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

export const createEvent = async (
  title,
  duration,
  dateTime,
  image,
  placeId,
  categoryId,
  organizerId
) => {
  let response = undefined;
  const uriDataObject = {
    title: title,
    duration: duration,
    dateTime: dateTime,
    image: image,
    placeId: placeId,
    categoryId: categoryId,
    organizerId: organizerId,
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
    console.log(error);
  } finally {
    return response;
  }
};

export const updateEvent = async (
  eventId,
  title,
  duration,
  dateTime,
  image,
  placeId,
  categoryId,
  organizerId
) => {
  let response = undefined;
  const uriDataObject = {
    title: title,
    duration: duration,
    dateTime: dateTime,
    image: image,
    placeId: placeId,
    categoryId: categoryId,
    organizerId: organizerId,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'PUT',
      baseURL: BASE_URL,
      url: `/${eventId}`,
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

export const changeEventStatus = async (eventId, statusId) => {
  let response = undefined;
  const uriDataObject = {
    eventId: eventId,
    statusId: statusId,
  };
  let body = uriDataConstructor(uriDataObject);

  try {
    const data = await axios({
      method: 'PATCH',
      baseURL: BASE_URL,
      url: '/change-status',
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
