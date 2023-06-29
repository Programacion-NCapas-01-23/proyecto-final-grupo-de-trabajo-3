import { useRecoilValue } from 'recoil'
import { testUser } from '../../user'
import { MdArrowBack } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';

export default function ChangePassword() {
    const navigate = useNavigate()
    const user = testUser;
    const createdAt = new Date(user.createdAt)

    return (
        <div className='min-h-[calc(100vh-52px-3.5rem)] p-12 flex items-center justify-center'>
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
                                    className="block w-full rounded-md border-0 p-1.5 text-black shadow-sm ring-1 text-sm leading-6"
                                />
                            </div>
                        </div>
                        <button className='col-span-2 col-start-5 bg-sky-900 hover:bg-sky-800 transition-all p-2 rounded-md font-medium text-white'> Update Password </button>
                    </div>

            </div>
            <img src="/src/assets/loginFooter.svg" alt="Footer" className="footer-login" />
        </div>
    )
}
