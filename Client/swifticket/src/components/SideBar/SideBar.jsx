import { Fragment } from "react";
import { Dialog, Transition } from "@headlessui/react";
import AdminActions from "./components/AdminActions";
import UserActions from "./components/UserActions";
import ModActions from "./components/ModActions"
import CollabActions from "./components/CollabActions";
import { MdAccountCircle, MdClose, MdLogout, MdPerson } from "react-icons/md";


export default function SideBar(props) {


  return (
    <Transition show={props.open} as={Fragment} >
      <Dialog as="div" className="relative z-10" onClose={props.setOpen}>
        {/* ----------- BACKGROUND BLUR --------------- */}
        <Transition.Child
          as={Fragment}
          enter="ease-in-out duration-500"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="ease-in-out duration-500"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <div className="fixed inset-0 bg-black bg-opacity-50 transition-all" />
        </Transition.Child>
        {/* ----------- BACKGROUND BLUR --------------- */}

        {/* ----------- SIDE BAR COMPONENT --------------- */}
        <div className="fixed inset-0 overflow-hidden">
          <div className="absolute inset-0 overflow-hidden">
            <div className="pointer-events-none fixed inset-y-0 left-0 flex max-w-full">
              <Transition.Child
                as={Fragment}
                enter="transform transition ease-in-out duration-500 sm:duration-700"
                enterFrom="-translate-x-full"
                enterTo="translate-x-0"
                leave="transform transition ease-in-out duration-500 sm:duration-700"
                leaveFrom="translate-x-0"
                leaveTo="-translate-x-full"
              >
                <Dialog.Panel className="pointer-events-auto w-screen max-w-md mr-default">
                  <div className="flex h-full flex-col overflow-y-auto bg-black shadow-xl">
                    <div className="overflow-y-auto px-4 py-6 sm:px-6">
                      <Dialog.Title>
                        <p className="subtitle">
                          Swifticket
                          <button onClick={() => props.setOpen(false)} className="absolute right-0 px-default">
                            <MdClose />
                          </button>
                        </p>

                        <div className="flex flex-col items-center md:my-default-sm my-default-xs">
                          <MdAccountCircle size={"12rem"} />
                          <p className="heading-lg"> User Name </p>
                        </div>
                      </Dialog.Title>
                    </div>

                    <div className="px-default-lg flex-1 overflow-y-auto">
                      <ul className="divide-y-2 divide-gray-200">
                        <li className="py-default text-xl">
                          <a className="flex items-center" href="">
                            <span className="mr-default-xs"> <MdPerson size={"2rem"} /> </span>
                            Log In OR My Profile
                          </a>
                        </li>

                        {/* HERE GOES MAPPING OF ROLE's ACTIONS */}

                        <UserActions />
                        <CollabActions />
                        <AdminActions />
                        <ModActions />

                      </ul>
                    </div>

                      {/* THIS SHOULDN'T SHOW UP ON isGuest  */}
                    <div className="m-auto px-4 py-6 sm:px-6">
                      <button className="flex items-center">
                        <span> <MdLogout size={"2rem"} /> </span>
                        <p className="heading-sm px-default"> Log Out</p>
                      </button>
                    </div>

                  </div>
                </Dialog.Panel>
              </Transition.Child>
            </div>
          </div>
        </div>
        {/* ----------- SIDE BAR COMPONENT --------------- */}
      </Dialog>
    </Transition>
  );
}