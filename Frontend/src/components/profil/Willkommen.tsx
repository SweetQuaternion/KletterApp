import { Link } from "react-router";
import "../../styles/App.css";

function Willkommen() {
  return (
    <>
      <div className="white-box center">
        <h2>Willkommen bei der KletterApp!</h2>
        <p>Du bist nun erfolgreich registriert.</p>
        <div className="centering">
          <div className="button-container">
            <Link to="/profile">
              <button>Mein Profil</button>
            </Link>
            <Link to="/hallensuche5%">
              <button>Startseite</button>
            </Link>
          </div>
        </div>
      </div>
    </>
  );
}

export default Willkommen;
