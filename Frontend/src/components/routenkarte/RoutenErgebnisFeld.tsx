import type { RouteResponseDTO } from "../../api/model";
import { convertSchwierigkeitToString } from "../../constants/conversions";

interface Props {
  route: RouteResponseDTO;
  setSelectedRoute: (route: RouteResponseDTO | null) => void;
  setShowNeueRoute: (show: boolean) => void;
}

const RoutenErgebnisFeld = ({
  route,
  setSelectedRoute,
  setShowNeueRoute,
}: Props) => {
  return (
    <button
      className="routen-ergebnis-feld"
      onClick={() => {
        (setSelectedRoute(route), setShowNeueRoute(false));
      }}
    >
      <div className="left">
        <div
          className="mini-dot-colour"
          style={{ background: `var(--${route.farbe})` }}
        ></div>
        {route.name ? <p>{route.name}</p> : <p>{route.farbe}</p>}
      </div>
      <div className="right">
        <p>{convertSchwierigkeitToString(route.schwierigkeit || 0)}</p>
      </div>
    </button>
  );
};

export default RoutenErgebnisFeld;
