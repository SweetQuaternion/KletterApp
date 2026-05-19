import { useState } from "react";
import "../../styles/HallenSuche.css";
import "../../styles/Form.css";
import HallenErgebnisFeld from "./HallenErgebnisFeld.tsx";
import { useQuery } from "@tanstack/react-query";
import { getFindHalleQueryOptions } from "../../api/hallen-controller/hallen-controller.ts";
import { isAdmin } from "../../utils/keycloak.ts";
import { Link } from "react-router";
import { useOnline } from "../../utils/useOnline.ts";
import type { HalleResponseDTO } from "../../api/model/halleResponseDTO.ts";

// beim Laden der Seite wird die Suche mit leerem search ausgeführt (alle Ergebnisse)
// diese alle Ergebnisse werden im Frontend durch getSuchergebnisse gefiltert mit dem aktuellen Wert des Feldes
// bei Klick auf den Suchen-Knopf, updated sich search, das Backend lädt neue Daten
// wenn offline oder Backend weg, werden die OfflineHallen angezeigt

function HallenSuche() {
  const isOnline = useOnline();
  const [search, setSearch] = useState<string | undefined>(undefined);
  const { data, error } = useQuery(
    getFindHalleQueryOptions(search ? { name: search } : undefined, {
      query: { enabled: isOnline },
    }),
  );

  const [ergebnisse, setErgebnisse] = useState(
    data || (JSON.parse(localStorage.getItem("OfflineHallen") || "[]") as HalleResponseDTO[]),
  );

  function getSucherergebnisse(prompt: string) {
    const tmp = data?.filter((halle) => halle.name.includes(prompt)) || [];
    setErgebnisse(tmp.slice(0, 3));
  }

  return (
    <section className="hallensuche white-box center">
      <h2>Wähle deine Halle aus</h2>
      <search>
        <form
          className="flex-row"
          onSubmit={async (e) => {
            e.preventDefault();
            const input = new FormData(e.target as HTMLFormElement); // Erstellt ein FormData-Objekt aus dem Formular
            setSearch(input.get("name") as string);
          }}
        >
          <label className="sr-only" htmlFor="hallensuche">
            Hallenname
          </label>
          <input
            type="text"
            name="name"
            placeholder="Suche nach Halle..."
            autoComplete="off"
            onInput={(e) => getSucherergebnisse(e.currentTarget.value)}
            onSubmit={(e) => setSearch(e.currentTarget.value)}
            autoFocus
          />
          <button type="submit" className="red-button">
            Suchen
          </button>
        </form>
      </search>

      <ul className="ergebnis-container">
        {ergebnisse?.map((ergebnis, index) => (
          <li key={index}>
            <HallenErgebnisFeld ergebnis={ergebnis} />
          </li>
        ))}
      </ul>

      {search && (!data || data.length === 0) && (
        <p className="sans-serif small">Keine Halle gefunden...</p>
      )}
      {error !== null && <p className="sans-serif small">Ein Fehler ist aufgetreten</p>}

      {isAdmin() && (
        <Link to="/editor" className="text-button">
          Halle hinzufügen
        </Link>
      )}
    </section>
  );
}

export default HallenSuche;
