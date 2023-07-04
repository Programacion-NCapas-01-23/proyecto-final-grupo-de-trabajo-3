export default function FormInput({
  label,
  type,
  isDatalist,
  options,
  value,
  onInputChange,
}) {
  if (type === undefined) type = 'text';

  const handleChange = (event) => {
    onInputChange(event.target.value);
  };

  if (!isDatalist) {
    return (
      <div className="mb-default-sm">
        <p className="heading-sm">{label}</p>
        <input
          className="cursor-pointer bg-opacity-20 bg-white w-full p-default-sm rounded-md"
          type={type}
          value={value}
          onChange={handleChange}
        />
      </div>
    );
  } else {
    return (
      <div className="mb-default-sm">
        <p className="heading-sm">{label}</p>
        <select
          className="bg-opacity-20 cursor-pointer bg-white w-full p-default-sm rounded-md"
          value={value}
          onChange={handleChange}
        >
          {options.map((option, index) => (
            <option className="text-black" key={index} value={option}>
              {' '}
              {option}{' '}
            </option>
          ))}
        </select>
      </div>
    );
  }
}
