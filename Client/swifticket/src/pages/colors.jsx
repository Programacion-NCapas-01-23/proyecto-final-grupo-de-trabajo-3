import React from 'react';

export const Colors = () => {
  return (
    <section className="grid grid-cols-2 p-default-lg text-center space-y-4">
      <section className="grid grid-cols-1 p-default-lg text-center space-y-4">
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> primary-100</div>
          <div className="p-default-lg border rounded-full w-fit bg-primary-100 h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> primary-400</div>
          <div className="p-default-lg border rounded-full w-fit bg-primary-400 h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> primary</div>
          <div className="p-default-lg border rounded-full w-fit bg-primary h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> primary-700</div>
          <div className="p-default-lg border rounded-full w-fit bg-primary-700 h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> primary-900</div>
          <div className="p-default-lg border rounded-full w-fit bg-primary-900 h-fit"></div>
        </div>
      </section>
      <section className="grid grid-cols-1 p-default-lg text-center space-y-4">
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> secondary-100</div>
          <div className="p-default-lg border rounded-full w-fit bg-secondary-100 h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> secondary-400</div>
          <div className="p-default-lg border rounded-full w-fit bg-secondary-400 h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> secondary</div>
          <div className="p-default-lg border rounded-full w-fit bg-secondary h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> secondary-700</div>
          <div className="p-default-lg border rounded-full w-fit bg-secondary-700 h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> secondary-900</div>
          <div className="p-default-lg border rounded-full w-fit bg-secondary-900 h-fit"></div>
        </div>
      </section>
      <section className="grid grid-cols-1 p-default-lg text-center space-y-4">
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> default-400</div>
          <div className="p-default-lg border rounded-full w-fit bg-default-400 h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> default</div>
          <div className="p-default-lg border rounded-full w-fit bg-default h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> default-900</div>
          <div className="p-default-lg border rounded-full w-fit bg-default-900 h-fit"></div>
        </div>
      </section>
      <section className="grid grid-cols-1 p-default-lg text-center space-y-4">
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> neutral-100</div>
          <div className="p-default-lg border rounded-full w-fit bg-neutral-100 h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> neutral-400</div>
          <div className="p-default-lg border rounded-full w-fit bg-neutral-400 h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> neutral-700</div>
          <div className="p-default-lg border rounded-full w-fit bg-neutral-700 h-fit"></div>
        </div>
        <div className="grid grid-cols-2 space-x-10">
          <div className="p-default w-fit justify-self-end"> neutral-900</div>
          <div className="p-default-lg border rounded-full w-fit bg-neutral-900 h-fit"></div>
        </div>
      </section>
      <section className="grid grid-cols-1 col-span-2 p-default-lg text-center space-y-4">
        <p className=" border rounded-md w-fit justify-self-center p-default title">
          {' '}
          TITLE{' '}
        </p>
        <p className=" border rounded-md w-fit justify-self-center p-default subtitle">
          {' '}
          SUBTITLE{' '}
        </p>
        <p className=" border rounded-md w-fit justify-self-center p-default heading-xl">
          {' '}
          EXTRA LARGE HEADING{' '}
        </p>
        <p className=" border rounded-md w-fit justify-self-center p-default heading-lg">
          {' '}
          LARGE HEADING{' '}
        </p>
        <p className=" border rounded-md w-fit justify-self-center p-default heading-md">
          {' '}
          MEDIUM HEADING{' '}
        </p>
        <p className=" border rounded-md w-fit justify-self-center p-default heading-sm">
          {' '}
          SMALL HEADING{' '}
        </p>
        <p className=" border rounded-md w-fit justify-self-center p-default">
          {' '}
          BODY{' '}
        </p>
      </section>
    </section>
  );
};
