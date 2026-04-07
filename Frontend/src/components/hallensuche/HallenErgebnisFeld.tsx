import { Link } from "react-router";
import "../../styles/HallenFinder.css";
import type { HalleResponseDTO } from "../../api/model";

interface Props {
  ergebnis: HalleResponseDTO;
  setSelectedHalle: (id: HalleResponseDTO) => void;
}

function HallenErgebnisFeld({ ergebnis, setSelectedHalle }: Props) {
  const handleHallenSelection = (ergebnis: HalleResponseDTO) => {
    localStorage.setItem("Halle", JSON.stringify(ergebnis));
    setSelectedHalle(ergebnis);
  };
  return (
    <Link to="/routenkarte">
      <button
        className="hallen-ergebnis-feld"
        onClick={() => handleHallenSelection(ergebnis)}
      >
        <div className="mini-dot"></div>
        <p>{ergebnis.name}</p>
      </button>
    </Link>
  );
}

export default HallenErgebnisFeld;
