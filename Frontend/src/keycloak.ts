import Keycloak from "keycloak-js";
// import type { User } from "./constants/APIResponseTypes";

export const keycloak = new Keycloak({
  url: "http://localhost:8180",
  realm: "KletterApp",
  clientId: "kletterapp-frontend",
});

// export const useAuth = () => {
//   return {
//     isAuthenticated: keycloak.authenticated ?? false,
//     user: keycloak.tokenParsed,
//     login: () => keycloak.login(),
//     logout: () => keycloak.logout(),
//     register: () => keycloak.register(),
//     token: keycloak.token,
//   };
// };

export const isAuthenticated = () => keycloak.authenticated ?? false;
export const getUser = () => keycloak.profile;
export const getToken = () => keycloak.token;
export const login = () => keycloak.login();
export const logout = () => keycloak.logout();
export const register = () =>
  keycloak.register({
    redirectUri: window.location.origin + "/willkommen",
  });
