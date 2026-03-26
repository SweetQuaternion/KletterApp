import { type Route, type Halle, type User, type Wand } from "../../api/model";
import Header from "./../Header";
import "../../styles/RoutenKarte.css";
import HallenInfoBox from "./HallenInfoBox";
import SVGMap from "./SVGMap";
import Knopfsis from "./Knopfsis";
import { useState } from "react";
import RoutenDetails from "./RoutenDetails";
import NeueRoute from "./NeueRoute";
import { useQuery } from "@tanstack/react-query";
import { getGetWaendeByHallenIdQueryOptions } from "../../api/wand-controller/wand-controller";

interface Props {
  selectedHalle: Halle;
  user: User | null;
}

const RoutenKarte = ({ selectedHalle, user }: Props) => {
  const [scale, setScale] = useState(1);
  const [selectedWand, setSelectedWand] = useState<Wand | null>(null);
  const [selectedRoute, setSelectedRoute] = useState<Route | null>(null);
  const [showNeueRoute, setShowNeueRoute] = useState(false);

  const { data } = useQuery(
    getGetWaendeByHallenIdQueryOptions(selectedHalle.id),
  );

  return (
    <>
      <SVGMap
        scale={scale}
        setScale={setScale}
        wände={data?.data || []}
        selectedWand={selectedWand}
        setSelectedWand={setSelectedWand}
        setSelectedRoute={setSelectedRoute}
        setShowNeueRoute={setShowNeueRoute}
      />
      <Header user={user} />
      <HallenInfoBox selectedHalle={selectedHalle} />
      <Knopfsis scale={scale} setScale={setScale} />
      {selectedRoute && (
        <RoutenDetails selectedRoute={selectedRoute} user={user} />
      )}
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
