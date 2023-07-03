import { useRecoilValue } from 'recoil'
import { MdArrowBack } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { tokenState } from '../../../../state/atoms/tokenState';
import { changePass } from '../../../../services/User.Services';
import { Toaster, toast } from 'react-hot-toast';

export default function ChangePassword() {
    const navigate = useNavigate()
    const [formData, setFormData] = useState({
        password: '',
        newPassword: '',
    });
    const token = useRecoilValue(tokenState)

    const handleUser = async () => {
        let response = await changePass(token, formData.password, formData.newPassword)

        console.log(response);

        if (response.status == 200) {
            toast.success("Password Updated")
            setTimeout(() => {
                navigate('/user')
            }, 2000);
        }
        else {
            if (response.data.password)
                response.data.password.forEach((element) => {
                    toast.error(element)
                });
            if (response.data.newPassword)
                response.data.newPassword.forEach((element) => {
                    toast.error(element)
                });
            toast.error(response.data.message)
        }
    }

    return (
        <div className='min-h-[calc(100vh-52px-3.5rem)] p-12 flex items-center justify-center'>
            <Toaster position='top-right' />
            <div className="sm:p-32 p-12 bg-default-900 rounded-lg shadow-2xl relative h-fit max-w-5xl flex flex-col gap-2 sm:gap-8">
                <MdArrowBack onClick={() => { navigate(-1) }} className='absolute cursor-pointer left-0 top-0 text-8xl sm:p-6 p-8 sm:m-0 -mt-3 -ml-3' />
                <h5 className="mb-5 sm:text-5xl text-3xl leading-10 text-right font-bold tracking-wider text-white"> Change your password</h5>
                <div className="grid grid-cols-6 gap-x-6 gap-y-10">
                    <div className="col-span-6">
                        <label htmlFor="old-password" className="block text-lg font-medium leading-6">
                            Old Password
                        </label>
                        <div className="mt-2">
                            <input
                                type="text"
                                name="old-password"
                                id="old-password"
                                autoComplete="off"
                                value={formData.password}
                                onChange={(e) => { setFormData({ ...formData, password: e.target.value }) }}
                                className="block w-full rounded-md border-0 p-1.5 text-black shadow-sm ring-1 text-lg leading-6"
                            />
                        </div>
                    </div>
                    <div className="col-span-6">
                        <label htmlFor="new-password" className="block text-lg font-medium leading-6">
                            New Password
                        </label>
                        <div className="mt-2">
                            <input
                                type="text"
                                name="new-password"
                                id="new-password"
                                autoComplete="off"
                                value={formData.newPassword}
                                onChange={(e) => { setFormData({ ...formData, newPassword: e.target.value }) }}
                                className="block w-full rounded-md border-0 p-1.5 text-black shadow-sm ring-1 text-lg leading-6"
                            />
                        </div>
                    </div>
                    <button onClick={handleUser} className='col-span-2 col-start-5 bg-sky-900 hover:bg-sky-800 transition-all p-2 rounded-md font-medium text-white'> Update Password </button>
                </div>

            </div>
            <img src="/assets/loginFooter.svg" alt="Footer" className="footer-login" />
        </div>
    )
}
