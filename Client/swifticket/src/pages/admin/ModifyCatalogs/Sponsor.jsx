import { MdCameraAlt } from "react-icons/md";
import FormInput from "../components/FormInput";
import TitileWithLines from "../components/TitleWithLines";

export default function Sponsor() {
    return (
        <section className="w-full">
            <TitileWithLines title="Sponsor" />

            <div className="w-3/5 m-auto flex flex-col">
                <div className="w-1/2 self-center"> <FormInput label="Name" /> </div>
                <button className='w-96 h-96 flex self-center items-center justify-center bg-slate-500 bg-opacity-30 rounded-2xl text-8xl hover:text-9xl hover:bg-gray-200 hover:bg-opacity-20 transition-all'>
                    <MdCameraAlt />
                </button>
                <div className="flex justify-evenly mt-default-sm">
                    <button className="subaction-button w-fit self-end"> Cancel </button>
                    <button className="action-button w-fit self-end"> Save </button>
                </div>
            </div>

        </section>
    )
}
