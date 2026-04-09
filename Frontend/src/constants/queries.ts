import { mutationOptions, QueryClient } from "@tanstack/react-query";
import type {
  AscentCreateDTO,
  AscentResponseDTO,
  KommentarCreateDTO,
  KommentarResponseDTO,
  RouteCreateDTO,
  UserDTO,
  UserRoutenStatus,
} from "../api/model";
import { keycloak } from "./keycloak";
import {
  changeUser,
  getUser,
  syncUser,
} from "../api/user-controller/user-controller";
import {
  addAscent,
  findAscents,
} from "../api/ascent-controller/ascent-controller";
import { addRoute } from "../api/routen-controller/routen-controller";
import {
  addKommentar,
  getKommentareByRouteID,
} from "../api/kommentar-controller/kommentar-controller";
import {
  getUserRoutenStatus,
  updateUserRoutenStatus,
} from "../api/user-routen-status-controller/user-routen-status-controller";
import { customFetch, keycloakFetch } from "./fetcher";
import {
  getAvatar,
  getUploadAvatarUrl,
} from "../api/avatar-controller/avatar-controller";

const ONE_DAY = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
    },
  },
});

export async function fetchUser(keycloakId: string, name: string) {
  return (await syncUser({ keycloakId, name })) as UserDTO;
}

export function createProfileQueryOptions(username: string) {
  return {
    queryKey: ["profile", username],
    queryFn: () => getUser({ username }) as Promise<UserDTO>,
  };
}

export function createAscentQueryOptions(
  routenId: number | null,
  user: UserDTO | null,
) {
  return {
    queryKey: ["ascents", routenId, user?.keycloakId],
    queryFn: async () => {
      if (!user) {
        return null;
      }
      const response = await findAscents({
        routenId: routenId || undefined,
        userId: user.keycloakId,
      });
      return response as AscentResponseDTO[];
    },
    staleTime: ONE_DAY,
  };
}

export function createUserRoutenStatusQueryOptions(
  routenId: number,
  user: UserDTO | null,
) {
  return {
    queryKey: ["userRoutenStatus", routenId, user?.keycloakId],
    queryFn: async () => {
      if (!user) {
        return null;
      }
      const response = await getUserRoutenStatus({
        routenId,
        userId: user.keycloakId,
      });
      return response as UserRoutenStatus;
    },
    staleTime: ONE_DAY,
  };
}

export function createKommentarQueryOptions(routeId: number) {
  return {
    queryKey: ["kommentare", routeId],
    queryFn: async () =>
      getKommentareByRouteID({ routeId }) as Promise<KommentarResponseDTO[]>,
    staleTime: ONE_DAY,
  };
}

export function createUserRoutenStatusMutationOptions() {
  return mutationOptions({
    mutationFn: (data: UserRoutenStatus) => updateUserRoutenStatus(data),
    onSuccess: async (_, variables) => {
      await queryClient.invalidateQueries({
        queryKey: ["userRoutenStatus", variables.routenId, variables.userId],
      });
    },
  });
}

export function createUserSyncMutation() {
  return mutationOptions({
    mutationFn: async (data: UserDTO) => {
      await keycloakFetch({
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: data.name,
          email: keycloak.tokenParsed?.email,
          firstName: keycloak.tokenParsed?.given_name,
          lastName: keycloak.tokenParsed?.family_name,
        }),
      });
      changeUser(data);
    },
  });
}

export function createAddRouteMutationOptions() {
  return mutationOptions({
    mutationFn: async (data: RouteCreateDTO) => {
      await keycloak.updateToken(30).catch((err) => {
        console.error("Failed to refresh token", err);
        throw new Error("Failed to refresh token");
      });
      const response = await addRoute(data.hallenId || 0, data);
      if (!response) {
        throw new Error("Failed to add route");
      }
    },
  });
}

export function createAddAscentMutationOptions() {
  return mutationOptions({
    mutationFn: (data: AscentCreateDTO) => addAscent(data),
    onSuccess: async (_) => {
      await queryClient.invalidateQueries({
        queryKey: ["ascents"],
      });
    },
  });
}

export function createAddKommentarMutationOptions() {
  return mutationOptions({
    mutationFn: (data: KommentarCreateDTO) => addKommentar(data),
  });
}

export function createAvatarQueryOptions(userId: string) {
  return {
    queryKey: ["avatar", userId],
    queryFn: async () => {
      if (!userId) {
        return null;
      }
      return getAvatar({ userId }) as Promise<Blob>;
    },
    staleTime: ONE_DAY,
  };
}

export function createAvatarMutationOptions() {
  return mutationOptions({
    mutationFn: async (data: File) => {
      const userId = keycloak.tokenParsed?.sub || "";
      const formData = new FormData();
      formData.append("file", data);

      return customFetch<string>(getUploadAvatarUrl({ userId }), {
        method: "POST",
        body: formData,
      });
    },
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: ["avatar"],
      });
    },
  });
}
