import { useState } from "react";
import type { HalleResponseDTO } from "../../api/model";

interface Props {
  selectedHalle: HalleResponseDTO;
}

const HallenInfoBox = ({ selectedHalle }: Props) => {
  const [hidden, setHidden] = useState(false);

  return (
    <>
      <button className="menu map top left" onClick={() => setHidden(false)}>
        ⌂
      </button>
      <div
        className={`white-box top left hallen-info-box ${hidden ? "hidden" : ""}`}
      >
        <button className="close-button">
          <div onClick={() => setHidden(true)}>×</div>
        </button>
        <h2>{selectedHalle.name}</h2>
        <p>{selectedHalle.betreiber}</p>
        <p>{selectedHalle.adresse}</p>
      </div>
    </>
  );
};

export default HallenInfoBox;
