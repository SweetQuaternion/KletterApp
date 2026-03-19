import type { Route, Wand } from "../../constants/APIResponseTypes";
import RoutenErgebnisFeld from "./RoutenErgebnisFeld";

interface Props {
  selectedWand: Wand;
  routen: Route[];
  setSelectedRoute: (route: Route | null) => void;
}

const WandInfoBox = ({ selectedWand, routen, setSelectedRoute }: Props) => {
  return (
    <div
      className="wand-infobox"
      // style={{ top: `${position?.y}`, left: `${position?.x}` }}
    >
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
    </div>
  );
};

export default WandInfoBox;
