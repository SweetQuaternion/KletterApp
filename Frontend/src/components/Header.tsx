import "../styles/App.css";
import "../styles/Header.css";
import logo from "../assets/react.svg";
import defaultpic from "../assets/default-pic.png";
import { Link } from "react-router";
import type { User } from "../constants/APIResponseTypes";

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
          <Link to="/login">
            <button>Anmelden</button>
          </Link>
          <Link to="/signup">
            <button>Registrieren</button>
          </Link>
        </div>
      )}
      {user && (
        <div className="right-part">
          <span>Hallo, {user.name}!</span>
          <button className="profile-button">
            <img src={defaultpic} alt="Profil" />
          </button>
        </div>
      )}
    </header>
  );
}

export default Header;
