import { Link } from "react-router";
import type { HalleResponseDTO } from "../../api/model";
import { useContext } from "react";
import { HalleContext } from "../../utils/context";

interface Props {
  heimathalle: HalleResponseDTO | null;
}

const KachelRoutenkarte = ({ heimathalle }: Props) => {
  const { setSelectedHalle } = useContext(HalleContext);

  const handleHallenSelection = (ergebnis: HalleResponseDTO) => {
    sessionStorage.setItem("Halle", JSON.stringify(ergebnis));
    setSelectedHalle(ergebnis);
  };

  if (!heimathalle) {
    return (
      <Link to="/hallensuche">
        <div className="white-box feed">
          <h3>Hallensuche</h3>
          <p>Finde deine Heimathalle, dann wird sie dir hier angezeigt.</p>
        </div>
      </Link>
    );
  }

  return (
    <Link to="/routenkarte" onClick={() => handleHallenSelection(heimathalle)}>
      <div className="white-box feed">
        <img className="kachel-img" src="/images/routenkarte-kachel.webp" alt="Bild: Routenkarte" />
        {heimathalle ? (
          <>
            <h3>Routenkarte: {heimathalle.name}</h3>
            <p>Hier gehts direkt zur Routenkarte deiner Heimathalle.</p>
          </>
        ) : (
          <>
            <h3>Routenkarte deiner Heimathalle</h3>
            <p>Du hast noch keine Heimathalle markiert.</p>
          </>
        )}
      </div>
    </Link>
  );
};

export default KachelRoutenkarte;
