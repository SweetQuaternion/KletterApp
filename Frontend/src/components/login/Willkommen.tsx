import { useEffect } from "react";
import type { User } from "../../constants/APIResponseTypes";
import { getUser, isAuthenticated, keycloak } from "../../keycloak";
import "../../styles/App.css";
import Header from "../Header";

interface Props {
  user: User | null;
  setUser?: (user: User | null) => void;
}

function Willkommen({ user, setUser }: Props) {
  const putUserInDatabase = async () => {
    console.log("1. Funktion aufgerufen");
    console.log("2. isAuthenticated:", isAuthenticated());
    console.log("3. keycloak objekt:", keycloak);
    console.log("4. keycloakUser:", getUser());

    if (!isAuthenticated()) return null;
    const keycloakUser = getUser();
    console.log("5. keycloakUser nach check:", keycloakUser);

    if (!keycloakUser || !keycloakUser.email) return null;
    console.log("6. email:", keycloakUser.email);
    const request = {
      email: keycloakUser.email,
      name: keycloakUser.username,
    };
    const response = await fetch("http://localhost:8080/api/users/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(request),
    });
    if (!response.ok) return null;
    const data = (await response.json()) as User;
    console.log("User in database:", data);
    return data;
  };

  useEffect(() => {
    // direkt aufrufen falls schon eingeloggt
    if (isAuthenticated()) {
      putUserInDatabase().then(setUser);
      return;
    }

    // sonst auf Keycloak Event warten
    keycloak.onAuthSuccess = () => {
      putUserInDatabase().then(setUser);
    };
  }, []);

  return (
    <>
      <Header user={user} />
      <div className="white-box">
        <h2>Willkommen bei der KletterApp!</h2>
      </div>
    </>
  );
}

export default Willkommen;
