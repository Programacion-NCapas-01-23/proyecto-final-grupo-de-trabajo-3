import { useState } from "react";
import { Dialog, Transition } from "@headlessui/react";


export default function SideBar(props) {
  

  return (
    <Dialog as="div" className="relative z-10" open={props.open} onClose={props.setOpen}>
      <Dialog.Panel className="pointer-events-auto bg-black relative w-screen max-w-md">
        <Dialog.Title className="text-base font-semibold leading-6 text-gray-900">
          Panel title
        </Dialog.Title>
        <div className="relative mt-6 flex-1 px-4 sm:px-6">
          {/* Your content */}
        </div>
      </Dialog.Panel>
    </Dialog>
  );
}
