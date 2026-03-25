import { mutationOptions } from "@tanstack/react-query";
import type { User } from "../api/model";
import { keycloak } from "./keycloak";
import { changeUser, syncUser } from "../api/user-controller/user-controller";

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
