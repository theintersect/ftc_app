import React from "react";
import ConnectionLabel from "./connectionLabel.jsx";

const NavBar = props => {
  const { connected } = props;
  return (
    <nav className="navbar navbar-expand-sm navbar-light border-bottom">
      <span className="navbar-brand">
        <img src="/logo.png" height={35} alt="" />
      </span>
      <ConnectionLabel connected={connected} />
    </nav>
  );
};

export default NavBar;
