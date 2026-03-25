import { mutationOptions, queryOptions } from "@tanstack/react-query";
import type { Halle, Route, User, Wand } from "./APIResponseTypes";
import { keycloak } from "./keycloak";

export function createHallenQueryOptions(search: String | null) {
  return queryOptions({
    queryKey: ["hallen", search],
    queryFn: async (): Promise<Halle[]> => {
      const response = await fetch(
        `http://localhost:8080/api/hallen?name=${search}`,
      );
      if (!response.ok) {
        throw new Error("Failed to fetch hallen");
      }
      return (await response.json()) as Halle[];
    },
    enabled: search !== null,
    staleTime: 1000 * 60 * 60 * 24,
  });
}

export function createWändeQueryOptions(hallenId: number) {
  return queryOptions({
    queryKey: ["waende", hallenId],
    queryFn: async (): Promise<Wand[]> => {
      const response = await fetch(
        `http://localhost:8080/api/hallen/${hallenId}/waende`,
      );
      if (!response.ok) {
        throw new Error("Failed to fetch wände");
      }
      return (await response.json()) as Wand[];
    },
    staleTime: 1000 * 60 * 60 * 24,
  });
}

export async function fetchUser(keycloakId: string, name: string) {
  const response = await fetch("http://localhost:8080/api/users", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${keycloak.token}`,
    },
    body: JSON.stringify({ keycloakId, name }),
  });
  if (!response.ok) {
    throw new Error("Failed to fetch user");
  }
  return (await response.json()) as User;
}

export function createPostRouteMutation() {
  return mutationOptions({
    mutationFn: async (data: Route) => {
      const response = await fetch(
        `http://localhost:8080/api/hallen/${data.wand.hallenId}/routen`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${keycloak.token}`,
          },
          body: JSON.stringify(data),
        },
      );
      if (!response.ok) {
        throw new Error("Failed to post route");
      }
    },
  });
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
      const userResponse = await fetch("http://localhost:8080/api/users", {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${keycloak.token}`,
        },
        body: JSON.stringify({
          keycloakId: keycloak.tokenParsed?.sub,
          name: data.name,
          bildUrl: data.bildUrl,
          bio: data.bio,
        }),
      });
      if (!userResponse.ok) {
        throw new Error(await userResponse.text());
      }
    },
  });
}
