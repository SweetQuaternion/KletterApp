import "../styles/App.css";
import "../styles/Header.css";
import logo from "/kletterapp-logo.webp";
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
        <button
          className="left-part"
          onClick={(e) => {
            e.stopPropagation();
            setNavigationToggled(!navigationToggled);
          }}
          aria-label="Menü"
        >
          <img className="header-logo" src={logo} alt="Karabiner-Logo" />
          <h1>KletterApp</h1>
        </button>
        {keycloak.didInitialize && isOnline ? (
          <>
            {user ? (
              <div className="right-part">
                <span className="username">Hallo {user.name}</span>
                <button
                  className="red-button profile-button"
                  onClick={(e) => {
                    e.stopPropagation();
                    setProfileToggled(!profileToggled);
                  }}
                  aria-label="Profil"
                >
                  {avatar && <img src={URL.createObjectURL(avatar)} alt="Profilbild" />}
                </button>
              </div>
            ) : (
              <div className="right-part">
                <button className="red-button login" onClick={() => login()}>
                  Anmelden
                </button>
                <button className="red-button register" onClick={() => register()}>
                  Registrieren
                </button>
              </div>
            )}
          </>
        ) : (
          <div className="right-part">
            <span className="offline">offline</span>
          </div>
        )}
      </header>
      {profileToggled && (
        <nav className="overlay" onClick={() => setProfileToggled(false)}>
          <div className="profile-menu">
            <Link to="/profil" className="red-button">
              Mein Profil
            </Link>
            <button className="red-button" onClick={() => logout()}>
              Abmelden
            </button>
          </div>
        </nav>
      )}
      {navigationToggled && (
        <div className="overlay" onClick={() => setNavigationToggled(false)}>
          <nav className="navigation-menu">
            {heimatHallen.map((halle) => (
              <div key={halle.id}>
                <Link
                  to="/routenkarte/"
                  className="red-button"
                  onClick={() => setSelectedHalle(halle)}
                >
                  {halle.name}
                </Link>
              </div>
            ))}
            <div>
              <Link to="/hallensuche" className="red-button">
                Hallensuche
              </Link>
            </div>
            {user && (
              <div>
                <Link to="/profil" className="red-button">
                  Mein Profil
                </Link>
              </div>
            )}
          </nav>
        </div>
      )}
    </>
  );
}

export default Header;
