import { useContext, useState } from "react";
import { convertSchwierigkeitToString, getColor } from "../../constants/conversions";
import "../../styles/RoutenDetails.css";
import type { AscentResponseDTOStyle, RouteResponseDTO } from "../../api/model";
import { useMutation, useQuery } from "@tanstack/react-query";
import {
  createAddAscentMutationOptions,
  createAddKommentarMutationOptions,
  createAscentQueryOptions,
  createKommentarQueryOptions,
  createUserRoutenStatusMutationOptions,
  createUserRoutenStatusQueryOptions,
} from "../../constants/queries";
import { Link } from "react-router";
import { isAdmin } from "../../constants/keycloak";
import { UserContext } from "../../constants/context.ts";
import { useOnline } from "../../constants/useOnline.ts";

interface Props {
  selectedRoute: RouteResponseDTO;
  setSelectedRoute: (route: RouteResponseDTO | null) => void;
  setEditingRoute: (route: RouteResponseDTO | null) => void;
}

const RoutenDetails = ({ selectedRoute, setSelectedRoute, setEditingRoute }: Props) => {
  const user = useContext(UserContext);
  const isOnline = useOnline();
  const [ascentToggled, setAscentToggled] = useState(false);

  const { data: userRoutenStatus } = useQuery(
    createUserRoutenStatusQueryOptions(selectedRoute.id, user),
  );

  const { data: ascents } = useQuery(createAscentQueryOptions(selectedRoute.id, user));

  const { data: kommentare } = useQuery({
    ...createKommentarQueryOptions(selectedRoute.id),
    enabled: isOnline,
  });

  const {
    mutate: updateStatus,
    isSuccess: isStatusSuccess,
    isError: isStatusError,
  } = useMutation(createUserRoutenStatusMutationOptions());

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
      style: input.get("style") as AscentResponseDTOStyle,
      datum: new Date().toISOString().split("T")[0],
    });
    setAscentToggled(false);
  };

  const handleKommentarSubmit = (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    const input = new FormData(e.target as HTMLFormElement);
    addKommentar({
      userId: user?.keycloakId || "",
      routenId: selectedRoute.id,
      text: input.get("text") as string,
    });
  };

  return (
    <div className="white-box top right bottom routen-details space-between">
      <button className="close-button">
        <div onClick={() => setSelectedRoute(null)}>×</div>
      </button>
      <div className="top-section">
        <div className="routen-details-header">
          <h2>{selectedRoute?.name || "Route"}</h2>
          <div className="routen-details-schwierigkeit">
            {convertSchwierigkeitToString(selectedRoute.schwierigkeit || undefined)}
          </div>
        </div>
        <div className="flex-row small-gap">
          {selectedRoute.farbe && (
            <div
              className="tag-colour"
              style={{
                background: getColor(selectedRoute.farbe),
                color: `${selectedRoute.farbe === "schwarz" ? "white" : "black"}`,
                border: `${selectedRoute.farbe === "weiß" ? "1px solid grey" : "none"}`,
              }}
            >
              {selectedRoute.farbe}
            </div>
          )}
          <p className="small">
            {selectedRoute.isToprope ? "Toprope" : ""}{" "}
            {selectedRoute.isToprope && selectedRoute.isVorstieg ? " / " : ""}{" "}
            {selectedRoute.isVorstieg ? "Vorstieg" : ""}
          </p>
          {ascents && ascents?.length !== 0 && (
            <img src="/tick.svg" alt="geschafft" className="geschafft-haken" />
          )}
        </div>
        <p className="small">
          geschraubt{" "}
          {selectedRoute.schraubdatum
            ? `am ${getDateString(selectedRoute.schraubdatum)}`
            : "irgendwann"}{" "}
          von {selectedRoute?.schrauber ? selectedRoute.schrauber : "irgendwem"}
        </p>

        <div className="routen-details-badges">
          <button
            className={`favorit ${userRoutenStatus?.isFavorit ? "active" : ""}`}
            title="Als Favorit markieren"
            onClick={() =>
              updateStatus({
                ...userRoutenStatus,
                isFavorit: !userRoutenStatus?.isFavorit,
              })
            }
          >
            <div className="icon-wrapper">
              <img src="/favorite.svg" />
            </div>
          </button>
          <button
            className={`projekt ${userRoutenStatus?.isProjekt ? "active" : ""}`}
            title="Als Projekt markieren"
            onClick={() =>
              updateStatus({
                ...userRoutenStatus,
                isProjekt: !userRoutenStatus?.isProjekt,
              })
            }
          >
            <div className="icon-wrapper">
              <img src="/projekt.svg" />
            </div>
          </button>
          <button onClick={() => setAscentToggled(!ascentToggled)} title="Als geschafft markieren">
            Geschafft
          </button>
        </div>
        {ascentToggled && (
          <>
            <b>Glückwunsch!</b>
            <form onSubmit={handleAscent}>
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

        {!ascentToggled && (
          <>
            {isAscentSuccess && <p className="small">Gespeichert!</p>}
            {isAscentError && <p className="small">Etwas ist schiefgelaufen...</p>}
            <b>Beschreibung</b>
            <p className="small">{selectedRoute.beschreibung || "keine"}</p>
            {navigator.onLine && (
              <>
                <b>Kommentare</b>
                {kommentare && kommentare.length > 0 ? (
                  kommentare.map((kommentar) => (
                    <div key={kommentar.id} className="kommentar">
                      <Link to={`/user/${kommentar.username}`} className="kommentar-user">
                        <p className="kommentar-name">{kommentar.username}</p>
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
              </>
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
                    required
                  />
                  <button type="submit">Absenden</button>
                </form>
                {(isKommentarSuccess || isStatusSuccess) && <p className="small">Gespeichert!</p>}
                {(isKommentarError || isStatusError) && (
                  <p className="small">Etwas ist schiefgelaufen...</p>
                )}
              </>
            )}
          </>
        )}
      </div>
      {isAdmin() && (
        <div className="bottom-section">
          <div className="hinzufügen" onClick={() => setEditingRoute(selectedRoute)}>
            Route bearbeiten
          </div>
        </div>
      )}
    </div>
  );
};

export default RoutenDetails;
