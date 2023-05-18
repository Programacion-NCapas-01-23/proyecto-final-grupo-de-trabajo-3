import {MdGroups3} from "react-icons/md"


export default function ModActions() {
    return (
        <li className="py-default text-xl">
            <a className="flex items-center md:my-default-xs my-2" href="">
                <span className="mr-default-xs"> <MdGroups3 size={"2rem"}/> </span>
                Manage Users
            </a>
        </li>
    )
}
