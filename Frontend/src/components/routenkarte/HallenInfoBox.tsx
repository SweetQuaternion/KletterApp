import { useContext, useState } from "react";
import { HalleContext } from "../../constants/context";

const HallenInfoBox = () => {
  const { selectedHalle } = useContext(HalleContext);
  const [hidden, setHidden] = useState(false);

  if (!selectedHalle) {
    return null;
  }

  return (
    <>
      <button className="menu map top left" onClick={() => setHidden(false)}>
        ⌂
      </button>
      <section className={`white-box top left hallen-info-box ${hidden ? "hidden" : ""}`}>
        <button className="close-button">
          <div onClick={() => setHidden(true)}>×</div>
        </button>
        <h2>{selectedHalle.name}</h2>
        <p>{selectedHalle.betreiber}</p>
        <p>{selectedHalle.adresse}</p>
      </section>
    </>
  );
};

export default HallenInfoBox;
