import { Link } from "react-router";
import "../../styles/HallenSuche.css";
import type { HalleResponseDTO } from "../../api/model";
import { HalleContext } from "../../constants/context";
import { useContext } from "react";

interface Props {
  ergebnis: HalleResponseDTO;
}

function HallenErgebnisFeld({ ergebnis }: Props) {
  const { setSelectedHalle } = useContext(HalleContext);
  const handleHallenSelection = (ergebnis: HalleResponseDTO) => {
    sessionStorage.setItem("Halle", JSON.stringify(ergebnis));
    setSelectedHalle(ergebnis);
  };
  return (
    <Link to="/routenkarte">
      <button className="hallen-ergebnis-feld" onClick={() => handleHallenSelection(ergebnis)}>
        <div className="mini-dot"></div>
        <p>{ergebnis.name}</p>
      </button>
    </Link>
  );
}

export default HallenErgebnisFeld;
