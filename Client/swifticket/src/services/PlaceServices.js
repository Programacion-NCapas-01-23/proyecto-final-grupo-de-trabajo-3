import axios from 'axios';
import BASE from './BASE';
import uriDataConstructor from './UriDataConstructor';

const endpoint = 'places';
const postHeaderEncoded = { 'Content-Type': 'application/x-www-form-urlencoded' };
const postHeaderForm = { 'Content-Type': 'multipart/form-data' };
const getHeader = (token) => {
    return { Authorization: `Bearer ${token}` };
};

export async function getPlaces(token){
    try {
        const response = await axios.get(
            `${BASE}/${endpoint}`,
            {headers: getHeader(token)}
        )
        return response
    } catch (error) {
        console.log(error);
        return error.response
    }
}

export async function getPlace(placeID, token){
    try {
        const response = await axios.get(
            `${BASE}/${endpoint}/${placeID}`,
            {headers: getHeader(token)}
        )
        return response
    } catch (error) {
        console.log(error);
        return error.response
    }
}