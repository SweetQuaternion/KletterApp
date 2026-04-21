import { useState } from "react";
import type { WandCreateDTO } from "../../api/model";

interface Props {
  wände: WandCreateDTO[];
  setWände: (wände: WandCreateDTO[]) => void;
  selectedWand: number | null;
  setSelectedWand: (index: number | null) => void;
  setHoveredWand: (index: number | null) => void;
  undo: () => void;
  redo: () => void;
}

const WändeBox = ({
  wände,
  setWände,
  selectedWand,
  setSelectedWand,
  setHoveredWand,
  undo,
  redo,
}: Props) => {
  const [hidden, setHidden] = useState(false);

  function onKeyDown(e: React.KeyboardEvent<HTMLDivElement>) {
    if (e.ctrlKey && e.key === "z") {
      e.preventDefault();
      undo();
    }
    if (e.ctrlKey && e.key === "y") {
      e.preventDefault();
      redo();
    }
  }

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
    function handleNameChange(e: React.FocusEvent<HTMLDivElement>) {
      const newName = e.currentTarget.textContent || "";
      const newWände = [...wände];
      newWände[index] = {
        ...wand,
        name: newName || undefined,
      };
      setWände(newWände);
    }

    return (
      <div
        className={`wand-item ${index === selectedWand ? "selected" : ""}`}
        key={index}
        onClick={(e) => {
          e.stopPropagation();
          setSelectedWand(index);
        }}
        onMouseOver={() => setHoveredWand(index)}
        onMouseOut={() => setHoveredWand(null)}
      >
        <div className="wand-left">
          <div
            className={`mini-dot-${wand.position}`}
            onClick={() => flipPosition(index)}
            title={`${wand.position}`}
          />
          <div
            className="wand-name"
            contentEditable={true}
            suppressContentEditableWarning={true}
            onBlur={handleNameChange}
          >
            {wand.name || `Wand ${index + 1}`}
          </div>
        </div>
        <div className="wand-right">
          <div className="wand-coords">
            ({wand.startX}, {wand.startY}) → ({wand.endX}, {wand.endY})
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
        </div>
      </div>
    );
  }

  return (
    <div className="details-subcontainer">
      <button className="menu" onClick={() => setHidden(false)}>
        ⌂
      </button>
      <div
        className={`white-box relative ${hidden ? "hidden" : ""}`}
        onClick={() => setSelectedWand(null)}
        onKeyDown={onKeyDown}
        tabIndex={0}
      >
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
