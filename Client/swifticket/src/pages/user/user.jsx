import { useEffect, useState } from 'react';
import loginFooter from '/assets/loginFooter.svg';
import UserProfile from './UserProfile/UserProfile';
import { validateToken } from '../../services/Auth.Services';
import { useRecoilValue } from 'recoil';
import { tokenState } from '../../state/atoms/tokenState';

export const testUser = {
	"id": "",
	"name": "Name",
	"email": "Email",
	"state": {
		"id": 1,
		"name": "Activo"
	},
	"avatar": {
		"id": 1,
		"image": "avatar0.jpg"
	},
	"roles": [
		{
			"id": 2,
			"name": "Usuario"
		}
	],
	"createdAt": "2020-06-26T16:30:13.957+00:00"
}


const User = () => {

	const [user, setUser] = useState(testUser)
	const token = useRecoilValue(tokenState)

	useEffect(() => {
		const fetchUser = async (token) => {
			const response = await validateToken(token);
			setUser(response.data)
		}
		fetchUser(token)
	}, [])


	const avatar = "assets/" + user.avatar.image.split(".")[0] + ".png";

	return (
		<>
			<div className="flex justify-center items-center w-screen min-h-[calc(100vh-52px-3.5rem)]">
				<div className="flex flex-col justify-evenly py-16 items-center h-full sm:h-3/4 w-11/12 md:w-2/5 bg-transparent sm:bg-secondary sm:bg-opacity-50 rounded-[2rem]">
					<div className="flex flex-col mb-default-xs justify-center items-center h-2/6 sm:h-[45%]">
						<img src={avatar} alt="User" />
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