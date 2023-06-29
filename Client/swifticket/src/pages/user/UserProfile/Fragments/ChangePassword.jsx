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
            <div className="sm:p-32 p-12 sm:pl-20 bg-default-900 rounded-lg shadow-2xl relative h-fit max-w-5xl flex flex-col gap-2 sm:gap-8">
                <MdArrowBack onClick={() => {navigate(-1)}} className='absolute cursor-pointer left-0 top-0 text-8xl sm:p-6 p-8 sm:m-0 -mt-3 -ml-3' />
                <h5 className="mb-5 sm:text-5xl text-3xl leading-10 text-right font-bold tracking-wider text-white"> {user.name}'s Account Information</h5>
                <p className="text-primary sm:text-xl text-lg "> User Avatar ID <br /> <span className='font-normal text-white'> {user.avatar.id} </span> </p>
                <p className="text-primary sm:text-xl text-lg "> Email <br /> <span className='font-normal text-white'> {user.email} </span> </p>
                <p className="text-primary sm:text-xl text-lg "> Created At <br /> <span className='font-normal text-white'> {createdAt.toLocaleString('en-US', { day: 'numeric', month: 'long', hour: 'numeric', minute: '2-digit', hour12: true })} </span></p>
            </div>
            <img src="/src/assets/loginFooter.svg" alt="Footer" className="footer-login" />
        </div>
    )
}
