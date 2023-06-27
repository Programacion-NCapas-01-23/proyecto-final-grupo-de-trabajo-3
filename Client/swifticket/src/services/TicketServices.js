import axios from 'axios';
import BASE from './BASE';
import uriDataConstructor from './UriDataConstructor';

const endpoint = 'tickets';
const postHeaderEncoded = { 'Content-Type': 'application/x-www-form-urlencoded' };
const postHeaderForm = { 'Content-Type': 'multipart/form-data' };
const getHeader = (token) => {
    return { Authorization: `Bearer ${token}` };
};

export async function getTicketByID(token, ticketID){
    try {
        const response = await axios.get(
            `${BASE}/${endpoint}/${ticketID}`,
            {headers: getHeader(token)}
        )
        return response
    } catch (error) {
        console.log(error);
        return error.response
    }
}

export async function getTicketByUser(token, userID){
    try {
        const response = await axios.get(
            `${BASE}/${endpoint}/${userID}`,
            {headers: getHeader(token)}
        )
        return response
    } catch (error) {
        console.log(error);
        return error.response
    }
}

export async function createTicket(token, _tierId) {
    try {
        const response = await axios.post(
        `${BASE}/${endpoint}`,
        `${uriDataConstructor({
            tierId: _tierId
        })}`,
        { headers: {...postHeaderEncoded , ...getHeader(token)} }
        );
        return response;
    }
    catch (error) {
        console.log(error);
        return error.response;
    }
}