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
};

export type WandParsed = {
  wandNr: number;
  startX: number;
  startY: number;
  endX: number;
  endY: number;
  centerX: number;
  centerY: number;
  offsetX: number;
  offsetY: number;
  routen: Route[];
};

export type Route = {
  id: number;
  hallenId: number;
  wandNr: number;
  name: string;
  farbe: string;
  schwierigkeit: number;
  is_toprope: boolean;
  is_vorstieg: boolean;
  schrauber: string;
  schraubdatum: Date;
  is_active: boolean;
  beschreibung: string;
};

export type User = {
  id: number;
  name: string;
  bildUrl: string;
  bio: string;
};

export type LoginRequest = {
  email: string;
  password: string;
};

export type RegisterRequest = {
  name: string;
  email: string;
  password: string;
};

export type AuthResponse = {
  token: string;
  user: User;
};

export const colors: Record<string, string> = {
  rot: "#FF0000",
  grün: "#00FF00",
  blau: "#0000FF",
  gelb: "#FFFF00",
  orange: "#FFA500",
  lila: "#800080",
  schwarz: "#000000",
  weiß: "#FFFFFF",
};

export const schwierigkeiten: Record<number, string> = {
  1: "1",
  1.3: "1+",
  1.7: "2-",
  2: "2",
  2.3: "2+",
  2.7: "3-",
  3: "3",
  3.3: "3+",
  3.7: "4-",
  4: "4",
  4.3: "4+",
  4.7: "5-",
  5: "5",
  5.3: "5+",
  5.7: "6-",
  6: "6",
  6.3: "6+",
  6.7: "7-",
  7: "7",
  7.3: "7+",
  7.7: "8-",
  8: "8",
  8.3: "8+",
  8.7: "9-",
  9: "9",
  9.3: "9+",
  9.7: "10-",
  10: "10",
  10.3: "10+",
  10.7: "11-",
  11: "11",
  11.3: "11+",
  11.7: "12-",
  12: "12",
};
