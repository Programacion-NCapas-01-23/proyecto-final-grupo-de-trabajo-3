import { useRecoilValue } from 'recoil'
import { MdArrowBack } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';
import { Toaster, toast } from 'react-hot-toast';
import { updateUser } from '../../../../services/User.Services';
import { useEffect, useState } from 'react';
import { tokenState } from '../../../../state/atoms/tokenState';
import { testUser } from '../../user';
import { validateToken } from '../../../../services/Auth.Services';

export default function AccountInfo() {

    const navigate = useNavigate()
    const [formData, setFormData] = useState({
        name: '',
        avatar: 1,
    });
    const [user, setUser] = useState(testUser)
    const token = useRecoilValue(tokenState)

    useEffect(() => {
        const fetchUser = async (token) => {
            const response = await validateToken(token);
            setUser(response.data)
        }
        fetchUser(token)
    }, [])

    const handleInputChange = (e) => {
        setFormData({ ...formData, name: e.target.value })
    };

    function camelize(str) {
        return str.replace(/(?:^\w|[A-Z]|\b\w)/g, function (word, index) {
            return index === 0 ? word.toUpperCase() : word.toLowerCase();
        }).replace(/\s+/g, ' ');
    }

    const handleUser = async () => {
        setFormData({ ...formData, avatar: user.avatar.id })
        let response = await updateUser(formData.name, formData.avatar, token)

        console.log(response);

        if (response.status == 200){
            toast.success("User Updated")
            setTimeout(() => {
                navigate('/user')
            }, 2000);
        }
        else {
            if (response.data.name)
                response.data.name.forEach((element) => {
                    toast.error(element)
                });
        }

        setFormData({ name: '', avatar: 1 })
    }

    return (
        <div className='min-h-[calc(100vh-52px-3.5rem)] p-12 flex items-center justify-center'>
            <div className="sm:p-32 p-12 sm:pl-20 bg-default-900 rounded-lg shadow-2xl relative h-fit max-w-5xl flex flex-col gap-2 sm:gap-8">
                <MdArrowBack onClick={() => { navigate(-1) }} className='absolute cursor-pointer left-0 top-0 text-8xl sm:p-6 p-8 sm:m-0 -mt-3 -ml-3' />
                <h5 className="mb-5 sm:text-5xl text-3xl leading-10 text-right font-bold tracking-wider text-white"> {camelize(user.name.split(' ')[0])}'s Account Information</h5>
                <div className='flex'>
                    <div className='w-full'>
                        <p className="text-primary sm:text-xl text-lg tracking-tight"> Username <br /> <span className='font-normal text-white'> {user.name} </span> </p>
                        <p className="text-primary sm:text-xl text-lg tracking-tight"> User Avatar ID <br /> <span className='font-normal text-white'> {user.avatar.id} </span> </p>
                        <p className="text-primary sm:text-xl text-lg tracking-tight"> Email <br /> <span className='font-normal text-white'> {user.email} </span> </p>
                        <p className="text-primary sm:text-xl text-lg tracking-tight"> Created At <br /> <span className='font-normal text-white'> {new Date(user.createdAt).toLocaleString('en-US', { day: 'numeric', month: 'long', hour: 'numeric', minute: '2-digit', hour12: true })} </span></p>
                    </div>
                    <div className='w-full'>
                        <div className="grid grid-cols-6 gap-x-6 gap-y-10">
                            <div className="col-span-6">
                                <label htmlFor="new-username" className="block text-lg font-medium leading-6">
                                    New Username
                                </label>
                                <div className="mt-2">
                                    <input
                                        type="text"
                                        placeholder='The desired new username'
                                        name="new-username"
                                        id="new-username"
                                        autoComplete="off"
                                        onChange={handleInputChange}
                                        value={formData.name}
                                        className="block w-full rounded-md border-0 p-1.5 text-black shadow-sm ring-1 text-sm leading-6"
                                    />
                                </div>
                            </div>
                            <button onClick={handleUser} className='col-span-3 col-start-4 bg-sky-900 hover:bg-sky-800 transition-all p-2 rounded-md font-medium text-white'> Update Username </button>
                        </div>
                    </div>
                </div>
            </div>
            <img src="/src/assets/loginFooter.svg" alt="Footer" className="footer-login" />
            <Toaster position='top-right' />
        </div>
    )
}
