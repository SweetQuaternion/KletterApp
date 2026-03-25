export type Halle = {
  id: number;
  name: string;
  adresse: string;
  betreiber: string;
};

export type Wand = {
  hallenId: number;
  wandNr: number;
  sektor: string;
  startX: number;
  startY: number;
  endX: number;
  endY: number;
  position: String;
  routen: Route[];
};

export type Route = {
  id: number;
  wand: { hallenId: number; wandNr: number };
  name: string;
  farbe: string;
  schwierigkeit: number;
  is_toprope: boolean;
  is_vorstieg: boolean;
  schrauber: string;
  schraubdatum: string;
  is_active: boolean;
  beschreibung: string;
};

export type User = {
  keycloakId: string;
  name: string;
  bildUrl: string;
  bio: string;
};

export const convertSchwierigkeitToString = (schwierigkeit: number): string => {
  if (schwierigkeit % 1 === 0) return schwierigkeit.toString();
  let base = Math.floor(schwierigkeit);
  const modifier = schwierigkeit % 1 < 0.5 ? "+" : "-";
  if (modifier === "-") base++;
  return `${base}${modifier}`;
};

export const convertSchwierigkeitToNumber = (schwierigkeit: string): number => {
  const match = schwierigkeit.match(/^(\d+)([+-]?)$/);
  if (!match) throw new Error("Ungültiges Format");
  const base = parseInt(match[1], 10);
  const modifier = match[2];
  if (modifier === "+") return base + 0.3;
  if (modifier === "-") return base - 0.3;
  return base;
};
