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
        console.log(response);
        setTicket(response.data);
    }

    useEffect(() => {
        getTicket(token);
        setTimeout(() => setIsLoading(false), 1000);
    }, []);

    return (
        <div className="min-h-[calc(100vh-52px-4rem)] flex justify-center items-center">
            <TicketData isLoading={isLoading} ticket={ticket} />
        </div>
    );
}