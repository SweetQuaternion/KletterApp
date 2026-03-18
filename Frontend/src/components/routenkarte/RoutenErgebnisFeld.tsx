import type { Route } from "../../constants/APIResponseTypes";
import { colors, schwierigkeiten } from "../../constants/APIResponseTypes";

interface Props {
  route: Route;
}

const RoutenErgebnisFeld = ({ route }: Props) => {
  return (
    <div className="routen-ergebnis-feld">
      <div className="left">
        <div
          className="mini-dot-colour"
          style={{ backgroundColor: colors[route.farbe] }}
        ></div>
        {route.name ? <p>{route.name}</p> : <p>{route.farbe}</p>}
      </div>
      <div className="right">
        <p>{schwierigkeiten[route.schwierigkeit]}</p>
      </div>
    </div>
  );
};

export default RoutenErgebnisFeld;
