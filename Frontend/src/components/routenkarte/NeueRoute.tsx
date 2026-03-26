import { useMutation, useQueryClient } from "@tanstack/react-query";
import type { Halle, Route, Wand } from "../../api/model";
import { convertSchwierigkeitToNumber } from "../../constants/conversions";
import "../../styles/RoutenKarte.css";
import { createAddRouteMutationOptions } from "../../constants/queries";

interface Props {
  selectedHalle: Halle;
  selectedWand: Wand;
  setShowNeueRoute: (show: boolean) => void;
}

const NeueRoute = ({ selectedHalle, selectedWand }: Props) => {
  const queryClient = useQueryClient();
  const { mutate, isSuccess, isError } = useMutation(
    createAddRouteMutationOptions(),
  );

  const handleRoutenSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const data = {
      wand: { hallenId: selectedHalle.id, wandNr: selectedWand.wandNr },
      name: formData.get("name") as string,
      farbe: formData.get("farbe") as string,
      schwierigkeit: convertSchwierigkeitToNumber(
        formData.get("schwierigkeit") as string,
      ),
      is_toprope: formData.get("is_toprope") === "on",
      is_vorstieg: formData.get("is_vorstieg") === "on",
      schrauber: formData.get("schrauber") as string,
      schraubdatum: formData.get("schraubdatum") as string,
      is_active: true,
      beschreibung: formData.get("beschreibung") as string,
    } as Route;
    console.log("Submitting new route:", data);
    mutate(data);
    queryClient.invalidateQueries({ queryKey: ["waende", selectedHalle.id] });
  };

  return (
    <div className="routen-details">
      <h2>Neue Route</h2>
      <form onSubmit={handleRoutenSubmit}>
        <div className="form-group">
          <label htmlFor="name">Name:</label>
          <input type="text" id="name" name="name" autoComplete="off" />
        </div>
        <div className="form-group">
          <label htmlFor="farbe">Farbe:</label>
          <input type="text" id="farbe" name="farbe" autoComplete="off" />
        </div>
        <div className="form-group">
          <label htmlFor="schwierigkeit">Schwierigkeit:</label>
          <input
            type="text"
            id="schwierigkeit"
            name="schwierigkeit"
            autoComplete="off"
          />
        </div>
        <div className="form-group checkbox-container">
          <input type="checkbox" id="is_toprope" name="is_toprope" />
          <label htmlFor="is_toprope">Toprope</label>
          <input type="checkbox" id="is_vorstieg" name="is_vorstieg" />
          <label htmlFor="is_vorstieg">Vorstieg</label>
        </div>
        <div className="form-group">
          <label htmlFor="schrauber">Schrauber:</label>
          <input
            type="text"
            id="schrauber"
            name="schrauber"
            autoComplete="off"
          />
        </div>
        <div className="form-group">
          <label htmlFor="schraubdatum">Schraubdatum:</label>
          <input
            type="date"
            id="schraubdatum"
            name="schraubdatum"
            autoComplete="off"
          />
        </div>
        <div className="form-group">
          <label htmlFor="beschreibung">Beschreibung:</label>
          <textarea
            name="beschreibung"
            id="beschreibung"
            rows={5}
            autoComplete="off"
          ></textarea>
        </div>
        <button type="submit">Route hinzufügen</button>
        {isSuccess && <p className="small">Route erfolgreich hinzugefügt!</p>}
        {isError && <p className="small">Fehler beim Hinzufügen der Route</p>}
      </form>
    </div>
  );
};

export default NeueRoute;
