import { atom } from "recoil";

export const guestState = atom({
    key: 'guestState',
    default: JSON.parse(localStorage.getItem('guest'))
});