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
import NeueRoute from "./NeueRoute";

interface Props {
  selectedHalle: Halle;
  user: User | null;
}

const RoutenKarte = ({ selectedHalle, user }: Props) => {
  const [scale, setScale] = useState(1);
  const [wände, setWände] = useState<Wand[]>([]);
  const [selectedWand, setSelectedWand] = useState<Wand | null>(null);
  const [selectedRoute, setSelectedRoute] = useState<Route | null>(null);
  const [showNeueRoute, setShowNeueRoute] = useState(false);

  async function getWände() {
    const response = await fetch(
      `http://localhost:8080/api/hallen/${selectedHalle.id}/waende`,
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
        setShowNeueRoute={setShowNeueRoute}
      />
      <Header user={user} />
      <HallenInfoBox selectedHalle={selectedHalle} />
      <Knopfsis scale={scale} setScale={setScale} />
      {selectedRoute && <RoutenDetails selectedRoute={selectedRoute} />}
      {showNeueRoute && selectedWand && (
        <NeueRoute
          selectedHalle={selectedHalle}
          selectedWand={selectedWand}
          setShowNeueRoute={setShowNeueRoute}
        />
      )}
    </>
  );
};

export default RoutenKarte;
