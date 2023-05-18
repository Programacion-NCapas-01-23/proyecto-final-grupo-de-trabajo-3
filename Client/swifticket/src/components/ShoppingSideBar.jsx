import { Fragment, useState } from "react";
import { Dialog, Transition } from "@headlessui/react";
import { MdClose } from "react-icons/md";
import EventCard from "./EventCards/Shoping/EventCard";

const devEvents = [
  {
    img: "https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/styles/hc_1440x810/public/media/image/2023/02/mandalorian-2964862.jpg?itok=ZMtIO9yv",
    title: "Mando Cosplays",
    place: "Richard Vermont Plaza",
    date_time: new Date("2023-06-16T13:00:00"),
  },
  {
    img: "https://blogthinkbig.com/wp-content/uploads/sites/4/2020/07/Android-Basics-Kotlin.jpg?fit=1500%2C1000",
    title: "Kotlin Dev Convention",
    place: "UCA",
    date_time: new Date("2023-08-23T09:00:00"),
  },
];

export default function ShoppingSideBar(props) {
  
  return (
    <Transition.Root show={props.open} as={Fragment}>
      <Dialog as="div" className="relative z-10" onClose={props.setOpen}>
        <Transition.Child
          as={Fragment}
          enter="ease-in-out duration-500"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="ease-in-out duration-500"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <div className="fixed inset-0 bg-gray-950 bg-opacity-75 transition-opacity" />
        </Transition.Child>

        <div className="fixed inset-0 overflow-hidden">
          <div className="absolute inset-0 overflow-hidden">
            <div className="pointer-events-none fixed inset-y-0 right-0 flex max-w-full pl-10">
              <Transition.Child
                as={Fragment}
                enter="transform transition ease-in-out duration-500 sm:duration-700"
                enterFrom="translate-x-full"
                enterTo="translate-x-0"
                leave="transform transition ease-in-out duration-500 sm:duration-700"
                leaveFrom="translate-x-0"
                leaveTo="translate-x-full"
              >
                <Dialog.Panel className="pointer-events-auto w-screen max-w-md">
                  <div className="flex h-full flex-col overflow-y-scroll bg-black shadow-xl">
                    <div className="flex-1 overflow-y-auto px-4 py-6 sm:px-6">
                      <div className="flex subtitle items-start justify-between">
                        <Dialog.Title className="flex w-full justify-stretch items-center">
                          <div className="mr-default-xs flex-1 border border-primary"/>
                          <p className="">Shopping cart</p>
                          <div className="ml-default-xs flex-1 border border-primary"/>
                        </Dialog.Title>
                        <div className="ml-3 flex h-7 items-center">
                          <button
                            type="button"
                            className="-m-2 p-2 text-gray-400 hover:text-gray-500"
                            onClick={() => props.setOpen(false)}
                          >
                            <span className="sr-only">Close panel</span>
                            <MdClose />
                          </button>
                        </div>
                      </div>

                      <div className="mt-8">
                        <div className="flow-root">
                          <ul
                            role="list"
                          >
                            <li>
                              <EventCard count={1} event={devEvents[0]} />
                            </li>
                            <li>
                              <EventCard count={2} event={devEvents[1]} />
                            </li>
                          </ul>
                        </div>
                      </div>
                    </div>

                    <div className="px-4 py-6 sm:px-6">
                      <div className="mt-6">
                        <a
                          href="/checkout"
                          className="flex items-center justify-center rounded-md bg-primary px-6 py-3 text-3xl hover:bg-primary-700"
                        >
                          Checkout
                        </a>
                      </div>
                      <div>
                        <p className="mt-6 w-full text-sm flex justify-center text-gray-500">
                          or
                          <button
                            type="button"
                            className="font-medium text-primary mx-default-xs hover:text-primary-100"
                            onClick={() => props.setOpen(false)}
                          >
                            Continue Shopping
                            <span aria-hidden="true"> &rarr;</span>
                          </button>
                        </p>
                      </div>
                    </div>
                  </div>
                </Dialog.Panel>
              </Transition.Child>
            </div>
          </div>
        </div>
      </Dialog>
    </Transition.Root>
  );
}
