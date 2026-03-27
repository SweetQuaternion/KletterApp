import { mutationOptions } from "@tanstack/react-query";
import type { Ascent, Kommentar, Route, User } from "../api/model";
import { keycloak } from "./keycloak";
import {
  changeUser,
  getUser,
  syncUser,
} from "../api/user-controller/user-controller";
import { addAscent } from "../api/ascent-controller/ascent-controller";
import { addRoute } from "../api/routen-controller/routen-controller";
import { addKommentar } from "../api/kommentar-controller/kommentar-controller";

export async function fetchUser(keycloakId: string, name: string) {
  const response = await syncUser(
    {
      keycloakId,
      name,
    },
    {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${keycloak.token}`,
      },
    },
  );
  return response.data as User;
}

export function createProfileQueryOptions(username: string) {
  return {
    queryKey: ["profile", username],
    queryFn: async () => {
      await keycloak.updateToken(30).catch((err) => {
        console.error("Failed to refresh token", err);
        throw new Error("Failed to refresh token");
      });
      const response = await getUser(
        { username: username },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${keycloak.token}`,
          },
        },
      );
      return response.data as User;
    },
  };
}

export function createUserSyncMutation() {
  return mutationOptions({
    mutationFn: async (data: User) => {
      const response = await fetch(
        `${keycloak.authServerUrl}/realms/${keycloak.realm}/account`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${keycloak.token}`,
          },
          body: JSON.stringify({
            username: data.name,
            email: keycloak.tokenParsed?.email,
            firstName: keycloak.tokenParsed?.given_name,
            lastName: keycloak.tokenParsed?.family_name,
          }),
        },
      );
      if (!response.ok) {
        throw new Error(await response.text());
      }

      console.log(data);

      changeUser(data, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${keycloak.token}`,
        },
      });
    },
  });
}

export function createAddRouteMutationOptions() {
  return mutationOptions({
    mutationFn: async (data: Route) => {
      await keycloak.updateToken(30).catch((err) => {
        console.error("Failed to refresh token", err);
        throw new Error("Failed to refresh token");
      });
      const response = await addRoute(data.wand.id.hallenId || 0, data, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${keycloak.token}`,
        },
      });
      if (response.status !== 201) {
        throw new Error("Failed to add route");
      }
    },
  });
}

export function createAddAscentMutationOptions() {
  return mutationOptions({
    mutationFn: async (data: Ascent) => {
      await keycloak.updateToken(30).catch((err) => {
        console.error("Failed to refresh token", err);
        throw new Error("Failed to refresh token");
      });
      const response = await addAscent(data, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${keycloak.token}`,
        },
      });
      if (response.status !== 201) {
        throw new Error("Failed to add ascent");
      }
    },
  });
}

export function createAddKommentarMutationOptions() {
  return mutationOptions({
    mutationFn: async (data: Kommentar) => {
      await keycloak.updateToken(30).catch((err) => {
        console.error("Failed to refresh token", err);
        throw new Error("Failed to refresh token");
      });
      const response = await addKommentar(data, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${keycloak.token}`,
        },
      });
      if (response.status !== 201) {
        throw new Error("Failed to add kommentar");
      }
    },
  });
}
