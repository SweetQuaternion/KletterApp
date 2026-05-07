import { Link } from "react-router";
import "../../styles/App.css";

function Willkommen() {
  return (
    <>
      <section className="white-box center">
        <h2>Willkommen bei der KletterApp!</h2>
        <p>Du bist nun erfolgreich registriert.</p>
        <div className="centering">
          <div className="button-container">
            <Link to="/profil" className="red-button">
              Mein Profil
            </Link>
            <Link to="/hallensuche" className="red-button">
              Startseite
            </Link>
          </div>
        </div>
      </section>
    </>
  );
}

export default Willkommen;
