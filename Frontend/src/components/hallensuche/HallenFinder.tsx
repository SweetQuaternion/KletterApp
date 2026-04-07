import "../../styles/App.css";
import HallenSuchmaske from "./HallenSuchmaske";
import Header from "../Header";
import type { HalleResponseDTO, User } from "../../api/model";

interface Props {
  user: User | null;
  setSelectedHalle: (id: HalleResponseDTO) => void;
}

function HallenFinder({ user, setSelectedHalle }: Props) {
  return (
    <>
      <Header user={user} />
      <HallenSuchmaske setSelectedHalle={setSelectedHalle} />
    </>
  );
}

export default HallenFinder;
