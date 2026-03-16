import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import { keycloak } from "./keycloak.ts";

keycloak
  .init({
    onLoad: "check-sso",
    silentCheckSsoRedirectUri:
      window.location.origin + "/silent-check-sso.html",
  })
  .then(async (authenticated) => {
    let user = null;
    if (authenticated) {
      const token = keycloak.token;
      const keycloakId = keycloak.tokenParsed?.sub;
      const name = keycloak.tokenParsed?.preferred_username;
      const response = await fetch("http://localhost:8080/api/users", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ keycloakId, name }),
      });
      user = await response.json();
      console.log("User authenticated:", user);
    } else {
      console.log("User not authenticated");
    }
    createRoot(document.getElementById("root")!).render(
      <StrictMode>
        <App user={user} />
      </StrictMode>,
    );
  });

// login-required authenticates if the user is logged in or displays the login page, zwingt User zum Login
// check-sso authenticates if user is logged in, otherwise does nothing, lässt User trotzdem die Seite nutzen

// wir wollen hier check-sso (optional auth), damit Nutzer die Seite auch ohne Login nutzen können, aber wenn
// sie Dinge speichern wollen, müssen sie sich einloggen, damit wir wissen, wem die Daten gehören
