import type { Halle, User } from "../../constants/APIResponseTypes";
import "../../styles/App.css";
import HallenSuchmaske from "./HallenSuchmaske";
import Header from "../Header";

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
