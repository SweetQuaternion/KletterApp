import "../styles/App.css";
import "../styles/Header.css";
import logo from "../assets/react.svg";
import type { UserResponseDTO } from "../api/model";
import { login, register, logout } from "../constants/keycloak";
import { useEffect, useState } from "react";
import { Link } from "react-router";
import { useQuery } from "@tanstack/react-query";
import { createAvatarQueryOptions } from "../constants/queries";

interface Props {
  user: UserResponseDTO | null;
}

function Header({ user }: Props) {
  const [menuToggled, setMenuToggled] = useState(false);

  useEffect(() => {
    const handler = () => setMenuToggled(false);
    document.addEventListener("click", handler);
    return () => document.removeEventListener("click", handler);
  }, []);

  const { data: avatar } = useQuery(
    createAvatarQueryOptions(user?.keycloakId || ""),
  );

  return (
    <>
      <header>
        <div className="left-part">
          <img src={logo} />
          <h1>KletterApp</h1>
        </div>
        {!user && (
          <div className="right-part">
            <button className="login" onClick={() => login()}>
              Anmelden
            </button>
            <button className="register" onClick={() => register()}>
              Registrieren
            </button>
          </div>
        )}
        {user && (
          <div className="right-part">
            <span className="username">Hallo {user.name}</span>
            <button
              className="profile-button"
              onClick={(e) => {
                e.stopPropagation();
                setMenuToggled(!menuToggled);
              }}
            >
              {avatar && <img src={URL.createObjectURL(avatar)} alt="Profil" />}
            </button>
          </div>
        )}
      </header>
      {menuToggled && (
        <div className="profile-menu">
          <Link to="/profil">
            <button>Mein Profil</button>
          </Link>
          <button onClick={() => logout()}>Abmelden</button>
        </div>
      )}
    </>
  );
}

export default Header;
