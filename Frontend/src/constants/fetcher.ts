import { getAccessToken, keycloak, KEYCLOAK_URL } from "./keycloak";

const API_BASE_URL = "http://localhost:8080";

export async function customFetch<TResponse>(
  url: string,
  options: RequestInit,
): Promise<TResponse> {
  const token = await getAccessToken();
  const headers = new Headers(options.headers);
  if (token) {
    headers.set("Authorization", `Bearer ${token}`);
  }

  const response = await fetch(`${API_BASE_URL}${url}`, {
    ...options,
    mode: "cors",
    headers,
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(
      `API request failed (${response.status}): ${text || response.statusText}`,
    );
  }

  if (response.status === 204) {
    return undefined as TResponse;
  }

  const contentType = response.headers.get("content-type");
  if (contentType?.includes("application/json")) {
    return (await response.json()) as TResponse;
  }

  if (contentType && !contentType.startsWith("text/")) {
    return (await response.blob()) as TResponse;
  }

  return (await response.text()) as TResponse;
}

export async function keycloakFetch<TResponse>(
  options: RequestInit,
): Promise<TResponse> {
  const token = await getAccessToken();
  const headers = new Headers(options.headers);
  if (token) {
    headers.set("Authorization", `Bearer ${token}`);
  }

  const response = await fetch(
    `${KEYCLOAK_URL}/realms/${keycloak.realm}/account`,
    {
      ...options,
      mode: "cors",
      headers,
    },
  );

  if (!response.ok) {
    const text = await response.text();
    throw new Error(
      `Keycloak request failed (${response.status}): ${text || response.statusText}`,
    );
  }

  if (response.status === 204) {
    return undefined as TResponse;
  }

  const contentType = response.headers.get("content-type");
  if (contentType?.includes("application/json")) {
    return (await response.json()) as TResponse;
  }

  if (contentType && !contentType.startsWith("text/")) {
    return (await response.blob()) as TResponse;
  }

  return (await response.text()) as TResponse;
}
