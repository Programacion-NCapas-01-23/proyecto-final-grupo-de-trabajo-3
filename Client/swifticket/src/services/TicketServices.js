import axios from 'axios';
import BASE from './BASE';
import uriDataConstructor from './UriDataConstructor';

const endpoint = 'tickets';
const postHeaderEncoded = { 'Content-Type': 'application/x-www-form-urlencoded' };
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

export async function getTicketByUser(token, page = 1){
    page -= 1;
    try {
        const response = await axios.get(
            `${BASE}/${endpoint}/user?page=${page}`,
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

export async function generateTicketCode(token, _tokenId) {
    try {
        const response = await axios.post(
        `${BASE}/${endpoint}/generate-code`,
        `${uriDataConstructor({
            ticketId: _tokenId
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

export async function validateTicket(token, _code) {
    try {
        const response = await axios.patch(
        `${BASE}/${endpoint}/validate-ticket`,
        `${uriDataConstructor({
            verificationToken: _code
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

export async function startTransferTicket(token) {
    try {
        const response = await axios.post(
        `${BASE}/${endpoint}/transfer`,
        null,
        { headers: {...postHeaderEncoded , ...getHeader(token)} }
        );
        return response;
    }
    catch (error) {
        console.log(error);
        return error.response;
    }
}

export async function acceptTransferTicket(token, _ticketId, _transferId) {
    try {
        const response = await axios.put(
        `${BASE}/${endpoint}/transfer`,
        `${uriDataConstructor({
            ticketId: _ticketId,
            transferId: _transferId
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
