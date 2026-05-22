import { mutationOptions, QueryClient } from "@tanstack/react-query";
import type {
  AscentCreateDTO,
  AscentResponseDTO,
  HalleCreateDTO,
  KommentarCreateDTO,
  KommentarResponseDTO,
  RouteCreateDTO,
  UserCreateDTO,
  UserResponseDTO,
  UserRoutenStatus,
  WandCreateDTO,
  WandResponseDTO,
} from "../api/model";
import { keycloak } from "./keycloak";
import { changeUser, getUser, syncUser } from "../api/user-controller/user-controller";
import { addAscent, findAllAscents, findAscents } from "../api/ascent-controller/ascent-controller";
import { addRoute, deleteRoute, updateRoute } from "../api/routen-controller/routen-controller";
import {
  addKommentar,
  getKommentareByRouteID,
} from "../api/kommentar-controller/kommentar-controller";
import {
  getAllUserRoutenStatus,
  getUserRoutenStatus,
  updateUserRoutenStatus,
} from "../api/user-routen-status-controller/user-routen-status-controller";
import { customFetch, keycloakFetch } from "./fetcher";
import { getAvatar, getUploadAvatarUrl } from "../api/avatar-controller/avatar-controller";
import { addHalle } from "../api/hallen-controller/hallen-controller";
import { addWände, getWaendeByHallenId } from "../api/wand-controller/wand-controller";
import getDB from "./db";

const ONE_DAY = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
    },
  },
});

// ============= User Queries and Mutations =============

export async function fetchUser(keycloakId: string, name: string) {
  return (await syncUser({ keycloakId, name })) as UserResponseDTO;
}

export function createProfileQueryOptions(username: string) {
  return {
    queryKey: ["profile", username],
    queryFn: () => getUser({ username }) as Promise<UserResponseDTO>,
  };
}

