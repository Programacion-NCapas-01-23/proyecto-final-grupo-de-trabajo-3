import {MdQrCodeScanner} from "react-icons/md"

export default function CollabActions() {
    return (
        <li className="py-default text-xl">
            <a className="flex items-center md:my-default-xs my-2" href="">
                <span className="mr-default-xs"> <MdQrCodeScanner size={"2rem"}/> </span>
                Scan QR
            </a>
        </li>
    )
}
