import { type Halle } from "../constants/APIResponseTypes";

interface Props {
  ergebnis: Halle;
  setSelectedHalle: (id: Halle) => void;
}

function HallenErgebnisFeld({ ergebnis, setSelectedHalle }: Props) {
  return (
    <button
      className="hallen-ergebnis-feld"
      onClick={() => setSelectedHalle(ergebnis)}
    >
      <div className="mini-dot"></div>
      <p>{ergebnis.name}</p>
    </button>
  );
}

export default HallenErgebnisFeld;
