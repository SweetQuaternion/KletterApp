// ♫♫ You are way-maker, service worker, promise-keeper, that is who you are [...]

// ----------------Helper Funktionen------------------------

const addResourcesToCache = async (resources) => {
  const cache = await caches.open("v1");
  await cache.addAll(resources);
};

const putInCache = async (request, response) => {
  const cache = await caches.open("v1");
  await cache.put(request, response);
};

const networkFirst = async (request, event) => {
  try {
    const responseFromNetwork = await fetch(request);
    if (!responseFromNetwork || responseFromNetwork.status !== 200) {
      throw new Error("Netzwerkantwort war ungültig: " + responseFromNetwork.status);
    }
    await putInCache(request, responseFromNetwork.clone());
    return responseFromNetwork;
  } catch (error) {
    const responseFromCache = await caches.match(request);
    if (responseFromCache) {
      return responseFromCache;
    }
    return new Response("Sorry, kein Internet verfügbar!", {
      status: 408,
      headers: { "Content-Type": "text/plain" },
    });
  }
};

// ----------------------------------------------------------

// ----------------Servive Worker Code----------------------

// Wird als allererstes ausgeführt
self.addEventListener("install", (event) => {
  self.skipWaiting();
  event.waitUntil(addResourcesToCache(["/", "/index.html"]));
});

self.addEventListener("activate", (event) => {
  event.waitUntil(clients.claim()); // Adoptiert alle Seiten, damit der Service Worker sofort aktiv ist
  event.waitUntil(self.registration?.navigationPreload.enable()); // Aktiviert die Navigation Preload API, damit die Seite schneller geladen wird, während der Service Worker aktiviert wird
});

self.addEventListener("fetch", async (event) => {
  if (
    event.request.url.includes("extension") || // Ignoriere Anfragen an Browser Extensions
    event.request.url.includes("/auth") || // Ignoriere Anfragen an Keycloak
    event.request.url.includes("/api") // Ignoriere Anfragen an unsere API
  ) {
    return;
  }
  event.respondWith(networkFirst(event.request, event));
});

// ----------------------------------------------------------

// Läuft in einem Worker Context, hat daher kein Zugriff auf den DOM
// Braucht https
// Wir müssen immer unsere request und response streams klonen, da sie nur einmal gelesen werden können
