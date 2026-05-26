import { useContext } from "react";
import { UserContext } from "../../utils/context";
import { Link } from "react-router";
import { login } from "../../utils/keycloak";

function KachelProfil() {
  const user = useContext(UserContext);

  if (user) {
    return (
      <Link to="/profil">
        <div className="white-box feed">
          <img className="kachel-img" src="/images/profil-kachel.webp" alt="Bild: Chalkbag"></img>
          <h3>Mein Profil</h3>
          <p>Willkommen zurück, {user.name}!</p>
          <p>Hier gehts zu deinem Profil.</p>
        </div>
      </Link>
    );
  }

  return (
    <div className="white-box feed" onClick={login}>
      <img className="kachel-img" src="/images/profil-kachel.webp" alt="Bild: Chalkbag"></img>
      <h3>Komm zu uns!</h3>
      <p>Hier kannst du dich anmelden oder registrieren.</p>
    </div>
  );
}

export default KachelProfil;
