import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import { keycloak } from "./constants/keycloak.ts";
import { QueryClientProvider } from "@tanstack/react-query";
import { fetchUser, queryClient } from "./constants/queries.ts";
import type { UserResponseDTO } from "./api/model";
import { UserContext } from "./constants/context.ts";

// Test Kommentar

if ("serviceWorker" in navigator) {
  // nicht alle Browser unterstützen Service Worker, besonders ältere nicht
  try {
    await navigator.serviceWorker.register("/service-worker.js"); // globaler Scope per default
  } catch (error) {
    console.error("Service Worker Registrierung fehlgeschlagen:", error);
  }
}

let user: UserResponseDTO | null = null;

let authenticated = false;

if (navigator.onLine) {
  try {
    authenticated = await keycloak.init({
      onLoad: "check-sso",
      checkLoginIframe: false,
      silentCheckSsoRedirectUri: window.location.origin + "/silent-check-sso.html",
    });
  } catch (error) {
    console.error("Fehler bei der Keycloak-Initialisierung:", error);
    authenticated = false;
  }
} else {
  console.info("Offline-Modus, kein Login verfügbar");
}

if (authenticated) {
  user = await fetchUser(keycloak.subject!, keycloak.tokenParsed?.preferred_username!);
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
