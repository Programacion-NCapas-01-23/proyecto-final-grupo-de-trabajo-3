import axios from "axios";
import BASE from "./BASE";

const endpoint = "categories";
const postHeaderEncoded = { "Content-Type": "application/x-www-form-urlencoded" };
const postHeaderForm = { "Content-Type": "multipart/form-data" };
const getHeader = (token) => {
    return { Authorization: `Bearer ${token}` };
}; 

export async function getCategories(token){
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
