import { useRecoilValue } from 'recoil'
import { testUser } from '../../user'
import { MdArrowBack } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';

export default function Avatars() {
    const navigate = useNavigate()
    const user = testUser;


    return (
        <div className='min-h-[calc(100vh-52px-3.5rem)] p-12 flex flex-col items-center justify-center'>    
            <h5 className="mb-5 sm:text-5xl text-3xl leading-10 text-right font-bold tracking-wider text-white"> Select your new avatar </h5>
            <div className="sm:px-32 sm:py-24 px-12 py-6 bg-default-900 rounded-lg shadow-2xl relative h-fit max-w-5xl flex flex-col gap-2 sm:gap-8">
                <MdArrowBack onClick={() => { navigate(-1) }} className='absolute cursor-pointer left-0 top-0 text-8xl sm:p-6 p-8 sm:m-0 -mt-3 -ml-3' />
                <div className='grid sm:grid-cols-3 grid-cols-2 gap-x-16 gap-y-5'>
                    <img className='hover:scale-110 transition-all cursor-pointer hover:shadow-md hover:shadow-white rounded-full' src={`/src/assets/avatar1.png`} alt="avatar" />
                    <img className='hover:scale-110 transition-all cursor-pointer hover:shadow-md hover:shadow-white rounded-full' src={`/src/assets/avatar2.png`} alt="avatar" />
                    <img className='hover:scale-110 transition-all cursor-pointer hover:shadow-md hover:shadow-white rounded-full' src={`/src/assets/avatar3.png`} alt="avatar" />
                    <img className='hover:scale-110 transition-all cursor-pointer hover:shadow-md hover:shadow-white rounded-full' src={`/src/assets/avatar4.png`} alt="avatar" />
                    <img className='hover:scale-110 transition-all cursor-pointer hover:shadow-md hover:shadow-white rounded-full' src={`/src/assets/avatar5.png`} alt="avatar" />
                    <img className='hover:scale-110 transition-all cursor-pointer hover:shadow-md hover:shadow-white rounded-full' src={`/src/assets/avatar6.png`} alt="avatar" />
                </div>
            </div>
            <img src="/src/assets/loginFooter.svg" alt="Footer" className="footer-login" />
        </div>
    )
}
