import { useRef, useState } from "react";
import { type Route, type Wand } from "../../constants/APIResponseTypes";
import WandInfoBox from "./WandInfoBox";

interface Props {
  scale: number;
  setScale: (scale: number) => void;
  wände: Wand[];
  selectedWand: Wand | null;
  setSelectedWand: (wand: Wand | null) => void;
  setSelectedRoute: (route: Route | null) => void;
  setShowNeueRoute: (show: boolean) => void;
}

function SVGMap({
  scale,
  setScale,
  wände,
  selectedWand,
  setSelectedWand,
  setSelectedRoute,
  setShowNeueRoute,
}: Props) {
  const [offset, setOffset] = useState({ x: 0, y: 0 });
  const dragging = useRef(false);
  const dragStart = useRef({ x: 0, y: 0, offsetX: 0, offsetY: 0 });

  function onPointerDown(e: React.PointerEvent<SVGSVGElement>) {
    dragging.current = true;
    dragStart.current = {
      x: e.clientX,
      y: e.clientY,
      offsetX: offset.x,
      offsetY: offset.y,
    };
  }

  function onPointerMove(e: React.PointerEvent<SVGSVGElement>) {
    if (!dragging.current) return;
    const dx = e.clientX - dragStart.current.x;
    const dy = e.clientY - dragStart.current.y;
    setOffset({
      x: dragStart.current.offsetX + dx / scale,
      y: dragStart.current.offsetY + dy / scale,
    });
  }

  function onPointerUp() {
    dragging.current = false;
  }

  function onWheel(e: React.WheelEvent<SVGSVGElement>) {
    setScale(scale + (e.deltaY > 0 ? -0.02 : 0.02));
  }

  // ja das Verhalten hier ist super unintuitiv, aber es funktioniert wegen des
  // Closure-Problems trotzdem. selectedWand ist immer ein Render hinterher und
  // deswegen funktioniert der Vergleich. Man sollte hier wohl useRef nehmen.
  const handleWandClick = (wand: Wand | null) => {
    // console.log("Wand " + wand?.wandNr + " clicked");
    setSelectedWand(wand);
    if (selectedWand !== wand) {
      setSelectedRoute(null);
      setShowNeueRoute(false);
    }
    if (wand == null) setSelectedRoute(null);
  };

  return (
    <div className="routenkarte-container">
      <svg
        onPointerDown={onPointerDown}
        onPointerMove={onPointerMove}
        onPointerUp={onPointerUp}
        onWheel={onWheel}
        onClick={() => handleWandClick(null)}
        className="routenkarte"
        xmlns="http://www.w3.org/2000/svg"
        width="100%"
        height="100%"
      >
        <g
          transform={`
            translate(${500 + offset.x * scale}, ${500 + offset.y * scale}) 
            scale(${scale}) 
            translate(200, -100)
          `}
        >
          {wände.map((wand) => (
            <g
              key={wand.wandNr}
              onClick={(e) => {
                e.stopPropagation();
                handleWandClick(wand);
              }}
            >
              <line
                x1={wand.startX}
                y1={wand.startY}
                x2={wand.endX}
                y2={wand.endY}
                stroke="black"
                strokeWidth={5}
                style={{ cursor: "pointer" }}
              />
              {wand.routen.length > 0 && (
                <g>
                  <circle
                    cx={
                      (wand.startX + wand.endX) / 2 -
                      (wand.endY - wand.startY) * 0.1
                    }
                    cy={
                      (wand.startY + wand.endY) / 2 +
                      (wand.endX - wand.startX) * 0.1
                    }
                    r={20}
                    fill={"white"}
                    style={{ cursor: "pointer" }}
                  />
                  <text
                    x={
                      (wand.startX + wand.endX) / 2 -
                      (wand.endY - wand.startY) * 0.1
                    }
                    y={
                      (wand.startY + wand.endY) / 2 +
                      (wand.endX - wand.startX) * 0.1
                    }
                    fontSize={16}
                    fill="black"
                    textAnchor="middle"
                    dominantBaseline="central"
                  >
                    {wand.routen.length}
                  </text>
                  {wand === selectedWand &&
                    (() => {
                      const boxX =
                        (wand.startX + wand.endX) / 2 -
                        (wand.endY - wand.startY) * 0.05;
                      const boxY =
                        (wand.startY + wand.endY) / 2 +
                        (wand.endX - wand.startX) * 0.05;
                      return (
                        <g
                          transform={`translate(${boxX}, ${boxY}) scale(${1 / scale}) translate(${-boxX}, ${-boxY})`}
                        >
                          <foreignObject
                            x={boxX}
                            y={boxY}
                            width="9999"
                            height="9999"
                            pointerEvents="none"
                          >
                            <WandInfoBox
                              selectedWand={wand}
                              routen={wand.routen}
                              setSelectedRoute={setSelectedRoute}
                              setShowNeueRoute={setShowNeueRoute}
                            />
                          </foreignObject>
                        </g>
                      );
                    })()}
                </g>
              )}
            </g>
          ))}
        </g>
      </svg>
    </div>
  );
}

export default SVGMap;

/* <rect x="-500" y="-500" width="50" height="50" fill="tomato" />
  <rect x="450" y="-500" width="50" height="50" fill="green" />
  <rect x="-500" y="450" width="50" height="50" fill="steelblue" />
  <rect x="450" y="450" width="50" height="50" fill="purple" /> */
