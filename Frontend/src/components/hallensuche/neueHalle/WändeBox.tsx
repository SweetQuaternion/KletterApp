import { useState } from "react";
import type { WandCreateDTO } from "../../../api/model";

interface Props {
  wände: WandCreateDTO[];
  setWände: (wände: WandCreateDTO[]) => void;
}

const WändeBox = ({ wände, setWände }: Props) => {
  const [hidden, setHidden] = useState(false);

  function moveWand(fromIndex: number, toIndex: number) {
    if (fromIndex === toIndex) return;

    const newWände = [...wände];
    const [wand] = newWände.splice(fromIndex, 1);
    newWände.splice(toIndex, 0, wand);
    setWände(newWände);
  }

  function flipWand(index: number) {
    const wand = wände[index];
    const newWände = [...wände];
    newWände[index] = {
      hallenId: wand.hallenId,
      name: wand.name || undefined,
      startX: wand.endX,
      startY: wand.endY,
      endX: wand.startX,
      endY: wand.startY,
    };
    setWände(newWände);
  }

  function flipPosition(index: number) {
    const wand = wände[index];
    const newWände = [...wände];
    newWände[index] = {
      hallenId: wand.hallenId,
      name: wand.name || undefined,
      startX: wand.startX,
      startY: wand.startY,
      endX: wand.endX,
      endY: wand.endY,
      position: wand.position === "indoor" ? "outdoor" : "indoor",
    };
    setWände(newWände);
  }

  function deleteWand(index: number) {
    const newWände = [...wände];
    newWände.splice(index, 1);
    setWände(newWände);
  }

  function WandItem({ wand, index }: { wand: WandCreateDTO; index: number }) {
    return (
      <div className="wand-item" key={index}>
        <div className="wand-left">
          <div
            className={`mini-dot-${wand.position}`}
            onClick={() => flipPosition(index)}
            title={`${wand.position}`}
          />
          <div className="wand-name">{wand.name || `Wand ${index + 1}`}</div>(
          {wand.startX}, {wand.startY}) - ({wand.endX}, {wand.endY})
        </div>
        <div className="wand-right">
          <div>
            <button
              className="flip-button"
              onClick={() => moveWand(index, index - 1)}
              disabled={index === 0}
              title="Wand nach oben"
            >
              ↑
            </button>
            <button
              className="flip-button"
              onClick={() => moveWand(index, index + 1)}
              disabled={index === wände.length - 1}
              title="Wand nach unten"
            >
              ↓
            </button>
          </div>
          <button
            className="flip-button"
            onClick={() => flipWand(index)}
            title="flip"
          >
            ⟲
          </button>
          <button
            className="flip-button"
            onClick={() => deleteWand(index)}
            title="Wand löschen"
          >
            ×
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="details-subcontainer">
      <button className="menu" onClick={() => setHidden(false)}>
        ⌂
      </button>
      <div className={`white-box relative ${hidden ? "hidden" : ""}`}>
        <button className="close-button">
          <div onClick={() => setHidden(true)}>×</div>
        </button>
        <h2>Wände</h2>
        <div className="wände-liste">
          {wände.map((wand, index) => (
            <WandItem key={index} index={index} wand={wand} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default WändeBox;
