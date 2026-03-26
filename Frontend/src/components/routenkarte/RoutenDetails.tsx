import { useState } from "react";
import { convertSchwierigkeitToString } from "../../constants/conversions";
import "../../styles/RoutenDetails.css";
import type { AscentSicherung, AscentStyle, Route, User } from "../../api/model";
import { useMutation } from "@tanstack/react-query";
import { createAddAscentMutationOptions } from "../../constants/queries";

interface Props {
  selectedRoute: Route;
  user: User | null;
}

const RoutenDetails = ({ selectedRoute, user }: Props) => {
  const [ascentToggled, setAscentToggled] = useState(false);
  const { mutate, isSuccess, isError } = useMutation(
    createAddAscentMutationOptions(),
  );

  const getDateString = (date: string) => {
    const d = new Date(date);
    return `${d.getDate()}.${d.getMonth() + 1}.${d.getFullYear()}`;
  };

  const handleAscent = (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    const input = new FormData(e.target as HTMLFormElement);
    mutate(
      {
        userId: user?.keycloakId || "",
        routenId: selectedRoute.id,
        sicherung: input.get("sicherungsart") as AscentSicherung,
        style: input.get("style") as AscentStyle,
        datum: new Date().toISOString().split("T")[0],
      },
    );
    setAscentToggled(false);
  };

  return (
    <div className="routen-details">
      <div className="routen-details-header">
        <h2>{selectedRoute?.name || "Route"}</h2>

        <div className="routen-details-schwierigkeit">
          {convertSchwierigkeitToString(selectedRoute.schwierigkeit || 0)}
        </div>
      </div>
      <div
        className="tag-colour"
        style={{
          background: `var(--${selectedRoute.farbe})`,
          color: `${selectedRoute.farbe === "schwarz" ? "white" : "black"}`,
          border: `${selectedRoute.farbe === "weiß" ? "1px solid grey" : "none"}`,
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

      {user && (
        <>
          <div className="routen-details-badges">
            <button className="favorit" title="Als Favorit markieren">
              <div className="icon-wrapper">
                <img src="/favorite.svg" />
              </div>
            </button>
            <button className="projekt" title="Als Projekt markieren">
              <div className="icon-wrapper">
                <img src="/projekt.svg" />
              </div>
            </button>
            <button
              onClick={() => setAscentToggled(!ascentToggled)}
              title="Als geschafft markieren"
            >
              Geschafft
            </button>
          </div>
          {ascentToggled && (
            <>
              <b>Glückwunsch!</b>
              <form onSubmit={handleAscent}>
                <div className="form-group">
                  <label htmlFor="sicherungsart">Sicherungsart</label>
                  <select name="sicherungsart" id="sicherungsart">
                    <option value="vorstieg">Vorstieg</option>
                    <option value="toprope">Toprope</option>
                    <option value="solo">Solo</option>
                  </select>
                </div>
                <div className="form-group">
                  <label htmlFor="style">Style</label>
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
            </>
          )}
        </>
      )}

      {!ascentToggled && (
        <>
          {isSuccess && <p className="small">Gespeichert!</p>}
          {isError && <p className="small">Etwas ist schiefgelaufen...</p>}
          <b>Beschreibung</b>
          <p className="small">{selectedRoute.beschreibung || "keine"}</p>

          <b>Kommentare</b>
          <p className="small">keine</p>
        </>
      )}
    </div>
  );
};

export default RoutenDetails;
