import { Link } from "react-router";
import "../../styles/HallenSuche.css";
import type { HalleResponseDTO } from "../../api/model";
import { HalleContext } from "../../utils/context";
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
    <Link
      to="/routenkarte"
      className="hallen-ergebnis-feld"
      onClick={() => handleHallenSelection(ergebnis)}
    >
      <div className="mini-dot"></div>
      <p>{ergebnis.name}</p>
    </Link>
  );
}

export default HallenErgebnisFeld;
