import type { HalleResponseDTO } from "../../api/model";

interface Props {
  selectedHalle: HalleResponseDTO;
}

const HallenInfoBox = ({ selectedHalle }: Props) => {
  return (
    <div className="white-box top left">
      <h2>{selectedHalle.name}</h2>
      <p>{selectedHalle.betreiber}</p>
      <p>{selectedHalle.adresse}</p>
    </div>
  );
};

export default HallenInfoBox;
