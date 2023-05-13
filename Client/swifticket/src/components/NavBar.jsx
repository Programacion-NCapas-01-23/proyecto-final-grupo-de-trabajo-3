import { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import { MdShoppingCart, MdMenu, MdHome, MdSearch } from "react-icons/md";
import ShoppingSideBar from "./ShoppingSideBar";

export const NavBar = () => {
  const [shoppingCart, setShoppingCart] = useState(false);
  const [sidebar, setSidebar] = useState(false);

  return (
    <nav className="p-default bg-default-900 flex justify-between">
      <button
        onClick={() => {
          setSidebar(true);
        }}
        className="px-default"
      >
        <MdMenu fontSize={24} />
      </button>
      <div className="xs:hidden md:grid grid-flow-col">
        <Link className="px-default" to="/">
          <MdHome fontSize={24} />
        </Link>
        <Link className="px-default" to="/admin">
          ADMIN
        </Link>
        <Link className="px-default" to="/user">
          USER
        </Link>
      </div>
      <div className="flex align-middle">
        <Search />
        <button
          onClick={() => {
            setShoppingCart(true);
          }}
          className="px-default"
        >
          <MdShoppingCart fontSize={24} />
        </button>
      </div>
      <ShoppingSideBar open={shoppingCart} setOpen={setShoppingCart} />
    </nav>
  );
};

const Search = () => {
  const searchTarget = useRef(null);
  const [searchClicked, setSearchClicked] = useState(false);
  const [searchFocused, setSearchFocused] = useState(false);
  const [showSearchInput, setShowSearchInput] = useState(false);
  
  const handleOutsideClick = (event) => {
    if (searchTarget.current && !searchTarget.current.contains(event.target)) {
      setShowSearchInput(false);
    }
  };

  useEffect(() => {
    if (showSearchInput && searchTarget.current) {
      searchTarget.current.focus();
    } else if (searchTarget.current) {
      searchTarget.current.blur();
    }
  }, [showSearchInput]);

  useEffect(() => {
    document.addEventListener("mousedown", handleOutsideClick);
    return () => {
      document.removeEventListener("mousedown", handleOutsideClick);
    };
  }, []);

  return (
    <div className="flex align-middle justify-end relative p-default md:w-default-2xl xs:w-44 ">
      <MdSearch
        fontSize={24}
        onClick={() => setShowSearchInput(!showSearchInput)}
      />
      {showSearchInput && (
        <input
          ref={searchTarget}
          className="text-black absolute top-0 translate-y-1/3 left-0 focus:w-full xs:w-1 transition-all rounded-sm p-default-xs appearance-none"
          type="text"
          onFocus={() => {
            setSearchFocused(true);
          }}
          onBlur={() => {
            setSearchFocused(false);
          }}
        />
      )}
    </div>
  );
};
