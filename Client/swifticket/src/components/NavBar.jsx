import React, { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { MdShoppingCart, MdMenu, MdHome, MdSearch } from "react-icons/md";
import ShoppingSideBar from "./ShoppingSideBar";
import SideBar from "./SideBar/SideBar";

const NavBar = () => {
  const [shoppingCart, setShoppingCart] = useState(false);
  const [sidebar, setSidebar] = useState(false);

  return (
    <nav className="px-default bg-default-900 flex justify-between items-center w-full fixed z-10">
      <div className="flex">
        <button
          onClick={() => {
            setSidebar(true);
          }}
          className="px-default"
        >
          <MdMenu fontSize={24} />
        </button>
        <Link className="ml-default-xs" to="/">
          <MdHome fontSize={24} />
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
      <SideBar open={sidebar} setOpen={setSidebar} />
      <ShoppingSideBar open={shoppingCart} setOpen={setShoppingCart} />
    </nav>
  );
};

const Search = () => {
  const searchTarget = useRef(null);
  const navigate = useNavigate();
  const [searchValue, setSearchValue] = useState("");
  const [showSearchInput, setShowSearchInput] = useState(false);

  const handleOutsideClick = (event) => {
    if (
      searchTarget.current &&
      !searchTarget.current.contains(event.target) &&
      event.target.tagName !== "BUTTON"
    ) {
      setShowSearchInput(false);
      setSearchValue("");
    }
  };

  const handleSearchEnter = (event) => {
    if (event.key === "Enter") navigate(`/search?eventName=${searchValue}`);
  };

  const handleSearch = () => {
    navigate(`/search?title=${searchValue}`);
    setShowSearchInput(false);
    setSearchValue("");
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
          value={searchValue}
          onChange={(e) => {
            setSearchValue(e.target.value);
          }}
          onKeyDown={handleSearchEnter}
          className="text-black absolute top-0 translate-y-1/3 left-0 focus:w-full xs:w-1 transition-all rounded-sm p-default-xs appearance-none"
          type="text"
        />
      )}
      {showSearchInput && (
        <button
          className="absolute -right-16 top-1/2 -translate-y-1/2 z-30 bg-primary rounded-sm px-2 py-1"
          onClick={handleSearch}
        >
          Search
        </button>
      )}
    </div>
  );
};

export default NavBar;
