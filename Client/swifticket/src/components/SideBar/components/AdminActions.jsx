import {MdGroups, MdCalendarMonth, MdImportContacts, MdBarChart} from "react-icons/md"

export default function AdminActions() {
    return (
        <li className="py-default text-xl">
            <a className="flex items-center md:my-default-xs my-2" href="">
                <span className="mr-default-xs"> <MdGroups size={"2rem"}/> </span>
                All Users
            </a>
            <a className="flex items-center md:my-default-xs my-2" href="">
                <span className="mr-default-xs"> <MdCalendarMonth size={"2rem"}/> </span>
                Create Event
            </a>
            <a className="flex items-center md:my-default-xs my-2" href="">
                <span className="mr-default-xs"> <MdBarChart size={"2rem"}/> </span>
                Stats
            </a>
            <a className="flex items-center md:my-default-xs my-2" href="">
                <span className="mr-default-xs"> <MdImportContacts size={"2rem"}/> </span>
                Catalogs
            </a>
        </li>
    )
}
