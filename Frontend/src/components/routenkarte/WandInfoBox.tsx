import type { Route, Wand } from "../../constants/APIResponseTypes";
import RoutenErgebnisFeld from "./RoutenErgebnisFeld";
import { isAdmin } from "../../constants/keycloak.ts";

interface Props {
  selectedWand: Wand;
  routen: Route[];
  setSelectedRoute: (route: Route | null) => void;
  setShowNeueRoute: (show: boolean) => void;
}

const WandInfoBox = ({
  selectedWand,
  routen,
  setSelectedRoute,
  setShowNeueRoute,
}: Props) => {
  return (
    <div className="wand-infobox">
      <div className="wand-infobox-header">
        <h3>Wand {selectedWand?.wandNr}</h3>
        <div
          className="position-tag"
          style={{
            backgroundColor:
              selectedWand?.position === "outdoor"
                ? "var(--himmelsblau)"
                : "var(--pfirsichorange)",
          }}
        >
          {selectedWand?.position}
        </div>
      </div>
      <div className="routen-container">
        {routen.map((route) => (
          <RoutenErgebnisFeld
            key={route.id}
            route={route}
            setSelectedRoute={setSelectedRoute}
          />
        ))}
      </div>
      {isAdmin() && (
        <div
          className="route-hinzufügen"
          onClick={() => setShowNeueRoute(true)}
        >
          Route hinzufügen
        </div>
      )}
    </div>
  );
};

export default WandInfoBox;
