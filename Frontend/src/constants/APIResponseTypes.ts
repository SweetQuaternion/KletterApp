export type Halle = {
  id: number;
  name: string;
  adresse: string;
  betreiber: string;
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
