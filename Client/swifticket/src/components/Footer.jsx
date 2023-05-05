import React from "react";
import { Link } from "react-router-dom";

export default function Footer() {
  return (
    <footer>
      <p>FOOTER</p>
      <Link to="/login"> LOG OUT</Link>
    </footer>
  );
}
