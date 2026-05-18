import Keycloak from "keycloak-js";

export const KEYCLOAK_URL = import.meta.env.VITE_KEYCLOAK_URL;

export const keycloak = new Keycloak({
  url: KEYCLOAK_URL,
  realm: "KletterApp",
  clientId: "kletterapp-frontend",
});

export const isAuthenticated = () => keycloak.authenticated ?? false;
export const isAdmin = () =>
  keycloak.tokenParsed?.realm_access?.roles?.includes("ROLE_ADMIN") ?? false;
export const getUser = () => keycloak.profile;
export const getToken = () => keycloak.token;
export const login = () => keycloak.login();
export const logout = () => keycloak.logout();
export const register = () =>
  keycloak.register({
    redirectUri: window.location.origin + "/willkommen",
  });

export async function initKeycloak(): Promise<void> {
  await keycloak.init({
    onLoad: "check-sso",
    pkceMethod: false,
    checkLoginIframe: false,
    silentCheckSsoRedirectUri: window.location.origin + "/silent-check-sso.html",
  });
}

export async function getAccessToken(): Promise<string | undefined> {
  if (!keycloak.authenticated) {
    return undefined;
  }
  try {
    await keycloak.updateToken(30);
    return keycloak.token;
  } catch {
    await keycloak.login();
    return undefined;
  }
}
