import { useNavigate } from "react-router-dom";
import { Fragment } from "react";
import { Dialog, Transition } from "@headlessui/react";
import { MdClose } from "react-icons/md";
import EventCardSh from "./EventCards/Shoping/EventCardSh";
import { shoppingCartState } from "../state/atoms/shoppingCartState";
import { useRecoilValue } from "recoil";

export default function ShoppingSideBar(props) {
  const shoppingCart = useRecoilValue(shoppingCartState);
  const navigate = useNavigate();

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
                  <div className="flex h-full flex-col overflow-y-auto bg-black shadow-xl">
                    <div className="flex-1 overflow-y-auto px-4 py-6 sm:px-6">
                      <div className="flex subtitle items-start justify-between">
                        <Dialog.Title className="flex w-full justify-stretch items-center">
                          <div className="mr-default-xs flex-1 border border-primary" />
                          <p className="">Shopping cart</p>
                          <div className="ml-default-xs flex-1 border border-primary" />
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
                        {shoppingCart && shoppingCart.length > 0 ? (
                          <div className="flow-root">
                            <ul role="list">
                              {shoppingCart.map((event, index) => (
                                <li key={index}>
                                  <EventCardSh  
                                    event={event}
                                  />
                                </li>
                              ))}
                            </ul>
                          </div>
                        ) : (
                          <EmptyShoppingCart />
                        )}
                      </div>
                    </div>

                    {shoppingCart && shoppingCart.length > 0 ? (
                      <div className="px-4 py-6 sm:px-6">
                        <div className="mt-6">
                          <a
                            onClick={() => {navigate('/checkout')}}
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
                    ) : (
                      <div className="px-4 py-6 sm:px-6">

                        <div className="mt-6">
                          <a
                            href="/"
                            className="flex items-center justify-center rounded-md bg-primary px-6 py-3 text-3xl hover:bg-primary-700"
                          >
                            Buy some tickets!
                          </a>
                        </div>
                      </div>
                    )}
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

function EmptyShoppingCart() {
  return (
    <div className="flex flex-col h-full w-full gap-3 items-center align-middle">
      <img className="w-1/2" src="/assets/sad.png" alt="" />
      <p className="text-3xl tracking-wide leading-7 font-bold">Oh no...</p>
      <p className="p-default">
        It looks like your cart it's empty. Tickets you add to your shoping cart
        will show up here!{" "}
      </p>
    </div>
  );
}
