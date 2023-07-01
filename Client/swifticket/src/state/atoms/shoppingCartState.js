import { atom } from "recoil";

export const shoppingCartState = atom({
    key: 'ShoppingCart',
    default: JSON.parse(sessionStorage.getItem('shoppingCart')) ? JSON.parse(sessionStorage.getItem('shoppingCart')) : [],
})
