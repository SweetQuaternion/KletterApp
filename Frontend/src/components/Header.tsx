import "../styles/App.css";
import "../styles/Header.css";
import logo from "../assets/react.svg";

function Header() {
  return (
    <header>
      <div className="left-part">
        <img src={logo} />
        <h1>KletterApp</h1>
      </div>
      <div className="right-part">
        <button>Anmelden</button>
        <button>Registrieren</button>
      </div>
    </header>
  );
}

export default Header;
