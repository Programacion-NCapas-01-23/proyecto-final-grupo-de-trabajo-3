import axios from 'axios';
import BASE from './BASE';

const endpoint = 'stats';
const getHeader = (token) => {
    return { Authorization: `Bearer ${token}` };
};

export async function getGeneralStats(token){
    try {
        const response = await axios.get(
            `${BASE}/${endpoint}/general`,
            {headers: getHeader(token)}
        )
        return response
    } catch (error) {
        console.log(error);
        return error.response
    }
}

export async function getEventStats(token, eventID){
    try {
        const response = await axios.get(
            `${BASE}/${endpoint}/event/${eventID}`,
            {headers: getHeader(token)}
        )
        return response
    } catch (error) {
        console.log(error);
        return error.response
    }
}

export async function getEventAttendance(token, eventID){
    try {
        const response = await axios.get(
            `${BASE}/${endpoint}/event/${eventID}/attendance`,
            {headers: getHeader(token)}
        )
        return response
    } catch (error) {
        console.log(error);
        return error.response
    }
}