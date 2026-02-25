import "../styles/ColorScheme.css";
import "../styles/HallenFinder.css";

function HallenSuchmaske() {
  return (
    <div className="hallen-suchmaske">
      <h2>Wähle deine Halle aus</h2>
      <form className="flex-row">
        <input type="text" placeholder="Suche nach Halle..." />
        <button type="submit">Suchen</button>
      </form>
    </div>
  );
}

export default HallenSuchmaske;
