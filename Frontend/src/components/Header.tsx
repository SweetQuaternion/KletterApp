import "../styles/App.css";
import "../styles/Header.css";
import logo from "../assets/react.svg";
import defaultpic from "../assets/default-pic.png";
import type { User } from "../constants/APIResponseTypes";
import { login, register, logout } from "../constants/keycloak";
import { useEffect, useState } from "react";
import { Link } from "react-router";

interface Props {
  user: User | null;
}

function Header({ user }: Props) {
  const [menuToggled, setMenuToggled] = useState(false);

  useEffect(() => {
    const handler = () => setMenuToggled(false);
    document.addEventListener("click", handler);
    return () => document.removeEventListener("click", handler);
  }, []);

  return (
    <>
      <header>
        <div className="left-part">
          <img src={logo} />
          <h1>KletterApp</h1>
        </div>
        {!user && (
          <div className="right-part">
            <button onClick={() => login()}>Anmelden</button>
            <button onClick={() => register()}>Registrieren</button>
          </div>
        )}
        {user && (
          <div className="right-part">
            <span>Hallo {user.name}</span>
            <button
              className="profile-button"
              onClick={(e) => {
                e.stopPropagation();
                setMenuToggled(!menuToggled);
              }}
            >
              <img src={user.bildUrl || defaultpic} alt="Profil" />
            </button>
          </div>
        )}
      </header>
      {menuToggled && (
        <div className="profile-menu">
          <Link to="/profil">
            <button>Mein Profil</button>
          </Link>
          <button onClick={() => logout()}>Abmelden</button>
        </div>
      )}
    </>
  );
}

export default Header;
