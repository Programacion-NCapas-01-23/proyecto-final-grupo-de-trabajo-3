import {MdConfirmationNumber, MdCallReceived} from "react-icons/md"

export default function UserActions() {
    return (
        <li className="py-default text-xl">
            <a className="flex items-center md:my-default-xs my-2" href="user/owned-tickets">
                <span className="mr-default-xs"> <MdConfirmationNumber size={"2rem"}/> </span>
                My Tickets
            </a>
            <a className="flex items-center md:my-default-xs my-2" href="">
                <span className="mr-default-xs"> <MdCallReceived size={"2rem"}/> </span>
                Receive a QR
            </a>
        </li>
    )
}
