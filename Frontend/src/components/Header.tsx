import "../styles/App.css";
import "../styles/Header.css";
import logo from "../assets/react.svg";
import defaultpic from "../assets/default-pic.png";
// import { Link } from "react-router";
import type { User } from "../constants/APIResponseTypes";
import {
  // keycloak,
  login,
  register,
  logout,
} from "../keycloak";

interface Props {
  user: User | null;
}

function Header({ user }: Props) {
  return (
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
          <button className="profile-button" onClick={() => logout()}>
            <img src={user.bildUrl || defaultpic} alt="Profil" />
          </button>
        </div>
      )}
    </header>
  );
}

export default Header;
