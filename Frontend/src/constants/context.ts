// context/UserContext.tsx
import { createContext } from "react";
import type { HalleResponseDTO, UserResponseDTO } from "../api/model";

export const UserContext = createContext<UserResponseDTO | null>(null);

export const HalleContext = createContext<{
  selectedHalle: HalleResponseDTO | null;
  setSelectedHalle: (halle: HalleResponseDTO | null) => void;
}>({
  selectedHalle: null,
  setSelectedHalle: () => {},
});
