import {
  type Route,
  type Halle,
  type User,
  type Wand,
} from "../../constants/APIResponseTypes";
import Header from "./../Header";
import "../../styles/RoutenKarte.css";
import HallenInfoBox from "./HallenInfoBox";
import SVGMap from "./SVGMap";
import Knopfsis from "./Knopfsis";
import { useEffect, useState } from "react";
import RoutenDetails from "./RoutenDetails";

interface Props {
  selectedHalle: Halle;
  user: User | null;
}

const RoutenKarte = ({ selectedHalle, user }: Props) => {
  const [scale, setScale] = useState(1);
  const [wände, setWände] = useState<Wand[]>([]);
  const [selectedWand, setSelectedWand] = useState<Wand | null>(null);
  const [selectedRoute, setSelectedRoute] = useState<Route | null>(null);

  async function getWände() {
    const response = await fetch(
      "http://localhost:8080/api/waende?hallenId=" + selectedHalle.id,
    );
    const data = (await response.json()) as Wand[];
    if (!response.ok) {
      console.error("Fehler beim Laden der Wände:", response.statusText);
      return;
    }
    console.log("Fetched wände:", data);
    setWände(data);
  }

  useEffect(() => {
    getWände();
  }, []);

  return (
    <>
      <SVGMap
        scale={scale}
        setScale={setScale}
        wände={wände}
        selectedWand={selectedWand}
        setSelectedWand={setSelectedWand}
        setSelectedRoute={setSelectedRoute}
      />
      <Header user={user} />
      <HallenInfoBox selectedHalle={selectedHalle} />
      <Knopfsis scale={scale} setScale={setScale} />
      {selectedRoute && <RoutenDetails selectedRoute={selectedRoute} />}
    </>
  );
};

export default RoutenKarte;