export function createUserSyncMutation() {
  return mutationOptions({
    mutationFn: async (data: UserCreateDTO) => {
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

// ============= Ascent Queries and Mutations =============

export function createAscentQueryOptions(routenId: number | null, user: UserResponseDTO | null) {
  return {
    queryKey: ["ascents", routenId],
    queryFn: async () => {
      if (!user) {
        const db = await getDB();
        const ascents = await db
          .transaction("ascents")
          .objectStore("ascents")
          .index("routenId")
          .getAll(routenId);
        const ascentsPending = await db
          .transaction("ascentsPending")
          .objectStore("ascentsPending")
          .index("routenId")
          .getAll(routenId);
        return ascents.concat(ascentsPending) as AscentResponseDTO[];
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

export function createAllAscentsQueryOptions(user: UserResponseDTO | null, routenIdList: number[]) {
  return {
    queryKey: ["allAscents", user?.keycloakId, routenIdList],
    queryFn: async () => {
      if (!user) return [];
      return findAllAscents({ userId: user.keycloakId, routenIdList: routenIdList }) as Promise<
        AscentResponseDTO[]
      >;
    },
    enabled: routenIdList.length > 0,
  };
}

export function createAddAscentMutationOptions() {
  return mutationOptions({
    mutationFn: async (data: AscentCreateDTO) => {
      if (!data.userId) {
        const db = await getDB();
        await db.put("ascentsPending", data);
        return null;
      } else {
        return addAscent(data);
      }
    },
    onSuccess: async (_) => {
      await queryClient.invalidateQueries({
        queryKey: ["ascents"],
      });
    },
  });
}

// ============= UserRoutenStatus Queries and Mutations =============

export function createUserRoutenStatusQueryOptions(routenId: number, user: UserResponseDTO | null) {
  return {
    queryKey: ["userRoutenStatus", routenId, user?.keycloakId],
    queryFn: async () => {
      if (!user) {
        // lese Status aus IndexedDB
        const db = await getDB();
        const status = await db.get("userRoutenStatus", `${routenId}`);
        const pendingStatus = await db.get("userRoutenStatusPending", `${routenId}`);
        if (pendingStatus) {
          return pendingStatus as UserRoutenStatus;
        } else if (status) {
          return status as UserRoutenStatus;
        } else {
          return {
            userId: "",
            routenId,
            isFavorit: false,
            isProjekt: false,
            notiz: "",
          } as UserRoutenStatus;
        }
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

export function createAllUserRoutenStatusQueryOptions(
  user: UserResponseDTO | null,
  routenIdList: number[],
) {
  return {
    queryKey: ["allUserRoutenStatus", user?.keycloakId, routenIdList],
    queryFn: async () => {
      if (!user) return [];
      return getAllUserRoutenStatus({
        userId: user.keycloakId,
        routenIdList: routenIdList,
      }) as Promise<UserRoutenStatus[]>;
    },
    enabled: routenIdList.length > 0,
  };
}

export function createUserRoutenStatusMutationOptions() {
  return mutationOptions({
    mutationFn: async (data: UserRoutenStatus) => {
      if (!data.userId) {
        const db = await getDB();
        return await db.put("userRoutenStatusPending", data, `${data.routenId}`);
      }
      return updateUserRoutenStatus(data);
    },
    onSuccess: async (_, variables) => {
      await queryClient.invalidateQueries({
        queryKey: ["userRoutenStatus", variables.routenId],
      });
    },
  });
}

// ============= Kommentar Queries and Mutations =============

export function createKommentarQueryOptions(routeId: number) {
  return {
    queryKey: ["kommentare", routeId],
    queryFn: async () => getKommentareByRouteID({ routeId }) as Promise<KommentarResponseDTO[]>,
    staleTime: ONE_DAY,
  };
}

export function createAddKommentarMutationOptions() {
  return mutationOptions({
    mutationFn: (data: KommentarCreateDTO) => addKommentar(data),
  });
}

// ============= Route Queries and Mutations =============

export function createAddRouteMutationOptions() {
  return mutationOptions({
    mutationFn: async (data: RouteCreateDTO) => {
      const response = await addRoute(data.hallenId || 0, data);
      if (!response) {
        throw new Error("Failed to add route");
      }
    },
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: ["waende"],
      });
    },
  });
}

export function createUpdateRouteMutationOptions() {
  return mutationOptions({
    mutationFn: async (data: { route: RouteCreateDTO; id: number }) => {
      const response = await updateRoute(data.route.hallenId, data.route, {
        id: data.id,
      });
      if (!response) {
        throw new Error("Failed to update route");
      }
    },
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: ["waende"],
      });
    },
  });
}

export function createDeleteRouteMutationOptions() {
  return mutationOptions({
    mutationFn: async (data: { hallenId: number; id: number }) => {
      await deleteRoute(data.hallenId, { id: data.id });
    },
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: ["waende"],
      });
    },
  });
}

// ============= Avatar Queries and Mutations =============

export function createAvatarQueryOptions(userId: string) {
  return {
    queryKey: ["avatar", userId],
    queryFn: async () => {
      if (!userId) {
        return null;
      }
      const avatarBlob = (await getAvatar({ userId })) as Blob;
      if (avatarBlob.size === 0) {
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

// ============= Halle Queries and Mutations =============

export function createHalleMutationOptions() {
  return mutationOptions({
    mutationFn: async (data: HalleCreateDTO) => {
      return addHalle(data);
    },
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: ["hallen"],
      });
    },
  });
}

// ============= Wand Queries and Mutations =============

export function createWaendeByHallenIdQueryOptions(halleId: number) {
  return {
    queryKey: ["waende", halleId],
    queryFn: async () => {
      if (navigator.onLine) {
        try {
          return (await getWaendeByHallenId(halleId)) as WandResponseDTO[];
        } catch (error) {
          console.log("Fehler beim Laden aus dem Netzwerk, nutze Cache.");
        }
      }

      try {
        const db = await getDB();
        return ((await db.get("waende", halleId)) as WandResponseDTO[]) || [];
      } catch (error) {
        console.log("Fehler beim Laden aus dem Cache.");
        return [];
      }
    },
    staleTime: 60 * 1000,
  };
}

export function createWandMutationOptions() {
  return mutationOptions({
    mutationFn: async ({ hallenId, data }: { hallenId: number; data: WandCreateDTO[] }) => {
      addWände(hallenId, data);
    },
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: ["hallen"],
      });
    },
  });
}
