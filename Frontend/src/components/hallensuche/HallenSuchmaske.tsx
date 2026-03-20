import { useState } from "react";
import "../../styles/HallenFinder.css";
import "../../styles/Form.css";
import "../../styles/App.css";
import HallenErgebnisFeld from "./HallenErgebnisFeld";
import { type Halle } from "../../constants/APIResponseTypes";

interface Props {
  setSelectedHalle: (id: Halle) => void;
}

function HallenSuchmaske({ setSelectedHalle }: Props) {
  const [ergebnisse, setErgebnisse] = useState<Halle[]>([]);

  const searchHallen = async (name: string) => {
    const response = await fetch(
      `http://localhost:8080/api/hallen?name=${name}`,
    );
    if (!response.ok) {
      throw new Error("Suche fehlgeschlagen");
    }
    const data = (await response.json()) as Halle[];
    return data;
  };

  return (
    <div className="hallensuche white-box">
      <h2>Wähle deine Halle aus</h2>
      <form
        className="flex-row"
        onSubmit={async (e) => {
          e.preventDefault();
          const input = new FormData(e.target as HTMLFormElement); // Erstellt ein FormData-Objekt aus dem Formular
          const name = input.get("name") as string;
          const hallen = await searchHallen(name);
          setErgebnisse(hallen);
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
