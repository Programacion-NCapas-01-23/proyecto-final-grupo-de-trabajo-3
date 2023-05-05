import { Link, Route } from "react-router-dom"
import OneUser from "./id/oneUser"

export default function User(){
    return(
        <>
            <p>SOME USER INFORMATION</p>
            <p> GO TO <Link to='/user/one'> ONE USER </Link></p>
        </>
    )
}