import { atom } from 'recoil';

export const roleState = atom({
  key: 'roleState',
  default: JSON.parse(localStorage.getItem('roles')),
});
