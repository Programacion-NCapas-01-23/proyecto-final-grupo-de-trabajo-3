import { atom } from "recoil";

export const tokenState = atom({
    key: 'tokenState',
    default: JSON.parse(localStorage.getItem('auth_token'))
});