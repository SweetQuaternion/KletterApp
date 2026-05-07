import RoutenErgebnisFeld from "./RoutenErgebnisFeld";
import { isAdmin } from "../../constants/keycloak.ts";
import type { RouteResponseDTO, WandResponseDTO } from "../../api/model";

interface Props {
  selectedWand: WandResponseDTO;
  routen: RouteResponseDTO[];
  setSelectedRoute: (route: RouteResponseDTO | null) => void;
  setShowNeueRoute: (show: boolean) => void;
}

const WandInfoBox = ({ selectedWand, routen, setSelectedRoute, setShowNeueRoute }: Props) => {
  return (
    <article className="wand-infobox">
      <div className="wand-infobox-header">
        <h3>Wand {selectedWand?.wandNr}</h3>
        <div
          className="position-tag"
          style={{
            backgroundColor:
              selectedWand?.position === "outdoor" ? "var(--himmelsblau)" : "var(--pfirsichorange)",
          }}
        >
          {selectedWand?.position}
        </div>
      </div>
      <ul className="routen-container">
        {routen
          .sort((a, b) => (b.schwierigkeit || 0) - (a.schwierigkeit || 0))
          .map((route) => (
            <li key={route.id}>
              <RoutenErgebnisFeld
                route={route}
                setSelectedRoute={setSelectedRoute}
                setShowNeueRoute={setShowNeueRoute}
              />
            </li>
          ))}
      </ul>
      {isAdmin() && (
        <div className="text-button" onClick={() => setShowNeueRoute(true)}>
          Route hinzufügen
        </div>
      )}
    </article>
  );
};

export default WandInfoBox;
