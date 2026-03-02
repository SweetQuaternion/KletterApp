import type { Halle } from "../../constants/APIResponseTypes";

interface Props {
  selectedHalle: Halle;
}

const HallenInfoBox = ({ selectedHalle }: Props) => {
  return (
    <div className="hallen-infobox">
      <h2>{selectedHalle.name}</h2>
      <p>{selectedHalle.betreiber}</p>
      <p>{selectedHalle.adresse}</p>
    </div>
  );
};

export default HallenInfoBox;
