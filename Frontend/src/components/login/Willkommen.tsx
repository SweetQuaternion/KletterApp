import { Link } from "react-router";
import type { User } from "../../constants/APIResponseTypes";
import "../../styles/App.css";
import Header from "../Header";

interface Props {
  user: User | null;
}

function Willkommen({ user }: Props) {
  return (
    <>
      <Header user={user} />
      <div className="white-box">
        <h2>Willkommen bei der KletterApp!</h2>
        <p>Du bist nun eingeloggt.</p>
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
