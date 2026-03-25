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
import { useState } from "react";
import RoutenDetails from "./RoutenDetails";
import NeueRoute from "./NeueRoute";
import { createWändeQueryOptions } from "../../constants/queries";
import { useQuery } from "@tanstack/react-query";

interface Props {
  selectedHalle: Halle;
  user: User | null;
}

const RoutenKarte = ({ selectedHalle, user }: Props) => {
  const [scale, setScale] = useState(1);
  const [selectedWand, setSelectedWand] = useState<Wand | null>(null);
  const [selectedRoute, setSelectedRoute] = useState<Route | null>(null);
  const [showNeueRoute, setShowNeueRoute] = useState(false);

  const { data } = useQuery(createWändeQueryOptions(selectedHalle.id));

  return (
    <>
      <SVGMap
        scale={scale}
        setScale={setScale}
        wände={data || []}
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
