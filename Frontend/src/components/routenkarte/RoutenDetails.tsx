import { useState } from "react";
import { convertSchwierigkeitToString } from "../../constants/conversions";
import "../../styles/RoutenDetails.css";
import type {
  AscentSicherung,
  AscentStyle,
  Route,
  User,
} from "../../api/model";
import { useMutation } from "@tanstack/react-query";
import {
  createAddAscentMutationOptions,
  createAddKommentarMutationOptions,
} from "../../constants/queries";
import { Link } from "react-router";

interface Props {
  selectedRoute: Route;
  user: User | null;
}

const RoutenDetails = ({ selectedRoute, user }: Props) => {
  const [ascentToggled, setAscentToggled] = useState(false);
  const {
    mutate: addAscent,
    isSuccess: isAscentSuccess,
    isError: isAscentError,
  } = useMutation(createAddAscentMutationOptions());

  const {
    mutate: addKommentar,
    isSuccess: isKommentarSuccess,
    isError: isKommentarError,
  } = useMutation(createAddKommentarMutationOptions());

  const getDateString = (date: string) => {
    const d = new Date(date);
    return `${d.getDate()}.${d.getMonth() + 1}.${d.getFullYear()}`;
  };

  const handleAscent = (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    const input = new FormData(e.target as HTMLFormElement);
    addAscent({
      userId: user?.keycloakId || "",
      routenId: selectedRoute.id,
      sicherung: input.get("sicherungsart") as AscentSicherung,
      style: input.get("style") as AscentStyle,
      datum: new Date().toISOString().split("T")[0],
    });
    setAscentToggled(false);
  };

  const handleKommentarSubmit = (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    const input = new FormData(e.target as HTMLFormElement);
    addKommentar({
      user: user!,
      route: selectedRoute,
      datum: new Date().toISOString(),
      text: input.get("text") as string,
    });
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
          {isAscentSuccess && <p className="small">Gespeichert!</p>}
          {isAscentError && (
            <p className="small">Etwas ist schiefgelaufen...</p>
          )}
          <b>Beschreibung</b>
          <p className="small">{selectedRoute.beschreibung || "keine"}</p>

          <b>Kommentare</b>
          {selectedRoute.kommentare && selectedRoute.kommentare.length > 0 ? (
            selectedRoute.kommentare.map((kommentar) => (
              <div key={kommentar.id} className="kommentar">
                <Link
                  to={`/user/${kommentar.user.name}`}
                  className="kommentar-user"
                >
                  <p className="kommentar-name">{kommentar.user.name}</p>
                </Link>
                <p className="kommentar-text">{kommentar.text}</p>
                {/* {(isAdmin() ||
                  user?.keycloakId === kommentar.user.keycloakId) && (
                  <p className="bearbeiten">löschen</p>
                )} */}
              </div>
            ))
          ) : (
            <p className="small">keine</p>
          )}

          {user && (
            <>
              <b>Schreib ein Kommentar!</b>
              <form onSubmit={handleKommentarSubmit}>
                <textarea
                  name="text"
                  id="text"
                  placeholder="Deine eloquenten Gedanken..."
                  rows={4}
                />
                <button type="submit">Absenden</button>
              </form>
              {isKommentarSuccess && <p className="small">Gespeichert!</p>}
              {isKommentarError && (
                <p className="small">Etwas ist schiefgelaufen...</p>
              )}
            </>
          )}
        </>
      )}
    </div>
  );
};

export default RoutenDetails;
