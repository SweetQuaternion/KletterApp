import { Link } from "react-router";
import "../../styles/App.css";
import Header from "../Header";
import type { UserResponseDTO } from "../../api/model";

interface Props {
  user: UserResponseDTO | null;
}

function Willkommen({ user }: Props) {
  return (
    <>
      <Header user={user} />
      <div className="white-box center">
        <h2>Willkommen bei der KletterApp!</h2>
        <p>Du bist nun erfolgreich registriert.</p>
        <div className="centering">
          <div className="button-container">
            <Link to="/profile">
              <button>Mein Profil</button>
            </Link>
            <Link to="/hallenfinder">
              <button>Startseite</button>
            </Link>
          </div>
        </div>
      </div>
    </>
  );
}

export default Willkommen;
