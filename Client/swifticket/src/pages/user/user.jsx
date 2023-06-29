import { useState } from 'react';
import loginFooter from '../../assets/loginFooter.svg';
import UserProfile from './UserProfile/UserProfile';

export const testUser = {
	"id": "9ec2e152-05b4-4628-8c15-a265f1d8ebdc",
	"name": "Super Admin",
	"email": "dsolismarroquin@gmail.com",
	"state": {
		"id": 1,
		"name": "Activo"
	},
	"avatar": {
		"id": 1,
		"image": "avatar1.jpg"
	},
	"roles": [
		{
			"id": 5,
			"name": "Super-admin"
		}
	],
	"createdAt": "2023-06-26T16:30:13.957+00:00"
}


const User = () => {
  const [user, setUser] = useState(testUser)
  const avatar = user.avatar.image.split(".")[0] + ".png";

  return (
    <>
      <div className="flex justify-center items-center w-screen h-screen">
        <div className="flex flex-col justify-evenly items-center h-full sm:h-3/4 w-11/12 md:w-2/5 bg-transparent sm:bg-secondary sm:bg-opacity-50 rounded-[2rem]">
          <div className="flex flex-col justify-center items-center h-2/6 sm:h-[45%]">
            <img src={`/src/assets/${avatar}`} alt="User" style={{ height: '20vh' }} />
            <p className="subtitle"> {user.name} </p>
          </div>
         <UserProfile />
        </div>
      </div>
      <img src={loginFooter} alt="Footer" className="footer-login" />
    </>
  );
};

export default User;
