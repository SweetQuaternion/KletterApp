import { type Halle } from "../../constants/APIResponseTypes";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import "../../styles/HallenFinder.css";

interface Props {
  ergebnis: Halle;
  setSelectedHalle: (id: Halle) => void;
}

function HallenErgebnisFeld({ ergebnis, setSelectedHalle }: Props) {
  return (
    <Link to="/routenkarte">
      <button
        className="hallen-ergebnis-feld"
        onClick={() => setSelectedHalle(ergebnis)}
      >
        <div className="mini-dot"></div>
        <p>{ergebnis.name}</p>
      </button>
    </Link>
  );
}

export default HallenErgebnisFeld;
