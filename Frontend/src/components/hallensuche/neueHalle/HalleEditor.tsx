import type { UserResponseDTO, WandCreateDTO } from "../../../api/model";
import Header from "../../Header";
import NeueHalleBox from "./NeueHalleBox";
import "../../../styles/NeueHalle.css";
import Canvas from "./Canvas";
import WändeBox from "./WändeBox";
import { useState } from "react";

interface Props {
  user: UserResponseDTO | null;
}

const HalleEditor = ({ user }: Props) => {
  const [wände, setWände] = useState<WandCreateDTO[]>([]);

  function saveHalle() {
    console.log(wände);
  }

  return (
    <>
      <Canvas wände={wände} setWände={setWände} />
      <Header user={user} />
      <div className="halle-details"></div>
      <div className="details-container">
        <NeueHalleBox />
        <WändeBox wände={wände} setWände={setWände} />
      </div>
      <div className="erklärung top right">
        <p>Klicke um eine Wand zu zeichnen</p>
        <p>Drücke ESC zum Abbrechen</p>
        <p>Drücke Shift zum Einrasten an Gitterlinien</p>
      </div>
      <button className="speichern-knopfsi" onClick={saveHalle}>
        Halle speichern
      </button>
    </>
  );
};

export default HalleEditor;
