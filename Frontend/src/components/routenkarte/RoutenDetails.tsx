import {
  convertSchwierigkeitToString,
  type Route,
} from "../../constants/APIResponseTypes";
import "../../styles/RoutenDetails.css";

interface Props {
  selectedRoute: Route;
}

const RoutenDetails = ({ selectedRoute }: Props) => {
  const getDateString = (date: string) => {
    const d = new Date(date);
    return `${d.getDate()}.${d.getMonth() + 1}.${d.getFullYear()}`;
  };

  return (
    <div className="routen-details">
      <div className="routen-details-header">
        <h2>{selectedRoute?.name || selectedRoute?.farbe}</h2>
        <div className="routen-details-schwierigkeit">
          {convertSchwierigkeitToString(selectedRoute.schwierigkeit)}
        </div>
      </div>
      <p>
        geschraubt{" "}
        {selectedRoute.schraubdatum
          ? `am ${getDateString(selectedRoute.schraubdatum)}`
          : "irgendwann"}{" "}
        von {selectedRoute?.schrauber ? selectedRoute.schrauber : "irgendwem"}
      </p>
      <b>Beschreibung</b>
      <p>{selectedRoute.beschreibung || "keine"}</p>
    </div>
  );
};

export default RoutenDetails;
