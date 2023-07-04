import { useParams } from "react-router";
import { getTicketByID } from "../../services/TicketServices";
import TicketData from "./components/TicketData";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { tokenState } from "../../state/atoms/tokenState";

export function Ticket() {
    const [isLoading, setIsLoading] = useState(true);
    const [ticket, setTicket] = useState(null);
    const token = useRecoilValue(tokenState);
    const { id } = useParams();

    const getTicket = async () => {
        let response = await getTicketByID(token, id);
        setTimeout(() => setIsLoading(false), 600);
        console.log(response);
        setTicket(response.data);
    }

    useEffect(() => {
        getTicket(token);
    }, []);

    return (
        <div className="min-h-[calc(100vh-52px-3.5rem)] flex justify-center items-center overflow-hidden">
            <TicketData isLoading={isLoading} ticket={ticket} />
        </div>
    );
}