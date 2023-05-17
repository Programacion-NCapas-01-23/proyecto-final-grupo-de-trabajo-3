import LinnedText from "./LinnedText";

export default function EventInfo() {
  return (
    <div className="relative pl-default-lg pr-default-xl">
      <span className="w-full h-full z-0 right-0 absolute bg-secondary opacity-30 rounded-r-2xl" />
      <div className="w-full relative py-default">
        <h1 className="md:title subtitle">Title</h1>
        <div>
            <LinnedText text={"Some Text 1"} />
            <LinnedText text={"Some Text 2"} />
        </div>
      </div>
    </div>
  );
}
