import { useState } from "react";

const NeueHalleBox = () => {
  const [hidden, setHidden] = useState(false);

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
        <form>
          <div className="form-group">
            <label htmlFor="name">Name der Halle *</label>
            <input type="text" id="name" name="name" />
          </div>
          <div className="form-group">
            <label htmlFor="adresse">Adresse *</label>
            <input type="text" id="adresse" name="adresse" />
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
