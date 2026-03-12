import type { Halle, User } from "../../constants/APIResponseTypes";
import Header from "./../Header";
import "../../styles/RoutenKarte.css";
import HallenInfoBox from "./HallenInfoBox";
import TestMap from "./TestMap";

interface Props {
  selectedHalle: Halle;
  user: User | null;
}

const RoutenKarte = ({ selectedHalle, user }: Props) => {
  return (
    <>
      <Header user={user} />
      <HallenInfoBox selectedHalle={selectedHalle} />
      <TestMap />
    </>
  );
};

export default RoutenKarte;
