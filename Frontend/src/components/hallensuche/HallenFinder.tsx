import "../../styles/App.css";
import HallenSuchmaske from "./HallenSuchmaske";
import Header from "../Header";
import type { Halle, User } from "../../api/model";

interface Props {
  user: User | null;
  setSelectedHalle: (id: Halle) => void;
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
