import { useContext, useState } from "react";
import { useMutation } from "@tanstack/react-query";
import type { RouteCreateDTO, RouteResponseDTO, WandResponseDTO } from "../../api/model";
import { convertSchwierigkeitToNumber } from "../../constants/conversions";
import "../../styles/RoutenKarte.css";
import {
  createUpdateRouteMutationOptions,
  createDeleteRouteMutationOptions,
} from "../../constants/queries";
import { HalleContext } from "../../constants/context";

interface Props {
  selectedWand: WandResponseDTO;
  selectedRoute: RouteResponseDTO;
  setEditingRoute: (route: RouteResponseDTO | null) => void;
}

const RouteBearbeiten = ({ selectedWand, selectedRoute, setEditingRoute }: Props) => {
  const { selectedHalle } = useContext(HalleContext);
  const { mutate: update, isSuccess, isError } = useMutation(createUpdateRouteMutationOptions());
  const {
    mutate: deleteRoute,
    isSuccess: isDeleteSuccess,
    isError: isDeleteError,
  } = useMutation(createDeleteRouteMutationOptions());
  const [löschenAttempted, setLöschenAttempted] = useState(false);

  const handleRoutenSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const data = {
      hallenId: selectedHalle!.id,
      wandNr: selectedWand.wandNr,
      name: formData.get("name") as string,
      farbe: formData.get("farbe") as string,
      schwierigkeit: convertSchwierigkeitToNumber(formData.get("schwierigkeit") as string),
      isToprope: formData.get("is_toprope") === "on",
      isVorstieg: formData.get("is_vorstieg") === "on",
      schrauber: formData.get("schrauber") as string,
      schraubdatum: formData.get("schraubdatum") as string,
      beschreibung: formData.get("beschreibung") as string,
    } as RouteCreateDTO;
    update({ route: data, id: selectedRoute.id });
  };

  const handleLöschen = () => {
    deleteRoute({ hallenId: selectedHalle!.id, id: selectedRoute.id });
  };

  return (
    <div className="white-box top right bottom routen-details">
      <h2>Route bearbeiten</h2>
      <form onSubmit={handleRoutenSubmit}>
        <div className="form-group">
          <label htmlFor="name">Name:</label>
          <input
            type="text"
            id="name"
            name="name"
            autoComplete="off"
            defaultValue={selectedRoute.name}
          />
        </div>
        <div className="form-group">
          <label htmlFor="farbe">Farbe:</label>
          <input
            type="text"
            id="farbe"
            name="farbe"
            autoComplete="off"
            defaultValue={selectedRoute.farbe}
          />
        </div>
        <div className="form-group">
          <label htmlFor="schwierigkeit">Schwierigkeit:</label>
          <input
            type="text"
            id="schwierigkeit"
            name="schwierigkeit"
            autoComplete="off"
            defaultValue={selectedRoute.schwierigkeit}
          />
        </div>
        <div className="form-group checkbox-container">
          <input
            type="checkbox"
            id="is_toprope"
            name="is_toprope"
            defaultChecked={selectedRoute.isToprope}
          />
          <label htmlFor="is_toprope">Toprope</label>
          <input
            type="checkbox"
            id="is_vorstieg"
            name="is_vorstieg"
            defaultChecked={selectedRoute.isVorstieg}
          />
          <label htmlFor="is_vorstieg">Vorstieg</label>
        </div>
        <div className="form-group">
          <label htmlFor="schrauber">Schrauber:</label>
          <input
            type="text"
            id="schrauber"
            name="schrauber"
            autoComplete="off"
            defaultValue={selectedRoute.schrauber}
          />
        </div>
        <div className="form-group">
          <label htmlFor="schraubdatum">Schraubdatum:</label>
          <input
            type="date"
            id="schraubdatum"
            name="schraubdatum"
            autoComplete="off"
            defaultValue={
              selectedRoute.schraubdatum
                ? new Date(selectedRoute.schraubdatum).toISOString().split("T")[0]
                : ""
            }
          />
        </div>
        <div className="form-group">
          <label htmlFor="beschreibung">Beschreibung:</label>
          <textarea
            name="beschreibung"
            id="beschreibung"
            rows={5}
            autoComplete="off"
            defaultValue={selectedRoute.beschreibung}
          ></textarea>
        </div>
        <div className="flex-row wrap button-container">
          <button type="submit">Route speichern</button>
          <button type="button" onClick={() => setEditingRoute(null)}>
            Abbrechen
          </button>
          <button type="button" onClick={() => setLöschenAttempted(!löschenAttempted)}>
            Route deaktivieren
          </button>
        </div>
        {löschenAttempted && (
          <div className="flex-row wrap small-gap">
            <div className="small sans-serif">Bist du sicher?</div>
            <div className="bearbeiten sans-serif" onClick={handleLöschen}>
              ja
            </div>
            <div className="bearbeiten sans-serif" onClick={() => setLöschenAttempted(false)}>
              nein
            </div>
          </div>
        )}
        {isSuccess && <p className="small">Route erfolgreich gespeichert!</p>}
        {isError && <p className="small">Fehler beim Speichern der Route</p>}
        {isDeleteSuccess && <p className="small">Route erfolgreich deaktiviert!</p>}
        {isDeleteError && <p className="small">Fehler beim Deaktivieren der Route</p>}
      </form>
    </div>
  );
};

export default RouteBearbeiten;
