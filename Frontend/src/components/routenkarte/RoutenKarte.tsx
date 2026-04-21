import {
  type HalleResponseDTO,
  type RouteResponseDTO,
  type UserResponseDTO,
  type WandResponseDTO,
} from "../../api/model";
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
import { isAdmin } from "../../constants/keycloak";
import { Link } from "react-router-dom";

interface Props {
  selectedHalle: HalleResponseDTO;
  user: UserResponseDTO | null;
}

const RoutenKarte = ({ selectedHalle, user }: Props) => {
  const [scale, setScale] = useState(1);
  const [selectedWand, setSelectedWand] = useState<WandResponseDTO | null>(
    null,
  );
  const [selectedRoute, setSelectedRoute] = useState<RouteResponseDTO | null>(
    null,
  );
  const [showNeueRoute, setShowNeueRoute] = useState(false);

  const { data } = useQuery(
    getGetWaendeByHallenIdQueryOptions(selectedHalle.id),
  );

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
      {selectedRoute && (
        <RoutenDetails
          selectedRoute={selectedRoute}
          setSelectedRoute={setSelectedRoute}
          user={user}
        />
      )}
      {showNeueRoute && selectedWand && (
        <NeueRoute
          selectedHalle={selectedHalle}
          selectedWand={selectedWand}
          setShowNeueRoute={setShowNeueRoute}
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
