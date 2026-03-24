import { useState } from "react";
import {
  convertSchwierigkeitToString,
  type Route,
} from "../../constants/APIResponseTypes";
import { isAuthenticated } from "../../constants/keycloak";
import "../../styles/RoutenDetails.css";

interface Props {
  selectedRoute: Route;
}

const RoutenDetails = ({ selectedRoute }: Props) => {
  const [ascentToggled, setAscentToggled] = useState(false);

  const getDateString = (date: string) => {
    const d = new Date(date);
    return `${d.getDate()}.${d.getMonth() + 1}.${d.getFullYear()}`;
  };

  const handleAscent = (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log("Ascent submitted");
    // Handle ascent logic here
  };

  return (
    <div className="routen-details">
      <div className="routen-details-header">
        <h2>{selectedRoute?.name || "Route"}</h2>

        <div className="routen-details-schwierigkeit">
          {convertSchwierigkeitToString(selectedRoute.schwierigkeit)}
        </div>
      </div>
      <div
        className="tag-colour"
        style={{
          background: `var(--${selectedRoute.farbe})`,
          color: `${selectedRoute.farbe === "schwarz" ? "white" : "black"}`,
          border: `${selectedRoute.farbe === "weiß" ? "1px solid black" : "none"}`,
        }}
      >
        {selectedRoute.farbe}
      </div>
      <p className="small">
        geschraubt{" "}
        {selectedRoute.schraubdatum
          ? `am ${getDateString(selectedRoute.schraubdatum)}`
          : "irgendwann"}{" "}
        von {selectedRoute?.schrauber ? selectedRoute.schrauber : "irgendwem"}
      </p>

      {/* Hier kommen noch Favorit, Projekt und Geschafft-Button hin, wenn angemeldet */}

      {isAuthenticated() && (
        <>
          <div className="routen-details-badges">
            <button>Favorit</button>
            <button>Projekt</button>
            <button onClick={() => setAscentToggled(!ascentToggled)}>
              Geschafft
            </button>
          </div>
          {ascentToggled && (
            <form onSubmit={handleAscent}>
              <div className="form-group">
                <label htmlFor="sicherungsart">Sicherungsart</label>
                <select name="sicherungsart" id="sicherungsart">
                  <option value="toprope">Toprope</option>
                  <option value="vorstieg">Vorstieg</option>
                </select>
              </div>
              <div className="form-group">
                <label htmlFor="Style">Style</label>
                <select name="style" id="style">
                  <option value="onsight">onsight</option>
                  <option value="flash">flash</option>
                  <option value="redpoint">redpoint</option>
                  <option value="pinkpoint">pinkpoint</option>
                  <option value="toprope">toprope</option>
                  <option value="hangdog">hangdog</option>
                  <option value="attempt">attempt</option>
                </select>
              </div>
              <button type="submit">Speichern</button>
            </form>
          )}
        </>
      )}

      <b>Beschreibung</b>
      <p className="small">{selectedRoute.beschreibung || "keine"}</p>
    </div>
  );
};

export default RoutenDetails;
