import { atom } from "recoil";

export const userState = atom({
    key: 'userState',
    default: JSON.parse(localStorage.getItem('auth_user'))
});