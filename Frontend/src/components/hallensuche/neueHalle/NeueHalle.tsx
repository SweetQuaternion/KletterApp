import type { UserResponseDTO, WandCreateDTO } from "../../../api/model";
import Header from "../../Header";
import NeueHalleBox from "./NeueHalleBox";
import "../../../styles/NeueHalle.css";
import Canvas from "./Canvas";
// import WändeBox from "./WändeBox";

interface Props {
  user: UserResponseDTO | null;
}

const NeueHalle = ({ user }: Props) => {
  var wände: WandCreateDTO[] = [];

  return (
    <>
      <Canvas wände={wände} />
      <Header user={user} />
      <div className="halle-details"></div>
      <NeueHalleBox />
      {/* <WändeBox wände={wände} /> */}
      <div className="erklärung top-right">
        <p>Klicke um eine Wand zu zeichnen</p>
        <p>Drücke ESC zum Abbrechen</p>
        <p>Drücke Shift zum Einrasten an Gitterlinien</p>
      </div>
      <button className="speichern-knopfsi">Halle speichern</button>
    </>
  );
};

export default NeueHalle;
