import axios from "axios";
import BASE from "./BASE";

const endpoint = "sponsors";
const postHeaderEncoded = { "Content-Type": "application/x-www-form-urlencoded" };
const postHeaderForm = { "Content-Type": "multipart/form-data" };
const getHeader = (token) => {
    return { Authorization: `Bearer ${token}` };
};

export async function getAllSponsors(token) {
    try {
        const response = await axios.get(`${BASE}/${endpoint}`, {
            headers: getHeader(token),
        });
        return response;
    } catch (error) {
        console.log(error);
        return error.response;
    }
}

export async function getSponsorByID(sponsorID, token) {
    try {
        const response = await axios.get(`${BASE}/${endpoint}?=${sponsorID}`, {
            headers: getHeader(token),
        });
        return response;
    } catch (error) {
        console.log(error);
        return error.response;
    }
}

export async function getSponsorByName(sponsorName, token) {
    try {
        const response = await axios.get(`${BASE}/${endpoint}/${sponsorName}`, {
            headers: getHeader(token),
        });
        return response;
    } catch (error) {
        console.log(error);
        return error.response;
    }
}

export async function createSponsor(token, _name, _image) {
    try {
        const response = await axios.postForm(
        `${BASE}/${endpoint}`,{
            name: _name,
            image: _image
        },
        { headers: {...postHeaderForm , ...getHeader(token)} }
        );
        return response;
    }
    catch (error) {
        console.log(error);
        return error.response;
    }
}

export async function updateSponsor(token, sponsorID, _name, _image) {
    try {
        const response = await axios.putForm(
        `${BASE}/${endpoint}/${sponsorID}`,{
            name: _name,
            image: _image
        },
        { headers: {...postHeaderForm , ...getHeader(token)} }
        );
        return response;
    }
    catch (error) {
        console.log(error);
        return error.response;
    }
}

export async function deleteSponsor(token, sponsorID) {
    try {
        const response = await axios.delete(
        `${BASE}/${endpoint}/${sponsorID}`,
        { headers: {...postHeaderEncoded , ...getHeader(token)} }
        );
        return response;
    }
    catch (error) {
        console.log(error);
        return error.response;
    }
}