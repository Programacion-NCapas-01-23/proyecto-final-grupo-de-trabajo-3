import React from 'react';

export const Pagination = ({ page, setPage, limit }) => {
  return (
    <div className="flex gap-4">
      <button
        onClick={() => setPage(page - 1)}
        className={`${
          page <= 0 ? 'hidden' : 'block'
        } bg-primary px-4 py-2 rounded-md`}
        disabled={page <= 0}
      >
        {'<'}
      </button>
      <button className="bg-primary px-4 py-2 rounded-md">{page + 1}</button>
      <button
        onClick={() => setPage(page + 1)}
        className={`${
          page + 1 >= limit ? 'hidden' : 'block'
        } bg-primary px-4 py-2 rounded-md`}
        disabled={page + 1 >= limit}
      >
        {'>'}
      </button>
    </div>
  );
};
