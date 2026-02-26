import type { Halle } from "../constants/APIResponseTypes";
import "../styles/App.css";
import HallenSuchmaske from "./HallenSuchmaske";
import Header from "./Header";

interface Props {
  setSelectedHalle: (id: Halle) => void;
}

function HallenFinder({ setSelectedHalle }: Props) {
  return (
    <>
      <Header />
      <HallenSuchmaske setSelectedHalle={setSelectedHalle} />
    </>
  );
}

export default HallenFinder;
