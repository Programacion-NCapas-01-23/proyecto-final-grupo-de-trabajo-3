import { MdAdminPanelSettings } from "react-icons/md"

export default function AdminActions() {
    return (
        <li className="py-default text-xl">
            <a className="flex items-center md:my-default-xs my-2" href="admin">
                <span className="mr-default-xs"> <MdAdminPanelSettings size={"2rem"}/> </span>
                Go Admin
            </a>
        </li>
    )
}
