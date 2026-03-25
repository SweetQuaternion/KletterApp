import { useState } from "react";
import "../../styles/HallenFinder.css";
import "../../styles/Form.css";
import HallenErgebnisFeld from "./HallenErgebnisFeld";
import { useQuery } from "@tanstack/react-query";
import type { Halle } from "../../api/model/halle";
import { getFindHalleQueryOptions } from "../../api/hallen-controller/hallen-controller";

interface Props {
  setSelectedHalle: (id: Halle) => void;
}

function HallenSuchmaske({ setSelectedHalle }: Props) {
  const [search, setSearch] = useState<string | undefined>(undefined);
  const { data, error } = useQuery(
    getFindHalleQueryOptions(search ? { name: search } : undefined, {
      query: { enabled: search !== undefined },
    }),
  );

  return (
    <div className="hallensuche white-box">
      <h2>Wähle deine Halle aus</h2>
      <form
        className="flex-row"
        onSubmit={async (e) => {
          e.preventDefault();
          const input = new FormData(e.target as HTMLFormElement); // Erstellt ein FormData-Objekt aus dem Formular
          const name = input.get("name") as string;
          setSearch(name);
        }}
      >
        <input
          type="text"
          name="name"
          placeholder="Suche nach Halle..."
          autoComplete="off"
        />
        <button type="submit">Suchen</button>
      </form>
      <div className="ergebnis-container">
        {data?.data?.map((ergebnis, index) => (
          <HallenErgebnisFeld
            key={index}
            ergebnis={ergebnis}
            setSelectedHalle={setSelectedHalle}
          />
        ))}
        {data && data.data?.length === 0 && (
          <p className="sans-serif small">Keine Halle gefunden...</p>
        )}
        {error !== null && (
          <p className="sans-serif small">Ein Fehler ist aufgetreten</p>
        )}
      </div>
    </div>
  );
}

export default HallenSuchmaske;
