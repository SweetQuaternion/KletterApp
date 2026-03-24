import type { Route } from "../../constants/APIResponseTypes";
import { convertSchwierigkeitToString } from "../../constants/APIResponseTypes";

interface Props {
  route: Route;
  setSelectedRoute: (route: Route | null) => void;
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
        <p>{convertSchwierigkeitToString(route.schwierigkeit)}</p>
      </div>
    </button>
  );
};

export default RoutenErgebnisFeld;
