import "../../styles/App.css";
import HallenSuchmaske from "./HallenSuchmaske";
import type { HalleResponseDTO } from "../../api/model";

interface Props {
  setSelectedHalle: (id: HalleResponseDTO) => void;
}

function HallenFinder({ setSelectedHalle }: Props) {
  return (
    <>
      <HallenSuchmaske setSelectedHalle={setSelectedHalle} />
    </>
  );
}

export default HallenFinder;
