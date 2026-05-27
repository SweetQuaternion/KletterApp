import { useState } from "react";
import type { WandResponseDTO } from "../../api/model";

interface Props {
  wände: WandResponseDTO[];
  setWände: (wände: WandResponseDTO[]) => void;
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

  // function moveWand(fromIndex: number, toIndex: number) {
  //   if (fromIndex === toIndex) return;

  //   const newWände = [...wände];
  //   const [wand] = newWände.splice(fromIndex, 1);
  //   newWände.splice(toIndex, 0, wand);
  //   setWände(newWände);
  // }

  function flipWand(index: number) {
    const wand = wände[index];
    const newWände = [...wände];
    newWände[index] = {
      id: wand.id,
      hallenId: wand.hallenId,
      wandNr: index,
      name: wand.name || undefined,
      startX: wand.endX,
      startY: wand.endY,
      endX: wand.startX,
      endY: wand.startY,
      position: wand.position === "indoor" ? "outdoor" : "indoor",
    };
    setWände(newWände);
  }

  function flipPosition(index: number) {
    const wand = wände[index];
    const newWände = [...wände];
    newWände[index] = {
      id: wand.id,
      hallenId: wand.hallenId,
      wandNr: index,
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
    const wand = wände[index];
    const newWände = [...wände];
    newWände[index] = {
      id: wand.id,
      hallenId: wand.hallenId,
      wandNr: -1, // mark as deleted
      name: wand.name || undefined,
      startX: wand.startX,
      startY: wand.startY,
      endX: wand.endX,
      endY: wand.endY,
      position: wand.position,
    };
    setWände(newWände);
    setSelectedWand(null);
    console.log(selectedWand);
  }

  function WandItem({ wand, index }: { wand: WandResponseDTO; index: number }) {
    function handleNameChange(e: React.FocusEvent<HTMLDivElement>) {
      const newName = e.currentTarget.textContent || "";
      const newWände = [...wände];
      newWände[index] = {
        ...wand,
        name: newName || undefined,
      };
      setWände(newWände);
    }

    function handleNrChange(e: React.FocusEvent<HTMLDivElement>) {
      const newNr = e.currentTarget.textContent || "";
      const newWände = [...wände];
      newWände[index] = {
        ...wand,
        wandNr: parseInt(newNr) || 0,
      };
      setWände(newWände);
    }

    if (wand.wandNr === -1) {
      return null;
    }

    return (
      <div
        className={`wand-item ${index === selectedWand ? "selected" : ""}`}
        key={index}
        onClick={(e) => {
          e.stopPropagation();
          console.log("Wand " + index + " ausgewählt");
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
            {wand.name || "Wand"}
          </div>
          <div
            className="wand-name"
            contentEditable={true}
            suppressContentEditableWarning={true}
            onBlur={handleNrChange}
          >
            {wand.wandNr}
          </div>
        </div>
        <div className="wand-right">
          {/* <div className="wand-coords">
            ({wand.startX}, {wand.startY}) → ({wand.endX}, {wand.endY})
          </div> */}

          <button className="flip-button" onClick={() => flipWand(index)} title="flip">
            ⟲
          </button>
          <button
            className="flip-button"
            onClick={(e) => {
              e.stopPropagation();
              deleteWand(index);
            }}
            title="Wand löschen"
          >
            ×
          </button>
          {/* <div>
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
          </div> */}
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
