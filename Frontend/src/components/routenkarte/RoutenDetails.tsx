import type { Route } from "../../constants/APIResponseTypes";
import "../../styles/RoutenDetails.css";

interface Props {
  selectedRoute: Route | null;
}

const RoutenDetails = ({ selectedRoute }: Props) => {
  return (
    <div className="routen-details">
      <h2>{selectedRoute?.name || selectedRoute?.farbe}</h2>
    </div>
  );
};

export default RoutenDetails;
