import { MdQrCodeScanner } from "react-icons/md";
import { useNavigate } from "react-router-dom";

export default function CollabActions() {
    
    const navigate = useNavigate();

    const redirectToScanQR = () => {
      navigate("/scan-qr");
    };

    return (
        <li className="py-default text-xl">
            <a className="flex items-center md:my-default-xs my-2" onClick={redirectToScanQR}>
                <span className="mr-default-xs"> <MdQrCodeScanner size={"2rem"}/> </span>
                Scan QR
            </a>
        </li>
    )
}
