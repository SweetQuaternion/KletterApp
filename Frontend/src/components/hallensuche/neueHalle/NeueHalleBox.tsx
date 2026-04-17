import { useState } from "react";
import type { FocusEvent } from "react";

interface Props {
  setHalleData: (data: {
    name: string;
    adresse: string;
    betreiber: string;
  }) => void;
}

const NeueHalleBox = ({ setHalleData }: Props) => {
  const [hidden, setHidden] = useState(false);

  function handleFormBlur(e: FocusEvent<HTMLFormElement>) {
    const form = e.currentTarget;
    const formData = new FormData(form);
    setHalleData({
      name: (formData.get("name") as string) || "",
      adresse: (formData.get("adresse") as string) || "",
      betreiber: (formData.get("betreiber") as string) || "",
    });
    console.log("Halle data updated:", {
      name: (formData.get("name") as string) || "",
      adresse: (formData.get("adresse") as string) || "",
      betreiber: (formData.get("betreiber") as string) || "",
    });
  }

  return (
    <div className="details-subcontainer">
      <button className="menu" onClick={() => setHidden(false)}>
        ⌂
      </button>
      <div
        className={`white-box relative neue-halle-box top-left ${hidden ? "hidden" : ""}`}
      >
        <button className="close-button">
          <div onClick={() => setHidden(true)}>×</div>
        </button>
        <h2>Halle hinzufügen</h2>
        <form onBlur={handleFormBlur}>
          <div className="form-group">
            <label htmlFor="name">Name der Halle *</label>
            <input type="text" id="name" name="name" required />
          </div>
          <div className="form-group">
            <label htmlFor="adresse">Adresse *</label>
            <input type="text" id="adresse" name="adresse" required />
          </div>
          <div className="form-group">
            <label htmlFor="betreiber">Betreiber</label>
            <input type="text" id="betreiber" name="betreiber" />
          </div>
        </form>
      </div>
    </div>
  );
};

export default NeueHalleBox;
