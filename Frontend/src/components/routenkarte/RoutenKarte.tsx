import { type RouteResponseDTO, type WandResponseDTO } from "../../api/model";
import "../../styles/RoutenKarte.css";
import HallenInfoBox from "./HallenInfoBox";
import SVGMap from "./SVGMap";
import Knopfsis from "./Knopfsis";
import { useContext, useState } from "react";
import RoutenDetails from "./RoutenDetails";
import NeueRoute from "./NeueRoute";
import { useQuery } from "@tanstack/react-query";
import { createWaendeByHallenIdQueryOptions } from "../../constants/queries";
import { isAdmin } from "../../constants/keycloak";
import { Link } from "react-router-dom";
import RouteBearbeiten from "./RouteBearbeiten";
import { HalleContext } from "../../constants/context";
import SpeicherKnopfsis from "./SpeicherKnopfsis";

const RoutenKarte = () => {
  const { selectedHalle } = useContext(HalleContext);

  const [scale, setScale] = useState(1);
  const [selectedWand, setSelectedWand] = useState<WandResponseDTO | null>(null);
  const [selectedRoute, setSelectedRoute] = useState<RouteResponseDTO | null>(null);
  const [showNeueRoute, setShowNeueRoute] = useState(false);
  const [editingRoute, setEditingRoute] = useState<RouteResponseDTO | null>(null);

  const { data } = useQuery(createWaendeByHallenIdQueryOptions(selectedHalle!.id));

  if (!selectedHalle) {
    return null;
  }

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
        setEditingRoute={setEditingRoute}
      />
      <SpeicherKnopfsis />
      <HallenInfoBox />
      <Knopfsis scale={scale} setScale={setScale} />

      {selectedRoute && (
        <RoutenDetails
          selectedRoute={selectedRoute}
          setSelectedRoute={setSelectedRoute}
          setEditingRoute={setEditingRoute}
        />
      )}
      {showNeueRoute && selectedWand && (
        <NeueRoute selectedWand={selectedWand} setShowNeueRoute={setShowNeueRoute} />
      )}
      {editingRoute && selectedWand && (
        <RouteBearbeiten
          selectedWand={selectedWand}
          selectedRoute={editingRoute}
          setEditingRoute={setEditingRoute}
        />
      )}
      {isAdmin() && (
        <Link to="/routenkarte/edit">
          <div className="speichern-knopfsi">
            <button>Bearbeiten</button>
          </div>
        </Link>
      )}
    </>
  );
};

export default RoutenKarte;
