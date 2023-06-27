import axios from "axios";
import BASE from "./BASE";

const endpoint = "categories";
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
