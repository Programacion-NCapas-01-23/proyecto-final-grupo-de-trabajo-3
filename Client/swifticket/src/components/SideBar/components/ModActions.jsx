import {MdGroups3} from "react-icons/md"
import { useNavigate } from "react-router-dom";


export default function ModActions() {
    
    const navigate = useNavigate();

    const redirectToManageUsers = () => {
      navigate("/admin/tables");
    };

    return (
        <li className="py-default text-xl">
            <a className="flex items-center md:my-default-xs my-2" onClick={redirectToManageUsers}>
                <span className="mr-default-xs"> <MdGroups3 size={"2rem"}/> </span>
                Manage Users
            </a>
        </li>
    )
}
