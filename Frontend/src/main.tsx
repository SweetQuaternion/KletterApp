import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import { keycloak } from "./constants/keycloak.ts";
import { QueryClientProvider } from "@tanstack/react-query";
import { fetchUser, queryClient } from "./constants/queries.ts";
import type { UserResponseDTO } from "./api/model";
import { UserContext } from "./constants/context.ts";

keycloak
  .init({
    onLoad: "check-sso",
    silentCheckSsoRedirectUri:
      window.location.origin + "/silent-check-sso.html",
  })

  .then(async (authenticated) => {
    let user: UserResponseDTO | null = null;
    if (authenticated) {
      user = await fetchUser(keycloak.subject!, keycloak.tokenParsed?.name!);
    }

    createRoot(document.getElementById("root")!).render(
      <StrictMode>
        <QueryClientProvider client={queryClient}>
          <UserContext.Provider value={user}>
            <App />
          </UserContext.Provider>
        </QueryClientProvider>
      </StrictMode>,
    );
  });
