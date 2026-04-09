import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import { keycloak } from "./constants/keycloak.ts";
import { QueryClientProvider } from "@tanstack/react-query";
import { fetchUser, queryClient } from "./constants/queries.ts";
import type { User } from "./api/model";

keycloak
  .init({
    onLoad: "check-sso",
    silentCheckSsoRedirectUri:
      window.location.origin + "/silent-check-sso.html",
  })

  .then(async (authenticated) => {
    let user: User | null = null;
    if (authenticated) {
      user = await fetchUser(keycloak.subject!, keycloak.tokenParsed?.name!);
      console.log("User authenticated:", user.name);
    } else {
      console.log("User not authenticated");
    }

    createRoot(document.getElementById("root")!).render(
      <StrictMode>
        <QueryClientProvider client={queryClient}>
          <App user={user} />
        </QueryClientProvider>
      </StrictMode>,
    );
  });

// login-required authenticates if the user is logged in or displays the login page, zwingt User zum Login
// check-sso authenticates if user is logged in, otherwise does nothing, lässt User trotzdem die Seite nutzen

// wir wollen hier check-sso (optional auth), damit Nutzer die Seite auch ohne Login nutzen können, aber wenn
// sie Dinge speichern wollen, müssen sie sich einloggen, damit wir wissen, wem die Daten gehören
