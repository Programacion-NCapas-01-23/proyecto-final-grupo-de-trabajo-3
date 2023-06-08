export default function FormInput({ label, type, isDatalist, options }) {
    if (type === undefined) type = "text";

    if (!isDatalist) {
        return (
            <div className="mb-default-sm">
                <p className="heading-sm">{label}</p>
                <input
                    className="cursor-pointer bg-opacity-20 bg-white w-full p-default-sm rounded-md"
                    type={type}
                />
            </div>
        );
    } else {
        return (
            <div className="mb-default-sm">
                <p className="heading-sm">{label}</p>
                <select className="bg-opacity-20 cursor-pointer bg-white w-full p-default-sm rounded-md">
                    {options.map((option) => (
                        <option className='text-black' key={option} value={option}>
                            {option}
                        </option>
                    ))}
                </select>
            </div>
        );
    }
}