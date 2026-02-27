import { useState } from "react";
import "../styles/HallenFinder.css";
import HallenErgebnisFeld from "./HallenErgebnisFeld";
import { type Halle } from "../constants/APIResponseTypes";

interface Props {
  setSelectedHalle: (id: Halle) => void;
}

function HallenSuchmaske({ setSelectedHalle }: Props) {
  const [ergebnisse, setErgebnisse] = useState<Halle[]>([]);

  const searchHallen = async (name: string) => {
    const response = await fetch(`http://localhost:8080/hallen?name=${name}`);
    if (!response.ok) {
      throw new Error("Suche fehlgeschlagen");
    }
    const data = (await response.json()) as Halle[];
    return data;
  };

  return (
    <div className="hallen-suchmaske">
      <h2>Wähle deine Halle aus</h2>
      <form
        className="flex-row"
        onSubmit={async (e) => {
          e.preventDefault(); // Verhindert das Neuladen der Seite
          const input = new FormData(e.target as HTMLFormElement); // Erstellt ein FormData-Objekt aus dem Formular
          const name = input.get("name") as string; // Holt den eingegebenen Namen aus dem FormData
          if (name) {
            try {
              const hallen = await searchHallen(name);
              setErgebnisse(hallen);
              console.log(hallen);
            } catch (error) {
              console.error(error);
            }
          }
        }}
      >
        <input type="text" name="name" placeholder="Suche nach Halle..." />
        <button type="submit">Suchen</button>
      </form>
      <div className="ergebnis-container">
        {ergebnisse.map((ergebnis, index) => (
          <HallenErgebnisFeld
            key={index}
            ergebnis={ergebnis}
            setSelectedHalle={setSelectedHalle}
          />
        ))}
      </div>
    </div>
  );
}

export default HallenSuchmaske;
