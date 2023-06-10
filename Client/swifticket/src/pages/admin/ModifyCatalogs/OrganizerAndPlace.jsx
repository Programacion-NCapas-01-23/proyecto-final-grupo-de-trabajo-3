import FormInput from "../components/FormInput";
import TitileWithLines from "../components/TitleWithLines";

export default function OrganizerAndPlace() {
    return (
        <section className="w-full">
            <TitileWithLines title="Place" />
            <section className="w-3/5 m-auto grid grid-cols-5">
                <div className="col-span-3"> <FormInput label={"Name"} /> </div>
                <div className="col-span-3"> <FormInput  label={"Address"} /> </div>
                <button className="action-button h-fit self-center col-start-5"> ADD </button>
            </section>
            <TitileWithLines title="Organizer" />
            <section className="w-3/5 m-auto grid grid-cols-5">
                <div className="col-span-3"> <FormInput label={"Name"} /> </div>
                <button className="action-button h-fit self-center col-start-5"> ADD </button>
            </section>
        </section>
    )
}
