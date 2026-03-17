import type { Halle, User } from "../../constants/APIResponseTypes";
import Header from "./../Header";
import "../../styles/RoutenKarte.css";
import HallenInfoBox from "./HallenInfoBox";
import TestMap from "./TestMap";
import Knopfsis from "./Knopfsis";
import { useState } from "react";

interface Props {
  selectedHalle: Halle;
  user: User | null;
}

const RoutenKarte = ({ selectedHalle, user }: Props) => {
  const [scale, setScale] = useState(1);

  return (
    <>
      <TestMap scale={scale} setScale={setScale} />
      <Header user={user} />
      <HallenInfoBox selectedHalle={selectedHalle} />
      <Knopfsis scale={scale} setScale={setScale} />
    </>
  );
};

export default RoutenKarte;
