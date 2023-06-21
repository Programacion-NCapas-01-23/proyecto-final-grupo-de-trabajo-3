import axios from 'axios';
import uriDataConstructor from './UriDataConstructor';

const BASE_URL = 'http://localhost:8080/users';

export const changePass = async (email, pass, newPass) => {
  let response = undefined;
  const uriDataObject = {
    email: email,
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
