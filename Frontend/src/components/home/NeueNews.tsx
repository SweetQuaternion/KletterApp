import { useContext, useState } from "react";
import "../../styles/Form.css";
import { useMutation, useQuery } from "@tanstack/react-query";
import { getFindHalleQueryOptions } from "../../api/hallen-controller/hallen-controller";
import { getAddNewsMutationOptions } from "../../api/news-controller/news-controller";
import type { NewsCreateDTO } from "../../api/model";
import { UserContext } from "../../utils/context.ts";

function NeueNews() {
  const [showForm, setShowForm] = useState(false);
  const { data: hallen } = useQuery(getFindHalleQueryOptions(undefined));
  const { mutate, isSuccess, isError } = useMutation(getAddNewsMutationOptions());
  const user = useContext(UserContext);

  const handleNewsSubmit = (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();

    const form = e.currentTarget;
    const formData = new FormData(form);

    const halleValue = formData.get("halle");
    const halle = halleValue === "" ? null : halleValue;

    const data = {
      hallenId: halle as number | null,
      autorId: user?.keycloakId || "",
      titel: formData.get("titel")?.toString() ?? "",
      inhalt: formData.get("inhalt")?.toString() ?? "",
    } as NewsCreateDTO;

    console.log("Submitting news with data:", data);
    mutate({ data });
  };

  return (
    <>
      <button className="red-button bottom right" onClick={() => setShowForm(!showForm)}>
        News hinzufügen
      </button>

      {showForm && (
        <div className="overlay news-overlay" onClick={() => setShowForm(false)}>
          <div className="white-box large news" onClick={(e) => e.stopPropagation()}>
            <h2>News hinzufügen</h2>
            <p>Hier kannst du eine neue News hinzufügen.</p>

            <form className="news-form" onSubmit={handleNewsSubmit}>
              <div className="form-group">
                <label>Halle:</label>
                <select name="halle">
                  <option value="">keine - allgemeine News</option>
                  {hallen?.map((halle) => (
                    <option key={halle.id} value={halle.id}>
                      {halle.name}
                    </option>
                  ))}
                </select>
              </div>

              <div className="form-group">
                <label>Titel:</label>
                <input name="titel" type="text" placeholder="Ein catchy Titel" />
              </div>

              <div className="form-group">
                <label>Inhalt:</label>
                <textarea
                  name="inhalt"
                  placeholder="Welche Gedanken möchtest du teilen?"
                  rows={15}
                />
              </div>

              <button className="red-button" type="submit">
                Hinzufügen
              </button>

              {isSuccess && <p className="small">News erfolgreich hinzugefügt!</p>}
              {isError && <p className="small">Fehler beim Hinzufügen der News</p>}
            </form>
          </div>
        </div>
      )}
    </>
  );
}

export default NeueNews;
