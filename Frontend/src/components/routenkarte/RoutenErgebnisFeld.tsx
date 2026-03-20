import type { Route } from "../../constants/APIResponseTypes";
import { schwierigkeiten } from "../../constants/APIResponseTypes";

interface Props {
  route: Route;
  setSelectedRoute: (route: Route | null) => void;
}

const RoutenErgebnisFeld = ({ route, setSelectedRoute }: Props) => {
  return (
    <button
      className="routen-ergebnis-feld"
      onClick={() => setSelectedRoute(route)}
    >
      <div className="left">
        <div
          className="mini-dot-colour"
          style={{ background: `var(--${route.farbe})` }}
        ></div>
        {route.name ? <p>{route.name}</p> : <p>{route.farbe}</p>}
      </div>
      <div className="right">
        <p>{schwierigkeiten[route.schwierigkeit]}</p>
      </div>
    </button>
  );
};

export default RoutenErgebnisFeld;
