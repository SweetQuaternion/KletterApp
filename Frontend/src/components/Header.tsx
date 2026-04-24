import "../styles/App.css";
import "../styles/Header.css";
import logo from "../assets/react.svg";
import type { HalleResponseDTO } from "../api/model";
import { login, register, logout, keycloak } from "../constants/keycloak";
import { useContext, useState } from "react";
import { Link } from "react-router";
import { useQuery } from "@tanstack/react-query";
import { createAvatarQueryOptions } from "../constants/queries";
import { UserContext } from "../constants/context.ts";
import { useOnline } from "../constants/useOnline.ts";

interface Props {
  setSelectedHalle: (halle: HalleResponseDTO) => void;
}

function Header({ setSelectedHalle }: Props) {
  const user = useContext(UserContext);
  const [profileToggled, setProfileToggled] = useState(false);
  const [navigationToggled, setNavigationToggled] = useState(false);
  const isOnline = useOnline();

  const heimatHallen = JSON.parse(
    localStorage.getItem("Heimathallen") || "[]",
  ) as HalleResponseDTO[];

  const { data: avatar } = useQuery(createAvatarQueryOptions(user?.keycloakId || ""));

  return (
    <>
      <header>
        <div className="left-part">
          <img
            src={logo}
            onClick={(e) => {
              e.stopPropagation();
              setNavigationToggled(!navigationToggled);
            }}
          />
          <h1>KletterApp</h1>
        </div>
        {keycloak.didInitialize && isOnline && !user ? (
          <div className="right-part">
            <button className="login" onClick={() => login()}>
              Anmelden
            </button>
            <button className="register" onClick={() => register()}>
              Registrieren
            </button>
          </div>
        ) : (
          <div className="right-part offline">offline</div>
        )}
        {user && (
          <div className="right-part">
            <span className="username">Hallo {user.name}</span>
            <button
              className="profile-button"
              onClick={(e) => {
                e.stopPropagation();
                setProfileToggled(!profileToggled);
              }}
            >
              {avatar && <img src={URL.createObjectURL(avatar)} alt="Profil" />}
            </button>
          </div>
        )}
      </header>
      {profileToggled && (
        <div className="overlay" onClick={() => setProfileToggled(false)}>
          <div className="profile-menu">
            <Link to="/profil">
              <button>Mein Profil</button>
            </Link>
            <button onClick={() => logout()}>Abmelden</button>
          </div>
        </div>
      )}
      {navigationToggled && (
        <div className="overlay" onClick={() => setNavigationToggled(false)}>
          <div className="navigation-menu">
            {heimatHallen.map((halle) => (
              <div key={halle.id}>
                <Link to="/routenkarte/">
                  <button onClick={() => setSelectedHalle(halle)}>{halle.name}</button>
                </Link>
              </div>
            ))}
            <Link to="/hallenfinder">
              <button>Hallenfinder</button>
            </Link>
            {user && (
              <>
                <Link to="/profil">
                  <button>Mein Profil</button>
                </Link>
              </>
            )}
          </div>
        </div>
      )}
    </>
  );
}

export default Header;
