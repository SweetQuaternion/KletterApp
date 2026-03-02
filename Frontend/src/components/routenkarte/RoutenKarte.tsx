import type { Halle } from "../../constants/APIResponseTypes";
import Header from "./../Header";
import "../../styles/RoutenKarte.css";
import HallenInfoBox from "./HallenInfoBox";
import TestMap from "./TestMap";

interface Props {
  selectedHalle: Halle;
}

const RoutenKarte = ({ selectedHalle }: Props) => {
  return (
    <>
      {/* <Header /> */}
      {/* <HallenInfoBox selectedHalle={selectedHalle} /> */}
      <TestMap />
    </>
  );
};

export default RoutenKarte;
